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

package com.liferay.portlet.social.service.persistence.impl;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchActivitySetException;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.model.impl.SocialActivitySetImpl;
import com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl;
import com.liferay.portlet.social.service.persistence.SocialActivitySetPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the social activity set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySetPersistence
 * @see com.liferay.portlet.social.service.persistence.SocialActivitySetUtil
 * @generated
 */
@ProviderType
public class SocialActivitySetPersistenceImpl extends BasePersistenceImpl<SocialActivitySet>
	implements SocialActivitySetPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialActivitySetUtil} to access the social activity set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialActivitySetImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SocialActivitySetModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the social activity sets where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator,
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

		List<SocialActivitySet> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivitySet>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((groupId != socialActivitySet.getGroupId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
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
	 * Returns the first social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByGroupId_First(long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByGroupId_First(groupId,
				orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByGroupId_First(long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		List<SocialActivitySet> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByGroupId_Last(long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByGroupId_Last(long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where groupId = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByGroupId_PrevAndNext(long activitySetId,
		long groupId, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, socialActivitySet,
					groupId, orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByGroupId_PrevAndNext(session, socialActivitySet,
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

	protected SocialActivitySet getByGroupId_PrevAndNext(Session session,
		SocialActivitySet socialActivitySet, long groupId,
		OrderByComparator<SocialActivitySet> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

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
			query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SocialActivitySet socialActivitySet : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "socialActivitySet.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			SocialActivitySetModelImpl.USERID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the social activity sets where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByUserId(long userId, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByUserId(long userId, int start,
		int end, OrderByComparator<SocialActivitySet> orderByComparator,
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

		List<SocialActivitySet> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivitySet>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((userId != socialActivitySet.getUserId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
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
	 * Returns the first social activity set in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByUserId_First(long userId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByUserId_First(userId,
				orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByUserId_First(long userId,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		List<SocialActivitySet> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByUserId_Last(long userId,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByUserId_Last(userId,
				orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByUserId_Last(long userId,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where userId = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByUserId_PrevAndNext(long activitySetId,
		long userId, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByUserId_PrevAndNext(session, socialActivitySet,
					userId, orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByUserId_PrevAndNext(session, socialActivitySet,
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

	protected SocialActivitySet getByUserId_PrevAndNext(Session session,
		SocialActivitySet socialActivitySet, long userId,
		OrderByComparator<SocialActivitySet> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

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
			query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (SocialActivitySet socialActivitySet : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "socialActivitySet.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivitySetModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.USERID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_T(long groupId, long userId,
		int type) {
		return findByG_U_T(groupId, userId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_T(long groupId, long userId,
		int type, int start, int end) {
		return findByG_U_T(groupId, userId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_T(long groupId, long userId,
		int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return findByG_U_T(groupId, userId, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_T(long groupId, long userId,
		int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T;
			finderArgs = new Object[] { groupId, userId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_T;
			finderArgs = new Object[] {
					groupId, userId, type,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivitySet> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivitySet>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((groupId != socialActivitySet.getGroupId()) ||
							(userId != socialActivitySet.getUserId()) ||
							(type != socialActivitySet.getType())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(type);

				if (!pagination) {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
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
	 * Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByG_U_T_First(long groupId, long userId,
		int type, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByG_U_T_First(groupId,
				userId, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByG_U_T_First(long groupId, long userId,
		int type, OrderByComparator<SocialActivitySet> orderByComparator) {
		List<SocialActivitySet> list = findByG_U_T(groupId, userId, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByG_U_T_Last(long groupId, long userId,
		int type, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByG_U_T_Last(groupId,
				userId, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByG_U_T_Last(long groupId, long userId,
		int type, OrderByComparator<SocialActivitySet> orderByComparator) {
		int count = countByG_U_T(groupId, userId, type);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByG_U_T(groupId, userId, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByG_U_T_PrevAndNext(long activitySetId,
		long groupId, long userId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByG_U_T_PrevAndNext(session, socialActivitySet,
					groupId, userId, type, orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByG_U_T_PrevAndNext(session, socialActivitySet,
					groupId, userId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySet getByG_U_T_PrevAndNext(Session session,
		SocialActivitySet socialActivitySet, long groupId, long userId,
		int type, OrderByComparator<SocialActivitySet> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

		query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_T_USERID_2);

		query.append(_FINDER_COLUMN_G_U_T_TYPE_2);

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
			query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where groupId = &#63; and userId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 */
	@Override
	public void removeByG_U_T(long groupId, long userId, int type) {
		for (SocialActivitySet socialActivitySet : findByG_U_T(groupId, userId,
				type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByG_U_T(long groupId, long userId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_T;

		Object[] finderArgs = new Object[] { groupId, userId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_U_T_GROUPID_2 = "socialActivitySet.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_T_USERID_2 = "socialActivitySet.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_T_TYPE_2 = "socialActivitySet.type = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivitySetModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivitySetModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByC_C_T(long classNameId, long classPK,
		int type) {
		return findByC_C_T(classNameId, classPK, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByC_C_T(long classNameId, long classPK,
		int type, int start, int end) {
		return findByC_C_T(classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByC_C_T(long classNameId, long classPK,
		int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return findByC_C_T(classNameId, classPK, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByC_C_T(long classNameId, long classPK,
		int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T;
			finderArgs = new Object[] { classNameId, classPK, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T;
			finderArgs = new Object[] {
					classNameId, classPK, type,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivitySet> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivitySet>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((classNameId != socialActivitySet.getClassNameId()) ||
							(classPK != socialActivitySet.getClassPK()) ||
							(type != socialActivitySet.getType())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				if (!pagination) {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
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
	 * Returns the first social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByC_C_T_First(long classNameId, long classPK,
		int type, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByC_C_T_First(classNameId,
				classPK, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByC_C_T_First(long classNameId, long classPK,
		int type, OrderByComparator<SocialActivitySet> orderByComparator) {
		List<SocialActivitySet> list = findByC_C_T(classNameId, classPK, type,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByC_C_T_Last(long classNameId, long classPK,
		int type, OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByC_C_T_Last(classNameId,
				classPK, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByC_C_T_Last(long classNameId, long classPK,
		int type, OrderByComparator<SocialActivitySet> orderByComparator) {
		int count = countByC_C_T(classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByC_C_T(classNameId, classPK, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByC_C_T_PrevAndNext(long activitySetId,
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByC_C_T_PrevAndNext(session, socialActivitySet,
					classNameId, classPK, type, orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByC_C_T_PrevAndNext(session, socialActivitySet,
					classNameId, classPK, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySet getByC_C_T_PrevAndNext(Session session,
		SocialActivitySet socialActivitySet, long classNameId, long classPK,
		int type, OrderByComparator<SocialActivitySet> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

		query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_T_TYPE_2);

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
			query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 */
	@Override
	public void removeByC_C_T(long classNameId, long classPK, int type) {
		for (SocialActivitySet socialActivitySet : findByC_C_T(classNameId,
				classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByC_C_T(long classNameId, long classPK, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_T;

		Object[] finderArgs = new Object[] { classNameId, classPK, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_C_C_T_CLASSNAMEID_2 = "socialActivitySet.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_CLASSPK_2 = "socialActivitySet.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_TYPE_2 = "socialActivitySet.type = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T =
		new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivitySetModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.USERID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_C_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_C_T(long groupId, long userId,
		long classNameId, int type) {
		return findByG_U_C_T(groupId, userId, classNameId, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_C_T(long groupId, long userId,
		long classNameId, int type, int start, int end) {
		return findByG_U_C_T(groupId, userId, classNameId, type, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_C_T(long groupId, long userId,
		long classNameId, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return findByG_U_C_T(groupId, userId, classNameId, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByG_U_C_T(long groupId, long userId,
		long classNameId, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T;
			finderArgs = new Object[] { groupId, userId, classNameId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C_T;
			finderArgs = new Object[] {
					groupId, userId, classNameId, type,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivitySet> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivitySet>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((groupId != socialActivitySet.getGroupId()) ||
							(userId != socialActivitySet.getUserId()) ||
							(classNameId != socialActivitySet.getClassNameId()) ||
							(type != socialActivitySet.getType())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(type);

				if (!pagination) {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
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
	 * Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByG_U_C_T_First(long groupId, long userId,
		long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByG_U_C_T_First(groupId,
				userId, classNameId, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByG_U_C_T_First(long groupId, long userId,
		long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		List<SocialActivitySet> list = findByG_U_C_T(groupId, userId,
				classNameId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByG_U_C_T_Last(long groupId, long userId,
		long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByG_U_C_T_Last(groupId,
				userId, classNameId, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByG_U_C_T_Last(long groupId, long userId,
		long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		int count = countByG_U_C_T(groupId, userId, classNameId, type);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByG_U_C_T(groupId, userId,
				classNameId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByG_U_C_T_PrevAndNext(long activitySetId,
		long groupId, long userId, long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByG_U_C_T_PrevAndNext(session, socialActivitySet,
					groupId, userId, classNameId, type, orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByG_U_C_T_PrevAndNext(session, socialActivitySet,
					groupId, userId, classNameId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySet getByG_U_C_T_PrevAndNext(Session session,
		SocialActivitySet socialActivitySet, long groupId, long userId,
		long classNameId, int type,
		OrderByComparator<SocialActivitySet> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

		query.append(_FINDER_COLUMN_G_U_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_C_T_USERID_2);

		query.append(_FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_U_C_T_TYPE_2);

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
			query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(classNameId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 */
	@Override
	public void removeByG_U_C_T(long groupId, long userId, long classNameId,
		int type) {
		for (SocialActivitySet socialActivitySet : findByG_U_C_T(groupId,
				userId, classNameId, type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where groupId = &#63; and userId = &#63; and classNameId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByG_U_C_T(long groupId, long userId, long classNameId,
		int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_C_T;

		Object[] finderArgs = new Object[] { groupId, userId, classNameId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_U_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_U_C_T_GROUPID_2 = "socialActivitySet.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_T_USERID_2 = "socialActivitySet.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_T_CLASSNAMEID_2 = "socialActivitySet.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_T_TYPE_2 = "socialActivitySet.type = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_C_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T =
		new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivitySetModelImpl.USERID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivitySetModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivitySetModelImpl.TYPE_COLUMN_BITMASK |
			SocialActivitySetModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C_T = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByU_C_C_T(long userId, long classNameId,
		long classPK, int type) {
		return findByU_C_C_T(userId, classNameId, classPK, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByU_C_C_T(long userId, long classNameId,
		long classPK, int type, int start, int end) {
		return findByU_C_C_T(userId, classNameId, classPK, type, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByU_C_C_T(long userId, long classNameId,
		long classPK, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return findByU_C_C_T(userId, classNameId, classPK, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching social activity sets
	 */
	@Override
	public List<SocialActivitySet> findByU_C_C_T(long userId, long classNameId,
		long classPK, int type, int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T;
			finderArgs = new Object[] { userId, classNameId, classPK, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C_C_T;
			finderArgs = new Object[] {
					userId, classNameId, classPK, type,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivitySet> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivitySet>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivitySet socialActivitySet : list) {
					if ((userId != socialActivitySet.getUserId()) ||
							(classNameId != socialActivitySet.getClassNameId()) ||
							(classPK != socialActivitySet.getClassPK()) ||
							(type != socialActivitySet.getType())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_T_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_U_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				if (!pagination) {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
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
	 * Returns the first social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByU_C_C_T_First(long userId, long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByU_C_C_T_First(userId,
				classNameId, classPK, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the first social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByU_C_C_T_First(long userId,
		long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		List<SocialActivitySet> list = findByU_C_C_T(userId, classNameId,
				classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set
	 * @throws NoSuchActivitySetException if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet findByU_C_C_T_Last(long userId, long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByU_C_C_T_Last(userId,
				classNameId, classPK, type, orderByComparator);

		if (socialActivitySet != null) {
			return socialActivitySet;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivitySetException(msg.toString());
	}

	/**
	 * Returns the last social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity set, or <code>null</code> if a matching social activity set could not be found
	 */
	@Override
	public SocialActivitySet fetchByU_C_C_T_Last(long userId, long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		int count = countByU_C_C_T(userId, classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<SocialActivitySet> list = findByU_C_C_T(userId, classNameId,
				classPK, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity sets before and after the current social activity set in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param activitySetId the primary key of the current social activity set
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet[] findByU_C_C_T_PrevAndNext(long activitySetId,
		long userId, long classNameId, long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = findByPrimaryKey(activitySetId);

		Session session = null;

		try {
			session = openSession();

			SocialActivitySet[] array = new SocialActivitySetImpl[3];

			array[0] = getByU_C_C_T_PrevAndNext(session, socialActivitySet,
					userId, classNameId, classPK, type, orderByComparator, true);

			array[1] = socialActivitySet;

			array[2] = getByU_C_C_T_PrevAndNext(session, socialActivitySet,
					userId, classNameId, classPK, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivitySet getByU_C_C_T_PrevAndNext(Session session,
		SocialActivitySet socialActivitySet, long userId, long classNameId,
		long classPK, int type,
		OrderByComparator<SocialActivitySet> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE);

		query.append(_FINDER_COLUMN_U_C_C_T_USERID_2);

		query.append(_FINDER_COLUMN_U_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_U_C_C_T_CLASSPK_2);

		query.append(_FINDER_COLUMN_U_C_C_T_TYPE_2);

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
			query.append(SocialActivitySetModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivitySet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivitySet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 */
	@Override
	public void removeByU_C_C_T(long userId, long classNameId, long classPK,
		int type) {
		for (SocialActivitySet socialActivitySet : findByU_C_C_T(userId,
				classNameId, classPK, type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the number of matching social activity sets
	 */
	@Override
	public int countByU_C_C_T(long userId, long classNameId, long classPK,
		int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_C_C_T;

		Object[] finderArgs = new Object[] { userId, classNameId, classPK, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALACTIVITYSET_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_T_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_U_C_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_U_C_C_T_USERID_2 = "socialActivitySet.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_CLASSNAMEID_2 = "socialActivitySet.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_CLASSPK_2 = "socialActivitySet.classPK = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_T_TYPE_2 = "socialActivitySet.type = ?";

	public SocialActivitySetPersistenceImpl() {
		setModelClass(SocialActivitySet.class);
	}

	/**
	 * Caches the social activity set in the entity cache if it is enabled.
	 *
	 * @param socialActivitySet the social activity set
	 */
	@Override
	public void cacheResult(SocialActivitySet socialActivitySet) {
		entityCache.putResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey(),
			socialActivitySet);

		socialActivitySet.resetOriginalValues();
	}

	/**
	 * Caches the social activity sets in the entity cache if it is enabled.
	 *
	 * @param socialActivitySets the social activity sets
	 */
	@Override
	public void cacheResult(List<SocialActivitySet> socialActivitySets) {
		for (SocialActivitySet socialActivitySet : socialActivitySets) {
			if (entityCache.getResult(
						SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivitySetImpl.class,
						socialActivitySet.getPrimaryKey()) == null) {
				cacheResult(socialActivitySet);
			}
			else {
				socialActivitySet.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity sets.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SocialActivitySetImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity set.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivitySet socialActivitySet) {
		entityCache.removeResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SocialActivitySet> socialActivitySets) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivitySet socialActivitySet : socialActivitySets) {
			entityCache.removeResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey());
		}
	}

	/**
	 * Creates a new social activity set with the primary key. Does not add the social activity set to the database.
	 *
	 * @param activitySetId the primary key for the new social activity set
	 * @return the new social activity set
	 */
	@Override
	public SocialActivitySet create(long activitySetId) {
		SocialActivitySet socialActivitySet = new SocialActivitySetImpl();

		socialActivitySet.setNew(true);
		socialActivitySet.setPrimaryKey(activitySetId);

		socialActivitySet.setCompanyId(companyProvider.getCompanyId());

		return socialActivitySet;
	}

	/**
	 * Removes the social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activitySetId the primary key of the social activity set
	 * @return the social activity set that was removed
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet remove(long activitySetId)
		throws NoSuchActivitySetException {
		return remove((Serializable)activitySetId);
	}

	/**
	 * Removes the social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity set
	 * @return the social activity set that was removed
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet remove(Serializable primaryKey)
		throws NoSuchActivitySetException {
		Session session = null;

		try {
			session = openSession();

			SocialActivitySet socialActivitySet = (SocialActivitySet)session.get(SocialActivitySetImpl.class,
					primaryKey);

			if (socialActivitySet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivitySetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(socialActivitySet);
		}
		catch (NoSuchActivitySetException nsee) {
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
	protected SocialActivitySet removeImpl(SocialActivitySet socialActivitySet) {
		socialActivitySet = toUnwrappedModel(socialActivitySet);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(socialActivitySet)) {
				socialActivitySet = (SocialActivitySet)session.get(SocialActivitySetImpl.class,
						socialActivitySet.getPrimaryKeyObj());
			}

			if (socialActivitySet != null) {
				session.delete(socialActivitySet);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialActivitySet != null) {
			clearCache(socialActivitySet);
		}

		return socialActivitySet;
	}

	@Override
	public SocialActivitySet updateImpl(SocialActivitySet socialActivitySet) {
		socialActivitySet = toUnwrappedModel(socialActivitySet);

		boolean isNew = socialActivitySet.isNew();

		SocialActivitySetModelImpl socialActivitySetModelImpl = (SocialActivitySetModelImpl)socialActivitySet;

		Session session = null;

		try {
			session = openSession();

			if (socialActivitySet.isNew()) {
				session.save(socialActivitySet);

				socialActivitySet.setNew(false);
			}
			else {
				socialActivitySet = (SocialActivitySet)session.merge(socialActivitySet);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialActivitySetModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((socialActivitySetModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivitySetModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { socialActivitySetModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivitySetModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { socialActivitySetModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivitySetModelImpl.getOriginalGroupId(),
						socialActivitySetModelImpl.getOriginalUserId(),
						socialActivitySetModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T,
					args);

				args = new Object[] {
						socialActivitySetModelImpl.getGroupId(),
						socialActivitySetModelImpl.getUserId(),
						socialActivitySetModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T,
					args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivitySetModelImpl.getOriginalClassNameId(),
						socialActivitySetModelImpl.getOriginalClassPK(),
						socialActivitySetModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T,
					args);

				args = new Object[] {
						socialActivitySetModelImpl.getClassNameId(),
						socialActivitySetModelImpl.getClassPK(),
						socialActivitySetModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T,
					args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivitySetModelImpl.getOriginalGroupId(),
						socialActivitySetModelImpl.getOriginalUserId(),
						socialActivitySetModelImpl.getOriginalClassNameId(),
						socialActivitySetModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T,
					args);

				args = new Object[] {
						socialActivitySetModelImpl.getGroupId(),
						socialActivitySetModelImpl.getUserId(),
						socialActivitySetModelImpl.getClassNameId(),
						socialActivitySetModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C_T,
					args);
			}

			if ((socialActivitySetModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivitySetModelImpl.getOriginalUserId(),
						socialActivitySetModelImpl.getOriginalClassNameId(),
						socialActivitySetModelImpl.getOriginalClassPK(),
						socialActivitySetModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_C_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T,
					args);

				args = new Object[] {
						socialActivitySetModelImpl.getUserId(),
						socialActivitySetModelImpl.getClassNameId(),
						socialActivitySetModelImpl.getClassPK(),
						socialActivitySetModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_C_C_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C_C_T,
					args);
			}
		}

		entityCache.putResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey(),
			socialActivitySet, false);

		socialActivitySet.resetOriginalValues();

		return socialActivitySet;
	}

	protected SocialActivitySet toUnwrappedModel(
		SocialActivitySet socialActivitySet) {
		if (socialActivitySet instanceof SocialActivitySetImpl) {
			return socialActivitySet;
		}

		SocialActivitySetImpl socialActivitySetImpl = new SocialActivitySetImpl();

		socialActivitySetImpl.setNew(socialActivitySet.isNew());
		socialActivitySetImpl.setPrimaryKey(socialActivitySet.getPrimaryKey());

		socialActivitySetImpl.setActivitySetId(socialActivitySet.getActivitySetId());
		socialActivitySetImpl.setGroupId(socialActivitySet.getGroupId());
		socialActivitySetImpl.setCompanyId(socialActivitySet.getCompanyId());
		socialActivitySetImpl.setUserId(socialActivitySet.getUserId());
		socialActivitySetImpl.setCreateDate(socialActivitySet.getCreateDate());
		socialActivitySetImpl.setModifiedDate(socialActivitySet.getModifiedDate());
		socialActivitySetImpl.setClassNameId(socialActivitySet.getClassNameId());
		socialActivitySetImpl.setClassPK(socialActivitySet.getClassPK());
		socialActivitySetImpl.setType(socialActivitySet.getType());
		socialActivitySetImpl.setExtraData(socialActivitySet.getExtraData());
		socialActivitySetImpl.setActivityCount(socialActivitySet.getActivityCount());

		return socialActivitySetImpl;
	}

	/**
	 * Returns the social activity set with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity set
	 * @return the social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActivitySetException {
		SocialActivitySet socialActivitySet = fetchByPrimaryKey(primaryKey);

		if (socialActivitySet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActivitySetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return socialActivitySet;
	}

	/**
	 * Returns the social activity set with the primary key or throws a {@link NoSuchActivitySetException} if it could not be found.
	 *
	 * @param activitySetId the primary key of the social activity set
	 * @return the social activity set
	 * @throws NoSuchActivitySetException if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet findByPrimaryKey(long activitySetId)
		throws NoSuchActivitySetException {
		return findByPrimaryKey((Serializable)activitySetId);
	}

	/**
	 * Returns the social activity set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity set
	 * @return the social activity set, or <code>null</code> if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet fetchByPrimaryKey(Serializable primaryKey) {
		SocialActivitySet socialActivitySet = (SocialActivitySet)entityCache.getResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivitySetImpl.class, primaryKey);

		if (socialActivitySet == _nullSocialActivitySet) {
			return null;
		}

		if (socialActivitySet == null) {
			Session session = null;

			try {
				session = openSession();

				socialActivitySet = (SocialActivitySet)session.get(SocialActivitySetImpl.class,
						primaryKey);

				if (socialActivitySet != null) {
					cacheResult(socialActivitySet);
				}
				else {
					entityCache.putResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivitySetImpl.class, primaryKey,
						_nullSocialActivitySet);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivitySetImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return socialActivitySet;
	}

	/**
	 * Returns the social activity set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activitySetId the primary key of the social activity set
	 * @return the social activity set, or <code>null</code> if a social activity set with the primary key could not be found
	 */
	@Override
	public SocialActivitySet fetchByPrimaryKey(long activitySetId) {
		return fetchByPrimaryKey((Serializable)activitySetId);
	}

	@Override
	public Map<Serializable, SocialActivitySet> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SocialActivitySet> map = new HashMap<Serializable, SocialActivitySet>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SocialActivitySet socialActivitySet = fetchByPrimaryKey(primaryKey);

			if (socialActivitySet != null) {
				map.put(primaryKey, socialActivitySet);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			SocialActivitySet socialActivitySet = (SocialActivitySet)entityCache.getResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivitySetImpl.class, primaryKey);

			if (socialActivitySet == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, socialActivitySet);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SOCIALACTIVITYSET_WHERE_PKS_IN);

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

			for (SocialActivitySet socialActivitySet : (List<SocialActivitySet>)q.list()) {
				map.put(socialActivitySet.getPrimaryKeyObj(), socialActivitySet);

				cacheResult(socialActivitySet);

				uncachedPrimaryKeys.remove(socialActivitySet.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivitySetImpl.class, primaryKey,
					_nullSocialActivitySet);
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
	 * Returns all the social activity sets.
	 *
	 * @return the social activity sets
	 */
	@Override
	public List<SocialActivitySet> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of social activity sets
	 */
	@Override
	public List<SocialActivitySet> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity sets
	 */
	@Override
	public List<SocialActivitySet> findAll(int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of social activity sets
	 */
	@Override
	public List<SocialActivitySet> findAll(int start, int end,
		OrderByComparator<SocialActivitySet> orderByComparator,
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

		List<SocialActivitySet> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivitySet>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALACTIVITYSET);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYSET;

				if (pagination) {
					sql = sql.concat(SocialActivitySetModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
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
	 * Removes all the social activity sets from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SocialActivitySet socialActivitySet : findAll()) {
			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets.
	 *
	 * @return the number of social activity sets
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYSET);

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
		return SocialActivitySetModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the social activity set persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SocialActivitySetImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_SOCIALACTIVITYSET = "SELECT socialActivitySet FROM SocialActivitySet socialActivitySet";
	private static final String _SQL_SELECT_SOCIALACTIVITYSET_WHERE_PKS_IN = "SELECT socialActivitySet FROM SocialActivitySet socialActivitySet WHERE activitySetId IN (";
	private static final String _SQL_SELECT_SOCIALACTIVITYSET_WHERE = "SELECT socialActivitySet FROM SocialActivitySet socialActivitySet WHERE ";
	private static final String _SQL_COUNT_SOCIALACTIVITYSET = "SELECT COUNT(socialActivitySet) FROM SocialActivitySet socialActivitySet";
	private static final String _SQL_COUNT_SOCIALACTIVITYSET_WHERE = "SELECT COUNT(socialActivitySet) FROM SocialActivitySet socialActivitySet WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivitySet.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialActivitySet exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialActivitySet exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SocialActivitySetPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
	private static final SocialActivitySet _nullSocialActivitySet = new SocialActivitySetImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialActivitySet> toCacheModel() {
				return _nullSocialActivitySetCacheModel;
			}
		};

	private static final CacheModel<SocialActivitySet> _nullSocialActivitySetCacheModel =
		new CacheModel<SocialActivitySet>() {
			@Override
			public SocialActivitySet toEntityModel() {
				return _nullSocialActivitySet;
			}
		};
}