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

import com.liferay.portal.NoSuchRecentLayoutRevisionException;
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
import com.liferay.portal.model.RecentLayoutRevision;
import com.liferay.portal.model.impl.RecentLayoutRevisionImpl;
import com.liferay.portal.model.impl.RecentLayoutRevisionModelImpl;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.RecentLayoutRevisionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the recent layout revision service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutRevisionPersistence
 * @see com.liferay.portal.service.persistence.RecentLayoutRevisionUtil
 * @generated
 */
@ProviderType
public class RecentLayoutRevisionPersistenceImpl extends BasePersistenceImpl<RecentLayoutRevision>
	implements RecentLayoutRevisionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RecentLayoutRevisionUtil} to access the recent layout revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RecentLayoutRevisionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			RecentLayoutRevisionModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the recent layout revisions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout revisions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @return the range of matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout revisions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByGroupId(long groupId, int start,
		int end, OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout revisions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByGroupId(long groupId, int start,
		int end, OrderByComparator<RecentLayoutRevision> orderByComparator,
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

		List<RecentLayoutRevision> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutRevision>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutRevision recentLayoutRevision : list) {
					if ((groupId != recentLayoutRevision.getGroupId())) {
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

			query.append(_SQL_SELECT_RECENTLAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RecentLayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<RecentLayoutRevision>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutRevision>)QueryUtil.list(q,
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
	 * Returns the first recent layout revision in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision findByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = fetchByGroupId_First(groupId,
				orderByComparator);

		if (recentLayoutRevision != null) {
			return recentLayoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first recent layout revision in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		List<RecentLayoutRevision> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last recent layout revision in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision findByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (recentLayoutRevision != null) {
			return recentLayoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last recent layout revision in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutRevision> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the recent layout revisions before and after the current recent layout revision in the ordered set where groupId = &#63;.
	 *
	 * @param recentLayoutRevisionId the primary key of the current recent layout revision
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	 */
	@Override
	public RecentLayoutRevision[] findByGroupId_PrevAndNext(
		long recentLayoutRevisionId, long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = findByPrimaryKey(recentLayoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutRevision[] array = new RecentLayoutRevisionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, recentLayoutRevision,
					groupId, orderByComparator, true);

			array[1] = recentLayoutRevision;

			array[2] = getByGroupId_PrevAndNext(session, recentLayoutRevision,
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

	protected RecentLayoutRevision getByGroupId_PrevAndNext(Session session,
		RecentLayoutRevision recentLayoutRevision, long groupId,
		OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTREVISION_WHERE);

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
			query.append(RecentLayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(recentLayoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RecentLayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the recent layout revisions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (RecentLayoutRevision recentLayoutRevision : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(recentLayoutRevision);
		}
	}

	/**
	 * Returns the number of recent layout revisions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching recent layout revisions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RECENTLAYOUTREVISION_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "recentLayoutRevision.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			RecentLayoutRevisionModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the recent layout revisions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout revisions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @return the range of matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByUserId(long userId, int start,
		int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout revisions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByUserId(long userId, int start,
		int end, OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout revisions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByUserId(long userId, int start,
		int end, OrderByComparator<RecentLayoutRevision> orderByComparator,
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

		List<RecentLayoutRevision> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutRevision>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutRevision recentLayoutRevision : list) {
					if ((userId != recentLayoutRevision.getUserId())) {
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

			query.append(_SQL_SELECT_RECENTLAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RecentLayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<RecentLayoutRevision>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutRevision>)QueryUtil.list(q,
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
	 * Returns the first recent layout revision in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision findByUserId_First(long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = fetchByUserId_First(userId,
				orderByComparator);

		if (recentLayoutRevision != null) {
			return recentLayoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first recent layout revision in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByUserId_First(long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		List<RecentLayoutRevision> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last recent layout revision in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision findByUserId_Last(long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = fetchByUserId_Last(userId,
				orderByComparator);

		if (recentLayoutRevision != null) {
			return recentLayoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last recent layout revision in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByUserId_Last(long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutRevision> list = findByUserId(userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the recent layout revisions before and after the current recent layout revision in the ordered set where userId = &#63;.
	 *
	 * @param recentLayoutRevisionId the primary key of the current recent layout revision
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	 */
	@Override
	public RecentLayoutRevision[] findByUserId_PrevAndNext(
		long recentLayoutRevisionId, long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = findByPrimaryKey(recentLayoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutRevision[] array = new RecentLayoutRevisionImpl[3];

			array[0] = getByUserId_PrevAndNext(session, recentLayoutRevision,
					userId, orderByComparator, true);

			array[1] = recentLayoutRevision;

			array[2] = getByUserId_PrevAndNext(session, recentLayoutRevision,
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

	protected RecentLayoutRevision getByUserId_PrevAndNext(Session session,
		RecentLayoutRevision recentLayoutRevision, long userId,
		OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTREVISION_WHERE);

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
			query.append(RecentLayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(recentLayoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RecentLayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the recent layout revisions where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (RecentLayoutRevision recentLayoutRevision : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(recentLayoutRevision);
		}
	}

	/**
	 * Returns the number of recent layout revisions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching recent layout revisions
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RECENTLAYOUTREVISION_WHERE);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "recentLayoutRevision.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTREVISIONID =
		new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutRevisionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTREVISIONID =
		new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutRevisionId", new String[] { Long.class.getName() },
			RecentLayoutRevisionModelImpl.LAYOUTREVISIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTREVISIONID = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutRevisionId", new String[] { Long.class.getName() });

	/**
	 * Returns all the recent layout revisions where layoutRevisionId = &#63;.
	 *
	 * @param layoutRevisionId the layout revision ID
	 * @return the matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId) {
		return findByLayoutRevisionId(layoutRevisionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout revisions where layoutRevisionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutRevisionId the layout revision ID
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @return the range of matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId, int start, int end) {
		return findByLayoutRevisionId(layoutRevisionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout revisions where layoutRevisionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutRevisionId the layout revision ID
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId, int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return findByLayoutRevisionId(layoutRevisionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout revisions where layoutRevisionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutRevisionId the layout revision ID
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findByLayoutRevisionId(
		long layoutRevisionId, int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTREVISIONID;
			finderArgs = new Object[] { layoutRevisionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTREVISIONID;
			finderArgs = new Object[] {
					layoutRevisionId,
					
					start, end, orderByComparator
				};
		}

		List<RecentLayoutRevision> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutRevision>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutRevision recentLayoutRevision : list) {
					if ((layoutRevisionId != recentLayoutRevision.getLayoutRevisionId())) {
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

			query.append(_SQL_SELECT_RECENTLAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTREVISIONID_LAYOUTREVISIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RecentLayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutRevisionId);

				if (!pagination) {
					list = (List<RecentLayoutRevision>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutRevision>)QueryUtil.list(q,
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
	 * Returns the first recent layout revision in the ordered set where layoutRevisionId = &#63;.
	 *
	 * @param layoutRevisionId the layout revision ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision findByLayoutRevisionId_First(
		long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = fetchByLayoutRevisionId_First(layoutRevisionId,
				orderByComparator);

		if (recentLayoutRevision != null) {
			return recentLayoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutRevisionId=");
		msg.append(layoutRevisionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first recent layout revision in the ordered set where layoutRevisionId = &#63;.
	 *
	 * @param layoutRevisionId the layout revision ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByLayoutRevisionId_First(
		long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		List<RecentLayoutRevision> list = findByLayoutRevisionId(layoutRevisionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last recent layout revision in the ordered set where layoutRevisionId = &#63;.
	 *
	 * @param layoutRevisionId the layout revision ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision findByLayoutRevisionId_Last(
		long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = fetchByLayoutRevisionId_Last(layoutRevisionId,
				orderByComparator);

		if (recentLayoutRevision != null) {
			return recentLayoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutRevisionId=");
		msg.append(layoutRevisionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last recent layout revision in the ordered set where layoutRevisionId = &#63;.
	 *
	 * @param layoutRevisionId the layout revision ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByLayoutRevisionId_Last(
		long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		int count = countByLayoutRevisionId(layoutRevisionId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutRevision> list = findByLayoutRevisionId(layoutRevisionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the recent layout revisions before and after the current recent layout revision in the ordered set where layoutRevisionId = &#63;.
	 *
	 * @param recentLayoutRevisionId the primary key of the current recent layout revision
	 * @param layoutRevisionId the layout revision ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	 */
	@Override
	public RecentLayoutRevision[] findByLayoutRevisionId_PrevAndNext(
		long recentLayoutRevisionId, long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = findByPrimaryKey(recentLayoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutRevision[] array = new RecentLayoutRevisionImpl[3];

			array[0] = getByLayoutRevisionId_PrevAndNext(session,
					recentLayoutRevision, layoutRevisionId, orderByComparator,
					true);

			array[1] = recentLayoutRevision;

			array[2] = getByLayoutRevisionId_PrevAndNext(session,
					recentLayoutRevision, layoutRevisionId, orderByComparator,
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

	protected RecentLayoutRevision getByLayoutRevisionId_PrevAndNext(
		Session session, RecentLayoutRevision recentLayoutRevision,
		long layoutRevisionId,
		OrderByComparator<RecentLayoutRevision> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTREVISIONID_LAYOUTREVISIONID_2);

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
			query.append(RecentLayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutRevisionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(recentLayoutRevision);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RecentLayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the recent layout revisions where layoutRevisionId = &#63; from the database.
	 *
	 * @param layoutRevisionId the layout revision ID
	 */
	@Override
	public void removeByLayoutRevisionId(long layoutRevisionId) {
		for (RecentLayoutRevision recentLayoutRevision : findByLayoutRevisionId(
				layoutRevisionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(recentLayoutRevision);
		}
	}

	/**
	 * Returns the number of recent layout revisions where layoutRevisionId = &#63;.
	 *
	 * @param layoutRevisionId the layout revision ID
	 * @return the number of matching recent layout revisions
	 */
	@Override
	public int countByLayoutRevisionId(long layoutRevisionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTREVISIONID;

		Object[] finderArgs = new Object[] { layoutRevisionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RECENTLAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTREVISIONID_LAYOUTREVISIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutRevisionId);

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

	private static final String _FINDER_COLUMN_LAYOUTREVISIONID_LAYOUTREVISIONID_2 =
		"recentLayoutRevision.layoutRevisionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_U_L_P = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			RecentLayoutRevisionModelImpl.USERID_COLUMN_BITMASK |
			RecentLayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			RecentLayoutRevisionModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_L_P = new FinderPath(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or throws a {@link NoSuchRecentLayoutRevisionException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the matching recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision findByU_L_P(long userId,
		long layoutSetBranchId, long plid)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = fetchByU_L_P(userId,
				layoutSetBranchId, plid);

		if (recentLayoutRevision == null) {
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

			throw new NoSuchRecentLayoutRevisionException(msg.toString());
		}

		return recentLayoutRevision;
	}

	/**
	 * Returns the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByU_L_P(long userId,
		long layoutSetBranchId, long plid) {
		return fetchByU_L_P(userId, layoutSetBranchId, plid, true);
	}

	/**
	 * Returns the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching recent layout revision, or <code>null</code> if a matching recent layout revision could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByU_L_P(long userId,
		long layoutSetBranchId, long plid, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { userId, layoutSetBranchId, plid };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_U_L_P,
					finderArgs, this);
		}

		if (result instanceof RecentLayoutRevision) {
			RecentLayoutRevision recentLayoutRevision = (RecentLayoutRevision)result;

			if ((userId != recentLayoutRevision.getUserId()) ||
					(layoutSetBranchId != recentLayoutRevision.getLayoutSetBranchId()) ||
					(plid != recentLayoutRevision.getPlid())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_RECENTLAYOUTREVISION_WHERE);

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

				List<RecentLayoutRevision> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P,
						finderArgs, list);
				}
				else {
					RecentLayoutRevision recentLayoutRevision = list.get(0);

					result = recentLayoutRevision;

					cacheResult(recentLayoutRevision);

					if ((recentLayoutRevision.getUserId() != userId) ||
							(recentLayoutRevision.getLayoutSetBranchId() != layoutSetBranchId) ||
							(recentLayoutRevision.getPlid() != plid)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P,
							finderArgs, recentLayoutRevision);
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
			return (RecentLayoutRevision)result;
		}
	}

	/**
	 * Removes the recent layout revision where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the recent layout revision that was removed
	 */
	@Override
	public RecentLayoutRevision removeByU_L_P(long userId,
		long layoutSetBranchId, long plid)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = findByU_L_P(userId,
				layoutSetBranchId, plid);

		return remove(recentLayoutRevision);
	}

	/**
	 * Returns the number of recent layout revisions where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the number of matching recent layout revisions
	 */
	@Override
	public int countByU_L_P(long userId, long layoutSetBranchId, long plid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_L_P;

		Object[] finderArgs = new Object[] { userId, layoutSetBranchId, plid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RECENTLAYOUTREVISION_WHERE);

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

	private static final String _FINDER_COLUMN_U_L_P_USERID_2 = "recentLayoutRevision.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_L_P_LAYOUTSETBRANCHID_2 = "recentLayoutRevision.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_U_L_P_PLID_2 = "recentLayoutRevision.plid = ?";

	public RecentLayoutRevisionPersistenceImpl() {
		setModelClass(RecentLayoutRevision.class);
	}

	/**
	 * Caches the recent layout revision in the entity cache if it is enabled.
	 *
	 * @param recentLayoutRevision the recent layout revision
	 */
	@Override
	public void cacheResult(RecentLayoutRevision recentLayoutRevision) {
		entityCache.putResult(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			recentLayoutRevision.getPrimaryKey(), recentLayoutRevision);

		finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P,
			new Object[] {
				recentLayoutRevision.getUserId(),
				recentLayoutRevision.getLayoutSetBranchId(),
				recentLayoutRevision.getPlid()
			}, recentLayoutRevision);

		recentLayoutRevision.resetOriginalValues();
	}

	/**
	 * Caches the recent layout revisions in the entity cache if it is enabled.
	 *
	 * @param recentLayoutRevisions the recent layout revisions
	 */
	@Override
	public void cacheResult(List<RecentLayoutRevision> recentLayoutRevisions) {
		for (RecentLayoutRevision recentLayoutRevision : recentLayoutRevisions) {
			if (entityCache.getResult(
						RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
						RecentLayoutRevisionImpl.class,
						recentLayoutRevision.getPrimaryKey()) == null) {
				cacheResult(recentLayoutRevision);
			}
			else {
				recentLayoutRevision.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all recent layout revisions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RecentLayoutRevisionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the recent layout revision.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RecentLayoutRevision recentLayoutRevision) {
		entityCache.removeResult(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class, recentLayoutRevision.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((RecentLayoutRevisionModelImpl)recentLayoutRevision);
	}

	@Override
	public void clearCache(List<RecentLayoutRevision> recentLayoutRevisions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RecentLayoutRevision recentLayoutRevision : recentLayoutRevisions) {
			entityCache.removeResult(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
				RecentLayoutRevisionImpl.class,
				recentLayoutRevision.getPrimaryKey());

			clearUniqueFindersCache((RecentLayoutRevisionModelImpl)recentLayoutRevision);
		}
	}

	protected void cacheUniqueFindersCache(
		RecentLayoutRevisionModelImpl recentLayoutRevisionModelImpl,
		boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					recentLayoutRevisionModelImpl.getUserId(),
					recentLayoutRevisionModelImpl.getLayoutSetBranchId(),
					recentLayoutRevisionModelImpl.getPlid()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_U_L_P, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P, args,
				recentLayoutRevisionModelImpl);
		}
		else {
			if ((recentLayoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_L_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutRevisionModelImpl.getUserId(),
						recentLayoutRevisionModelImpl.getLayoutSetBranchId(),
						recentLayoutRevisionModelImpl.getPlid()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_U_L_P, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P, args,
					recentLayoutRevisionModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		RecentLayoutRevisionModelImpl recentLayoutRevisionModelImpl) {
		Object[] args = new Object[] {
				recentLayoutRevisionModelImpl.getUserId(),
				recentLayoutRevisionModelImpl.getLayoutSetBranchId(),
				recentLayoutRevisionModelImpl.getPlid()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_U_L_P, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_U_L_P, args);

		if ((recentLayoutRevisionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_U_L_P.getColumnBitmask()) != 0) {
			args = new Object[] {
					recentLayoutRevisionModelImpl.getOriginalUserId(),
					recentLayoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
					recentLayoutRevisionModelImpl.getOriginalPlid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_L_P, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_U_L_P, args);
		}
	}

	/**
	 * Creates a new recent layout revision with the primary key. Does not add the recent layout revision to the database.
	 *
	 * @param recentLayoutRevisionId the primary key for the new recent layout revision
	 * @return the new recent layout revision
	 */
	@Override
	public RecentLayoutRevision create(long recentLayoutRevisionId) {
		RecentLayoutRevision recentLayoutRevision = new RecentLayoutRevisionImpl();

		recentLayoutRevision.setNew(true);
		recentLayoutRevision.setPrimaryKey(recentLayoutRevisionId);

		return recentLayoutRevision;
	}

	/**
	 * Removes the recent layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutRevisionId the primary key of the recent layout revision
	 * @return the recent layout revision that was removed
	 * @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	 */
	@Override
	public RecentLayoutRevision remove(long recentLayoutRevisionId)
		throws NoSuchRecentLayoutRevisionException {
		return remove((Serializable)recentLayoutRevisionId);
	}

	/**
	 * Removes the recent layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the recent layout revision
	 * @return the recent layout revision that was removed
	 * @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	 */
	@Override
	public RecentLayoutRevision remove(Serializable primaryKey)
		throws NoSuchRecentLayoutRevisionException {
		Session session = null;

		try {
			session = openSession();

			RecentLayoutRevision recentLayoutRevision = (RecentLayoutRevision)session.get(RecentLayoutRevisionImpl.class,
					primaryKey);

			if (recentLayoutRevision == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecentLayoutRevisionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(recentLayoutRevision);
		}
		catch (NoSuchRecentLayoutRevisionException nsee) {
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
	protected RecentLayoutRevision removeImpl(
		RecentLayoutRevision recentLayoutRevision) {
		recentLayoutRevision = toUnwrappedModel(recentLayoutRevision);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(recentLayoutRevision)) {
				recentLayoutRevision = (RecentLayoutRevision)session.get(RecentLayoutRevisionImpl.class,
						recentLayoutRevision.getPrimaryKeyObj());
			}

			if (recentLayoutRevision != null) {
				session.delete(recentLayoutRevision);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (recentLayoutRevision != null) {
			clearCache(recentLayoutRevision);
		}

		return recentLayoutRevision;
	}

	@Override
	public RecentLayoutRevision updateImpl(
		RecentLayoutRevision recentLayoutRevision) {
		recentLayoutRevision = toUnwrappedModel(recentLayoutRevision);

		boolean isNew = recentLayoutRevision.isNew();

		RecentLayoutRevisionModelImpl recentLayoutRevisionModelImpl = (RecentLayoutRevisionModelImpl)recentLayoutRevision;

		Session session = null;

		try {
			session = openSession();

			if (recentLayoutRevision.isNew()) {
				session.save(recentLayoutRevision);

				recentLayoutRevision.setNew(false);
			}
			else {
				recentLayoutRevision = (RecentLayoutRevision)session.merge(recentLayoutRevision);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !RecentLayoutRevisionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((recentLayoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutRevisionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { recentLayoutRevisionModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((recentLayoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutRevisionModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { recentLayoutRevisionModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((recentLayoutRevisionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTREVISIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutRevisionModelImpl.getOriginalLayoutRevisionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTREVISIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTREVISIONID,
					args);

				args = new Object[] {
						recentLayoutRevisionModelImpl.getLayoutRevisionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTREVISIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTREVISIONID,
					args);
			}
		}

		entityCache.putResult(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutRevisionImpl.class,
			recentLayoutRevision.getPrimaryKey(), recentLayoutRevision, false);

		clearUniqueFindersCache(recentLayoutRevisionModelImpl);
		cacheUniqueFindersCache(recentLayoutRevisionModelImpl, isNew);

		recentLayoutRevision.resetOriginalValues();

		return recentLayoutRevision;
	}

	protected RecentLayoutRevision toUnwrappedModel(
		RecentLayoutRevision recentLayoutRevision) {
		if (recentLayoutRevision instanceof RecentLayoutRevisionImpl) {
			return recentLayoutRevision;
		}

		RecentLayoutRevisionImpl recentLayoutRevisionImpl = new RecentLayoutRevisionImpl();

		recentLayoutRevisionImpl.setNew(recentLayoutRevision.isNew());
		recentLayoutRevisionImpl.setPrimaryKey(recentLayoutRevision.getPrimaryKey());

		recentLayoutRevisionImpl.setMvccVersion(recentLayoutRevision.getMvccVersion());
		recentLayoutRevisionImpl.setRecentLayoutRevisionId(recentLayoutRevision.getRecentLayoutRevisionId());
		recentLayoutRevisionImpl.setGroupId(recentLayoutRevision.getGroupId());
		recentLayoutRevisionImpl.setCompanyId(recentLayoutRevision.getCompanyId());
		recentLayoutRevisionImpl.setUserId(recentLayoutRevision.getUserId());
		recentLayoutRevisionImpl.setLayoutRevisionId(recentLayoutRevision.getLayoutRevisionId());
		recentLayoutRevisionImpl.setLayoutSetBranchId(recentLayoutRevision.getLayoutSetBranchId());
		recentLayoutRevisionImpl.setPlid(recentLayoutRevision.getPlid());

		return recentLayoutRevisionImpl;
	}

	/**
	 * Returns the recent layout revision with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the recent layout revision
	 * @return the recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	 */
	@Override
	public RecentLayoutRevision findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecentLayoutRevisionException {
		RecentLayoutRevision recentLayoutRevision = fetchByPrimaryKey(primaryKey);

		if (recentLayoutRevision == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecentLayoutRevisionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return recentLayoutRevision;
	}

	/**
	 * Returns the recent layout revision with the primary key or throws a {@link NoSuchRecentLayoutRevisionException} if it could not be found.
	 *
	 * @param recentLayoutRevisionId the primary key of the recent layout revision
	 * @return the recent layout revision
	 * @throws NoSuchRecentLayoutRevisionException if a recent layout revision with the primary key could not be found
	 */
	@Override
	public RecentLayoutRevision findByPrimaryKey(long recentLayoutRevisionId)
		throws NoSuchRecentLayoutRevisionException {
		return findByPrimaryKey((Serializable)recentLayoutRevisionId);
	}

	/**
	 * Returns the recent layout revision with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the recent layout revision
	 * @return the recent layout revision, or <code>null</code> if a recent layout revision with the primary key could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByPrimaryKey(Serializable primaryKey) {
		RecentLayoutRevision recentLayoutRevision = (RecentLayoutRevision)entityCache.getResult(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
				RecentLayoutRevisionImpl.class, primaryKey);

		if (recentLayoutRevision == _nullRecentLayoutRevision) {
			return null;
		}

		if (recentLayoutRevision == null) {
			Session session = null;

			try {
				session = openSession();

				recentLayoutRevision = (RecentLayoutRevision)session.get(RecentLayoutRevisionImpl.class,
						primaryKey);

				if (recentLayoutRevision != null) {
					cacheResult(recentLayoutRevision);
				}
				else {
					entityCache.putResult(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
						RecentLayoutRevisionImpl.class, primaryKey,
						_nullRecentLayoutRevision);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutRevisionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return recentLayoutRevision;
	}

	/**
	 * Returns the recent layout revision with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recentLayoutRevisionId the primary key of the recent layout revision
	 * @return the recent layout revision, or <code>null</code> if a recent layout revision with the primary key could not be found
	 */
	@Override
	public RecentLayoutRevision fetchByPrimaryKey(long recentLayoutRevisionId) {
		return fetchByPrimaryKey((Serializable)recentLayoutRevisionId);
	}

	@Override
	public Map<Serializable, RecentLayoutRevision> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RecentLayoutRevision> map = new HashMap<Serializable, RecentLayoutRevision>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RecentLayoutRevision recentLayoutRevision = fetchByPrimaryKey(primaryKey);

			if (recentLayoutRevision != null) {
				map.put(primaryKey, recentLayoutRevision);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			RecentLayoutRevision recentLayoutRevision = (RecentLayoutRevision)entityCache.getResult(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutRevisionImpl.class, primaryKey);

			if (recentLayoutRevision == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, recentLayoutRevision);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_RECENTLAYOUTREVISION_WHERE_PKS_IN);

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

			for (RecentLayoutRevision recentLayoutRevision : (List<RecentLayoutRevision>)q.list()) {
				map.put(recentLayoutRevision.getPrimaryKeyObj(),
					recentLayoutRevision);

				cacheResult(recentLayoutRevision);

				uncachedPrimaryKeys.remove(recentLayoutRevision.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(RecentLayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutRevisionImpl.class, primaryKey,
					_nullRecentLayoutRevision);
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
	 * Returns all the recent layout revisions.
	 *
	 * @return the recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @return the range of recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findAll(int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of recent layout revisions
	 */
	@Override
	public List<RecentLayoutRevision> findAll(int start, int end,
		OrderByComparator<RecentLayoutRevision> orderByComparator,
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

		List<RecentLayoutRevision> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutRevision>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RECENTLAYOUTREVISION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RECENTLAYOUTREVISION;

				if (pagination) {
					sql = sql.concat(RecentLayoutRevisionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<RecentLayoutRevision>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutRevision>)QueryUtil.list(q,
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
	 * Removes all the recent layout revisions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RecentLayoutRevision recentLayoutRevision : findAll()) {
			remove(recentLayoutRevision);
		}
	}

	/**
	 * Returns the number of recent layout revisions.
	 *
	 * @return the number of recent layout revisions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RECENTLAYOUTREVISION);

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
		return RecentLayoutRevisionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the recent layout revision persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(RecentLayoutRevisionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_RECENTLAYOUTREVISION = "SELECT recentLayoutRevision FROM RecentLayoutRevision recentLayoutRevision";
	private static final String _SQL_SELECT_RECENTLAYOUTREVISION_WHERE_PKS_IN = "SELECT recentLayoutRevision FROM RecentLayoutRevision recentLayoutRevision WHERE recentLayoutRevisionId IN (";
	private static final String _SQL_SELECT_RECENTLAYOUTREVISION_WHERE = "SELECT recentLayoutRevision FROM RecentLayoutRevision recentLayoutRevision WHERE ";
	private static final String _SQL_COUNT_RECENTLAYOUTREVISION = "SELECT COUNT(recentLayoutRevision) FROM RecentLayoutRevision recentLayoutRevision";
	private static final String _SQL_COUNT_RECENTLAYOUTREVISION_WHERE = "SELECT COUNT(recentLayoutRevision) FROM RecentLayoutRevision recentLayoutRevision WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "recentLayoutRevision.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RecentLayoutRevision exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No RecentLayoutRevision exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(RecentLayoutRevisionPersistenceImpl.class);
	private static final RecentLayoutRevision _nullRecentLayoutRevision = new RecentLayoutRevisionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<RecentLayoutRevision> toCacheModel() {
				return _nullRecentLayoutRevisionCacheModel;
			}
		};

	private static final CacheModel<RecentLayoutRevision> _nullRecentLayoutRevisionCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<RecentLayoutRevision>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public RecentLayoutRevision toEntityModel() {
			return _nullRecentLayoutRevision;
		}
	}
}