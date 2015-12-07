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

import com.liferay.portal.NoSuchUserTrackerException;
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
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.model.impl.UserTrackerImpl;
import com.liferay.portal.model.impl.UserTrackerModelImpl;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.UserTrackerPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the user tracker service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPersistence
 * @see com.liferay.portal.service.persistence.UserTrackerUtil
 * @generated
 */
@ProviderType
public class UserTrackerPersistenceImpl extends BasePersistenceImpl<UserTracker>
	implements UserTrackerPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserTrackerUtil} to access the user tracker persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserTrackerImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, UserTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, UserTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, UserTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, UserTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			UserTrackerModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the user trackers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching user trackers
	 */
	@Override
	public List<UserTracker> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the user trackers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user trackers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<UserTracker> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user trackers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache) {
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

		List<UserTracker> list = null;

		if (retrieveFromCache) {
			list = (List<UserTracker>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserTracker userTracker : list) {
					if ((companyId != userTracker.getCompanyId())) {
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

			query.append(_SQL_SELECT_USERTRACKER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserTrackerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findByCompanyId_First(long companyId,
		OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserTrackerException(msg.toString());
	}

	/**
	 * Returns the first user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchByCompanyId_First(long companyId,
		OrderByComparator<UserTracker> orderByComparator) {
		List<UserTracker> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findByCompanyId_Last(long companyId,
		OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserTrackerException(msg.toString());
	}

	/**
	 * Returns the last user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchByCompanyId_Last(long companyId,
		OrderByComparator<UserTracker> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<UserTracker> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user trackers before and after the current user tracker in the ordered set where companyId = &#63;.
	 *
	 * @param userTrackerId the primary key of the current user tracker
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker[] findByCompanyId_PrevAndNext(long userTrackerId,
		long companyId, OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		Session session = null;

		try {
			session = openSession();

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, userTracker,
					companyId, orderByComparator, true);

			array[1] = userTracker;

			array[2] = getByCompanyId_PrevAndNext(session, userTracker,
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

	protected UserTracker getByCompanyId_PrevAndNext(Session session,
		UserTracker userTracker, long companyId,
		OrderByComparator<UserTracker> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERTRACKER_WHERE);

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
			query.append(UserTrackerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userTracker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserTracker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user trackers where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (UserTracker userTracker : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(userTracker);
		}
	}

	/**
	 * Returns the number of user trackers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching user trackers
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERTRACKER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "userTracker.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, UserTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, UserTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			UserTrackerModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the user trackers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user trackers
	 */
	@Override
	public List<UserTracker> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByUserId(long userId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user trackers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findByUserId(long userId, int start, int end,
		OrderByComparator<UserTracker> orderByComparator,
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

		List<UserTracker> list = null;

		if (retrieveFromCache) {
			list = (List<UserTracker>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserTracker userTracker : list) {
					if ((userId != userTracker.getUserId())) {
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

			query.append(_SQL_SELECT_USERTRACKER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserTrackerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findByUserId_First(long userId,
		OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = fetchByUserId_First(userId, orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserTrackerException(msg.toString());
	}

	/**
	 * Returns the first user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchByUserId_First(long userId,
		OrderByComparator<UserTracker> orderByComparator) {
		List<UserTracker> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findByUserId_Last(long userId,
		OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = fetchByUserId_Last(userId, orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserTrackerException(msg.toString());
	}

	/**
	 * Returns the last user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchByUserId_Last(long userId,
		OrderByComparator<UserTracker> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<UserTracker> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user trackers before and after the current user tracker in the ordered set where userId = &#63;.
	 *
	 * @param userTrackerId the primary key of the current user tracker
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker[] findByUserId_PrevAndNext(long userTrackerId,
		long userId, OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		Session session = null;

		try {
			session = openSession();

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = getByUserId_PrevAndNext(session, userTracker, userId,
					orderByComparator, true);

			array[1] = userTracker;

			array[2] = getByUserId_PrevAndNext(session, userTracker, userId,
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

	protected UserTracker getByUserId_PrevAndNext(Session session,
		UserTracker userTracker, long userId,
		OrderByComparator<UserTracker> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERTRACKER_WHERE);

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
			query.append(UserTrackerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userTracker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserTracker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user trackers where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (UserTracker userTracker : findByUserId(userId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(userTracker);
		}
	}

	/**
	 * Returns the number of user trackers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user trackers
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERTRACKER_WHERE);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "userTracker.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SESSIONID =
		new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, UserTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySessionId",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SESSIONID =
		new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, UserTrackerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySessionId",
			new String[] { String.class.getName() },
			UserTrackerModelImpl.SESSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SESSIONID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySessionId",
			new String[] { String.class.getName() });

	/**
	 * Returns all the user trackers where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @return the matching user trackers
	 */
	@Override
	public List<UserTracker> findBySessionId(String sessionId) {
		return findBySessionId(sessionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the user trackers where sessionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sessionId the session ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of matching user trackers
	 */
	@Override
	public List<UserTracker> findBySessionId(String sessionId, int start,
		int end) {
		return findBySessionId(sessionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user trackers where sessionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sessionId the session ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findBySessionId(String sessionId, int start,
		int end, OrderByComparator<UserTracker> orderByComparator) {
		return findBySessionId(sessionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user trackers where sessionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sessionId the session ID
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching user trackers
	 */
	@Override
	public List<UserTracker> findBySessionId(String sessionId, int start,
		int end, OrderByComparator<UserTracker> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SESSIONID;
			finderArgs = new Object[] { sessionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SESSIONID;
			finderArgs = new Object[] { sessionId, start, end, orderByComparator };
		}

		List<UserTracker> list = null;

		if (retrieveFromCache) {
			list = (List<UserTracker>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserTracker userTracker : list) {
					if (!Validator.equals(sessionId, userTracker.getSessionId())) {
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

			query.append(_SQL_SELECT_USERTRACKER_WHERE);

			boolean bindSessionId = false;

			if (sessionId == null) {
				query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_1);
			}
			else if (sessionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
			}
			else {
				bindSessionId = true;

				query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserTrackerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSessionId) {
					qPos.add(sessionId);
				}

				if (!pagination) {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findBySessionId_First(String sessionId,
		OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = fetchBySessionId_First(sessionId,
				orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sessionId=");
		msg.append(sessionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserTrackerException(msg.toString());
	}

	/**
	 * Returns the first user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchBySessionId_First(String sessionId,
		OrderByComparator<UserTracker> orderByComparator) {
		List<UserTracker> list = findBySessionId(sessionId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker
	 * @throws NoSuchUserTrackerException if a matching user tracker could not be found
	 */
	@Override
	public UserTracker findBySessionId_Last(String sessionId,
		OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = fetchBySessionId_Last(sessionId,
				orderByComparator);

		if (userTracker != null) {
			return userTracker;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sessionId=");
		msg.append(sessionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserTrackerException(msg.toString());
	}

	/**
	 * Returns the last user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user tracker, or <code>null</code> if a matching user tracker could not be found
	 */
	@Override
	public UserTracker fetchBySessionId_Last(String sessionId,
		OrderByComparator<UserTracker> orderByComparator) {
		int count = countBySessionId(sessionId);

		if (count == 0) {
			return null;
		}

		List<UserTracker> list = findBySessionId(sessionId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user trackers before and after the current user tracker in the ordered set where sessionId = &#63;.
	 *
	 * @param userTrackerId the primary key of the current user tracker
	 * @param sessionId the session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker[] findBySessionId_PrevAndNext(long userTrackerId,
		String sessionId, OrderByComparator<UserTracker> orderByComparator)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		Session session = null;

		try {
			session = openSession();

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = getBySessionId_PrevAndNext(session, userTracker,
					sessionId, orderByComparator, true);

			array[1] = userTracker;

			array[2] = getBySessionId_PrevAndNext(session, userTracker,
					sessionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserTracker getBySessionId_PrevAndNext(Session session,
		UserTracker userTracker, String sessionId,
		OrderByComparator<UserTracker> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERTRACKER_WHERE);

		boolean bindSessionId = false;

		if (sessionId == null) {
			query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_1);
		}
		else if (sessionId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
		}
		else {
			bindSessionId = true;

			query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
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
			query.append(UserTrackerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSessionId) {
			qPos.add(sessionId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userTracker);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserTracker> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user trackers where sessionId = &#63; from the database.
	 *
	 * @param sessionId the session ID
	 */
	@Override
	public void removeBySessionId(String sessionId) {
		for (UserTracker userTracker : findBySessionId(sessionId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(userTracker);
		}
	}

	/**
	 * Returns the number of user trackers where sessionId = &#63;.
	 *
	 * @param sessionId the session ID
	 * @return the number of matching user trackers
	 */
	@Override
	public int countBySessionId(String sessionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SESSIONID;

		Object[] finderArgs = new Object[] { sessionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERTRACKER_WHERE);

			boolean bindSessionId = false;

			if (sessionId == null) {
				query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_1);
			}
			else if (sessionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
			}
			else {
				bindSessionId = true;

				query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSessionId) {
					qPos.add(sessionId);
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

	private static final String _FINDER_COLUMN_SESSIONID_SESSIONID_1 = "userTracker.sessionId IS NULL";
	private static final String _FINDER_COLUMN_SESSIONID_SESSIONID_2 = "userTracker.sessionId = ?";
	private static final String _FINDER_COLUMN_SESSIONID_SESSIONID_3 = "(userTracker.sessionId IS NULL OR userTracker.sessionId = '')";

	public UserTrackerPersistenceImpl() {
		setModelClass(UserTracker.class);
	}

	/**
	 * Caches the user tracker in the entity cache if it is enabled.
	 *
	 * @param userTracker the user tracker
	 */
	@Override
	public void cacheResult(UserTracker userTracker) {
		entityCache.putResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerImpl.class, userTracker.getPrimaryKey(), userTracker);

		userTracker.resetOriginalValues();
	}

	/**
	 * Caches the user trackers in the entity cache if it is enabled.
	 *
	 * @param userTrackers the user trackers
	 */
	@Override
	public void cacheResult(List<UserTracker> userTrackers) {
		for (UserTracker userTracker : userTrackers) {
			if (entityCache.getResult(
						UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
						UserTrackerImpl.class, userTracker.getPrimaryKey()) == null) {
				cacheResult(userTracker);
			}
			else {
				userTracker.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user trackers.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(UserTrackerImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user tracker.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserTracker userTracker) {
		entityCache.removeResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerImpl.class, userTracker.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<UserTracker> userTrackers) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserTracker userTracker : userTrackers) {
			entityCache.removeResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
				UserTrackerImpl.class, userTracker.getPrimaryKey());
		}
	}

	/**
	 * Creates a new user tracker with the primary key. Does not add the user tracker to the database.
	 *
	 * @param userTrackerId the primary key for the new user tracker
	 * @return the new user tracker
	 */
	@Override
	public UserTracker create(long userTrackerId) {
		UserTracker userTracker = new UserTrackerImpl();

		userTracker.setNew(true);
		userTracker.setPrimaryKey(userTrackerId);

		return userTracker;
	}

	/**
	 * Removes the user tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker that was removed
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker remove(long userTrackerId)
		throws NoSuchUserTrackerException {
		return remove((Serializable)userTrackerId);
	}

	/**
	 * Removes the user tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user tracker
	 * @return the user tracker that was removed
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker remove(Serializable primaryKey)
		throws NoSuchUserTrackerException {
		Session session = null;

		try {
			session = openSession();

			UserTracker userTracker = (UserTracker)session.get(UserTrackerImpl.class,
					primaryKey);

			if (userTracker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(userTracker);
		}
		catch (NoSuchUserTrackerException nsee) {
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
	protected UserTracker removeImpl(UserTracker userTracker) {
		userTracker = toUnwrappedModel(userTracker);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userTracker)) {
				userTracker = (UserTracker)session.get(UserTrackerImpl.class,
						userTracker.getPrimaryKeyObj());
			}

			if (userTracker != null) {
				session.delete(userTracker);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (userTracker != null) {
			clearCache(userTracker);
		}

		return userTracker;
	}

	@Override
	public UserTracker updateImpl(UserTracker userTracker) {
		userTracker = toUnwrappedModel(userTracker);

		boolean isNew = userTracker.isNew();

		UserTrackerModelImpl userTrackerModelImpl = (UserTrackerModelImpl)userTracker;

		Session session = null;

		try {
			session = openSession();

			if (userTracker.isNew()) {
				session.save(userTracker);

				userTracker.setNew(false);
			}
			else {
				userTracker = (UserTracker)session.merge(userTracker);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !UserTrackerModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((userTrackerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userTrackerModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { userTrackerModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((userTrackerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userTrackerModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { userTrackerModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((userTrackerModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SESSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userTrackerModelImpl.getOriginalSessionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SESSIONID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SESSIONID,
					args);

				args = new Object[] { userTrackerModelImpl.getSessionId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SESSIONID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SESSIONID,
					args);
			}
		}

		entityCache.putResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerImpl.class, userTracker.getPrimaryKey(), userTracker,
			false);

		userTracker.resetOriginalValues();

		return userTracker;
	}

	protected UserTracker toUnwrappedModel(UserTracker userTracker) {
		if (userTracker instanceof UserTrackerImpl) {
			return userTracker;
		}

		UserTrackerImpl userTrackerImpl = new UserTrackerImpl();

		userTrackerImpl.setNew(userTracker.isNew());
		userTrackerImpl.setPrimaryKey(userTracker.getPrimaryKey());

		userTrackerImpl.setMvccVersion(userTracker.getMvccVersion());
		userTrackerImpl.setUserTrackerId(userTracker.getUserTrackerId());
		userTrackerImpl.setCompanyId(userTracker.getCompanyId());
		userTrackerImpl.setUserId(userTracker.getUserId());
		userTrackerImpl.setModifiedDate(userTracker.getModifiedDate());
		userTrackerImpl.setSessionId(userTracker.getSessionId());
		userTrackerImpl.setRemoteAddr(userTracker.getRemoteAddr());
		userTrackerImpl.setRemoteHost(userTracker.getRemoteHost());
		userTrackerImpl.setUserAgent(userTracker.getUserAgent());

		return userTrackerImpl;
	}

	/**
	 * Returns the user tracker with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user tracker
	 * @return the user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserTrackerException {
		UserTracker userTracker = fetchByPrimaryKey(primaryKey);

		if (userTracker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return userTracker;
	}

	/**
	 * Returns the user tracker with the primary key or throws a {@link NoSuchUserTrackerException} if it could not be found.
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker
	 * @throws NoSuchUserTrackerException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker findByPrimaryKey(long userTrackerId)
		throws NoSuchUserTrackerException {
		return findByPrimaryKey((Serializable)userTrackerId);
	}

	/**
	 * Returns the user tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user tracker
	 * @return the user tracker, or <code>null</code> if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker fetchByPrimaryKey(Serializable primaryKey) {
		UserTracker userTracker = (UserTracker)entityCache.getResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
				UserTrackerImpl.class, primaryKey);

		if (userTracker == _nullUserTracker) {
			return null;
		}

		if (userTracker == null) {
			Session session = null;

			try {
				session = openSession();

				userTracker = (UserTracker)session.get(UserTrackerImpl.class,
						primaryKey);

				if (userTracker != null) {
					cacheResult(userTracker);
				}
				else {
					entityCache.putResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
						UserTrackerImpl.class, primaryKey, _nullUserTracker);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
					UserTrackerImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return userTracker;
	}

	/**
	 * Returns the user tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker, or <code>null</code> if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker fetchByPrimaryKey(long userTrackerId) {
		return fetchByPrimaryKey((Serializable)userTrackerId);
	}

	@Override
	public Map<Serializable, UserTracker> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, UserTracker> map = new HashMap<Serializable, UserTracker>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			UserTracker userTracker = fetchByPrimaryKey(primaryKey);

			if (userTracker != null) {
				map.put(primaryKey, userTracker);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			UserTracker userTracker = (UserTracker)entityCache.getResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
					UserTrackerImpl.class, primaryKey);

			if (userTracker == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, userTracker);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_USERTRACKER_WHERE_PKS_IN);

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

			for (UserTracker userTracker : (List<UserTracker>)q.list()) {
				map.put(userTracker.getPrimaryKeyObj(), userTracker);

				cacheResult(userTracker);

				uncachedPrimaryKeys.remove(userTracker.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
					UserTrackerImpl.class, primaryKey, _nullUserTracker);
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
	 * Returns all the user trackers.
	 *
	 * @return the user trackers
	 */
	@Override
	public List<UserTracker> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of user trackers
	 */
	@Override
	public List<UserTracker> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user trackers
	 */
	@Override
	public List<UserTracker> findAll(int start, int end,
		OrderByComparator<UserTracker> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of user trackers
	 */
	@Override
	public List<UserTracker> findAll(int start, int end,
		OrderByComparator<UserTracker> orderByComparator,
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

		List<UserTracker> list = null;

		if (retrieveFromCache) {
			list = (List<UserTracker>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERTRACKER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERTRACKER;

				if (pagination) {
					sql = sql.concat(UserTrackerModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the user trackers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserTracker userTracker : findAll()) {
			remove(userTracker);
		}
	}

	/**
	 * Returns the number of user trackers.
	 *
	 * @return the number of user trackers
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERTRACKER);

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
		return UserTrackerModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user tracker persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(UserTrackerImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_USERTRACKER = "SELECT userTracker FROM UserTracker userTracker";
	private static final String _SQL_SELECT_USERTRACKER_WHERE_PKS_IN = "SELECT userTracker FROM UserTracker userTracker WHERE userTrackerId IN (";
	private static final String _SQL_SELECT_USERTRACKER_WHERE = "SELECT userTracker FROM UserTracker userTracker WHERE ";
	private static final String _SQL_COUNT_USERTRACKER = "SELECT COUNT(userTracker) FROM UserTracker userTracker";
	private static final String _SQL_COUNT_USERTRACKER_WHERE = "SELECT COUNT(userTracker) FROM UserTracker userTracker WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userTracker.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserTracker exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserTracker exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(UserTrackerPersistenceImpl.class);
	private static final UserTracker _nullUserTracker = new UserTrackerImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserTracker> toCacheModel() {
				return _nullUserTrackerCacheModel;
			}
		};

	private static final CacheModel<UserTracker> _nullUserTrackerCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<UserTracker>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public UserTracker toEntityModel() {
			return _nullUserTracker;
		}
	}
}