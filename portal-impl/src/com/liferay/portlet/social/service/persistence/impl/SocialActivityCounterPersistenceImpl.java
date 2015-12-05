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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchActivityCounterException;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.impl.SocialActivityCounterImpl;
import com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the social activity counter service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounterPersistence
 * @see com.liferay.portlet.social.service.persistence.SocialActivityCounterUtil
 * @generated
 */
@ProviderType
public class SocialActivityCounterPersistenceImpl extends BasePersistenceImpl<SocialActivityCounter>
	implements SocialActivityCounterPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialActivityCounterUtil} to access the social activity counter persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialActivityCounterImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the social activity counters where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SocialActivityCounter> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity counters where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SocialActivityCounter> orderByComparator,
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

		List<SocialActivityCounter> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivityCounter>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivityCounter socialActivityCounter : list) {
					if ((groupId != socialActivityCounter.getGroupId())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
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
	 * Returns the first social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByGroupId_First(long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = fetchByGroupId_First(groupId,
				orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the first social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByGroupId_First(long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		List<SocialActivityCounter> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByGroupId_Last(long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByGroupId_Last(long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SocialActivityCounter> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63;.
	 *
	 * @param activityCounterId the primary key of the current social activity counter
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter[] findByGroupId_PrevAndNext(
		long activityCounterId, long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = findByPrimaryKey(activityCounterId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter[] array = new SocialActivityCounterImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, socialActivityCounter,
					groupId, orderByComparator, true);

			array[1] = socialActivityCounter;

			array[2] = getByGroupId_PrevAndNext(session, socialActivityCounter,
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

	protected SocialActivityCounter getByGroupId_PrevAndNext(Session session,
		SocialActivityCounter socialActivityCounter, long groupId,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

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
			query.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityCounter);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityCounter> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity counters where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SocialActivityCounter socialActivityCounter : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivityCounter);
		}
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "socialActivityCounter.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByC_C(long classNameId, long classPK) {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return findByC_C(classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivityCounter> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivityCounter>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivityCounter socialActivityCounter : list) {
					if ((classNameId != socialActivityCounter.getClassNameId()) ||
							(classPK != socialActivityCounter.getClassPK())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
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
	 * Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByC_C_First(long classNameId,
		long classPK, OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<SocialActivityCounter> orderByComparator) {
		List<SocialActivityCounter> list = findByC_C(classNameId, classPK, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<SocialActivityCounter> orderByComparator) {
		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SocialActivityCounter> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity counters before and after the current social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param activityCounterId the primary key of the current social activity counter
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter[] findByC_C_PrevAndNext(
		long activityCounterId, long classNameId, long classPK,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = findByPrimaryKey(activityCounterId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter[] array = new SocialActivityCounterImpl[3];

			array[0] = getByC_C_PrevAndNext(session, socialActivityCounter,
					classNameId, classPK, orderByComparator, true);

			array[1] = socialActivityCounter;

			array[2] = getByC_C_PrevAndNext(session, socialActivityCounter,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialActivityCounter getByC_C_PrevAndNext(Session session,
		SocialActivityCounter socialActivityCounter, long classNameId,
		long classPK,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityCounter);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityCounter> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity counters where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (SocialActivityCounter socialActivityCounter : findByC_C(
				classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(socialActivityCounter);
		}
	}

	/**
	 * Returns the number of social activity counters where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "socialActivityCounter.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "socialActivityCounter.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_O = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_O",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O =
		new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_O",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.OWNERTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_O = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_O",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @return the matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType) {
		return findByG_C_C_O(groupId, classNameId, classPK, ownerType,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end) {
		return findByG_C_C_O(groupId, classNameId, classPK, ownerType, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return findByG_C_C_O(groupId, classNameId, classPK, ownerType, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O;
			finderArgs = new Object[] { groupId, classNameId, classPK, ownerType };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_O;
			finderArgs = new Object[] {
					groupId, classNameId, classPK, ownerType,
					
					start, end, orderByComparator
				};
		}

		List<SocialActivityCounter> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivityCounter>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SocialActivityCounter socialActivityCounter : list) {
					if ((groupId != socialActivityCounter.getGroupId()) ||
							(classNameId != socialActivityCounter.getClassNameId()) ||
							(classPK != socialActivityCounter.getClassPK()) ||
							(ownerType != socialActivityCounter.getOwnerType())) {
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

			query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_O_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_O_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_O_OWNERTYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(ownerType);

				if (!pagination) {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
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
	 * Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByG_C_C_O_First(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = fetchByG_C_C_O_First(groupId,
				classNameId, classPK, ownerType, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", ownerType=");
		msg.append(ownerType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_O_First(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		List<SocialActivityCounter> list = findByG_C_C_O(groupId, classNameId,
				classPK, ownerType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByG_C_C_O_Last(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = fetchByG_C_C_O_Last(groupId,
				classNameId, classPK, ownerType, orderByComparator);

		if (socialActivityCounter != null) {
			return socialActivityCounter;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", ownerType=");
		msg.append(ownerType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActivityCounterException(msg.toString());
	}

	/**
	 * Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_O_Last(long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		int count = countByG_C_C_O(groupId, classNameId, classPK, ownerType);

		if (count == 0) {
			return null;
		}

		List<SocialActivityCounter> list = findByG_C_C_O(groupId, classNameId,
				classPK, ownerType, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param activityCounterId the primary key of the current social activity counter
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter[] findByG_C_C_O_PrevAndNext(
		long activityCounterId, long groupId, long classNameId, long classPK,
		int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = findByPrimaryKey(activityCounterId);

		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter[] array = new SocialActivityCounterImpl[3];

			array[0] = getByG_C_C_O_PrevAndNext(session, socialActivityCounter,
					groupId, classNameId, classPK, ownerType,
					orderByComparator, true);

			array[1] = socialActivityCounter;

			array[2] = getByG_C_C_O_PrevAndNext(session, socialActivityCounter,
					groupId, classNameId, classPK, ownerType,
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

	protected SocialActivityCounter getByG_C_C_O_PrevAndNext(Session session,
		SocialActivityCounter socialActivityCounter, long groupId,
		long classNameId, long classPK, int ownerType,
		OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_O_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_O_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_O_OWNERTYPE_2);

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
			query.append(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(ownerType);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(socialActivityCounter);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialActivityCounter> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 */
	@Override
	public void removeByG_C_C_O(long groupId, long classNameId, long classPK,
		int ownerType) {
		for (SocialActivityCounter socialActivityCounter : findByG_C_C_O(
				groupId, classNameId, classPK, ownerType, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(socialActivityCounter);
		}
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param ownerType the owner type
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByG_C_C_O(long groupId, long classNameId, long classPK,
		int ownerType) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_O;

		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, ownerType
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_O_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_O_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_O_OWNERTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(ownerType);

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

	private static final String _FINDER_COLUMN_G_C_C_O_GROUPID_2 = "socialActivityCounter.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_O_CLASSNAMEID_2 = "socialActivityCounter.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_O_CLASSPK_2 = "socialActivityCounter.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_O_OWNERTYPE_2 = "socialActivityCounter.ownerType = ? AND socialActivityCounter.endPeriod = -1";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_N_O_S = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_N_O_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.NAME_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.OWNERTYPE_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.STARTPERIOD_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_N_O_S = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_N_O_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or throws a {@link NoSuchActivityCounterException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int startPeriod) throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = fetchByG_C_C_N_O_S(groupId,
				classNameId, classPK, name, ownerType, startPeriod);

		if (socialActivityCounter == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", name=");
			msg.append(name);

			msg.append(", ownerType=");
			msg.append(ownerType);

			msg.append(", startPeriod=");
			msg.append(startPeriod);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityCounterException(msg.toString());
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int startPeriod) {
		return fetchByG_C_C_N_O_S(groupId, classNameId, classPK, name,
			ownerType, startPeriod, true);
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int startPeriod, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, startPeriod
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
					finderArgs, this);
		}

		if (result instanceof SocialActivityCounter) {
			SocialActivityCounter socialActivityCounter = (SocialActivityCounter)result;

			if ((groupId != socialActivityCounter.getGroupId()) ||
					(classNameId != socialActivityCounter.getClassNameId()) ||
					(classPK != socialActivityCounter.getClassPK()) ||
					!Validator.equals(name, socialActivityCounter.getName()) ||
					(ownerType != socialActivityCounter.getOwnerType()) ||
					(startPeriod != socialActivityCounter.getStartPeriod())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(8);

			query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSPK_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_STARTPERIOD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(ownerType);

				qPos.add(startPeriod);

				List<SocialActivityCounter> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
						finderArgs, list);
				}
				else {
					SocialActivityCounter socialActivityCounter = list.get(0);

					result = socialActivityCounter;

					cacheResult(socialActivityCounter);

					if ((socialActivityCounter.getGroupId() != groupId) ||
							(socialActivityCounter.getClassNameId() != classNameId) ||
							(socialActivityCounter.getClassPK() != classPK) ||
							(socialActivityCounter.getName() == null) ||
							!socialActivityCounter.getName().equals(name) ||
							(socialActivityCounter.getOwnerType() != ownerType) ||
							(socialActivityCounter.getStartPeriod() != startPeriod)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
							finderArgs, socialActivityCounter);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
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
			return (SocialActivityCounter)result;
		}
	}

	/**
	 * Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the social activity counter that was removed
	 */
	@Override
	public SocialActivityCounter removeByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int startPeriod) throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = findByG_C_C_N_O_S(groupId,
				classNameId, classPK, name, ownerType, startPeriod);

		return remove(socialActivityCounter);
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByG_C_C_N_O_S(long groupId, long classNameId, long classPK,
		String name, int ownerType, int startPeriod) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_N_O_S;

		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, startPeriod
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_CLASSPK_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_C_C_N_O_S_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_S_STARTPERIOD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(ownerType);

				qPos.add(startPeriod);

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

	private static final String _FINDER_COLUMN_G_C_C_N_O_S_GROUPID_2 = "socialActivityCounter.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_CLASSNAMEID_2 = "socialActivityCounter.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_CLASSPK_2 = "socialActivityCounter.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_NAME_1 = "socialActivityCounter.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_NAME_2 = "socialActivityCounter.name = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_NAME_3 = "(socialActivityCounter.name IS NULL OR socialActivityCounter.name = '') AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_OWNERTYPE_2 = "socialActivityCounter.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_S_STARTPERIOD_2 = "socialActivityCounter.startPeriod = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_N_O_E = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED,
			SocialActivityCounterImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_N_O_E",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			SocialActivityCounterModelImpl.GROUPID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.CLASSPK_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.NAME_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.OWNERTYPE_COLUMN_BITMASK |
			SocialActivityCounterModelImpl.ENDPERIOD_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_N_O_E = new FinderPath(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_N_O_E",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or throws a {@link NoSuchActivityCounterException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the matching social activity counter
	 * @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter findByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int endPeriod) throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = fetchByG_C_C_N_O_E(groupId,
				classNameId, classPK, name, ownerType, endPeriod);

		if (socialActivityCounter == null) {
			StringBundler msg = new StringBundler(14);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", name=");
			msg.append(name);

			msg.append(", ownerType=");
			msg.append(ownerType);

			msg.append(", endPeriod=");
			msg.append(endPeriod);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityCounterException(msg.toString());
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int endPeriod) {
		return fetchByG_C_C_N_O_E(groupId, classNameId, classPK, name,
			ownerType, endPeriod, true);
	}

	/**
	 * Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	 */
	@Override
	public SocialActivityCounter fetchByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int endPeriod, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, endPeriod
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
					finderArgs, this);
		}

		if (result instanceof SocialActivityCounter) {
			SocialActivityCounter socialActivityCounter = (SocialActivityCounter)result;

			if ((groupId != socialActivityCounter.getGroupId()) ||
					(classNameId != socialActivityCounter.getClassNameId()) ||
					(classPK != socialActivityCounter.getClassPK()) ||
					!Validator.equals(name, socialActivityCounter.getName()) ||
					(ownerType != socialActivityCounter.getOwnerType()) ||
					(endPeriod != socialActivityCounter.getEndPeriod())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(8);

			query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSPK_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_ENDPERIOD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(ownerType);

				qPos.add(endPeriod);

				List<SocialActivityCounter> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
						finderArgs, list);
				}
				else {
					SocialActivityCounter socialActivityCounter = list.get(0);

					result = socialActivityCounter;

					cacheResult(socialActivityCounter);

					if ((socialActivityCounter.getGroupId() != groupId) ||
							(socialActivityCounter.getClassNameId() != classNameId) ||
							(socialActivityCounter.getClassPK() != classPK) ||
							(socialActivityCounter.getName() == null) ||
							!socialActivityCounter.getName().equals(name) ||
							(socialActivityCounter.getOwnerType() != ownerType) ||
							(socialActivityCounter.getEndPeriod() != endPeriod)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
							finderArgs, socialActivityCounter);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
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
			return (SocialActivityCounter)result;
		}
	}

	/**
	 * Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the social activity counter that was removed
	 */
	@Override
	public SocialActivityCounter removeByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, String name, int ownerType,
		int endPeriod) throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = findByG_C_C_N_O_E(groupId,
				classNameId, classPK, name, ownerType, endPeriod);

		return remove(socialActivityCounter);
	}

	/**
	 * Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param name the name
	 * @param ownerType the owner type
	 * @param endPeriod the end period
	 * @return the number of matching social activity counters
	 */
	@Override
	public int countByG_C_C_N_O_E(long groupId, long classNameId, long classPK,
		String name, int ownerType, int endPeriod) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_N_O_E;

		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, name, ownerType, endPeriod
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_CLASSPK_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_C_C_N_O_E_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_G_C_C_N_O_E_ENDPERIOD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(ownerType);

				qPos.add(endPeriod);

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

	private static final String _FINDER_COLUMN_G_C_C_N_O_E_GROUPID_2 = "socialActivityCounter.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_CLASSNAMEID_2 = "socialActivityCounter.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_CLASSPK_2 = "socialActivityCounter.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_NAME_1 = "socialActivityCounter.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_NAME_2 = "socialActivityCounter.name = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_NAME_3 = "(socialActivityCounter.name IS NULL OR socialActivityCounter.name = '') AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_OWNERTYPE_2 = "socialActivityCounter.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_N_O_E_ENDPERIOD_2 = "socialActivityCounter.endPeriod = ?";

	public SocialActivityCounterPersistenceImpl() {
		setModelClass(SocialActivityCounter.class);
	}

	/**
	 * Caches the social activity counter in the entity cache if it is enabled.
	 *
	 * @param socialActivityCounter the social activity counter
	 */
	@Override
	public void cacheResult(SocialActivityCounter socialActivityCounter) {
		entityCache.putResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			socialActivityCounter.getPrimaryKey(), socialActivityCounter);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S,
			new Object[] {
				socialActivityCounter.getGroupId(),
				socialActivityCounter.getClassNameId(),
				socialActivityCounter.getClassPK(),
				socialActivityCounter.getName(),
				socialActivityCounter.getOwnerType(),
				socialActivityCounter.getStartPeriod()
			}, socialActivityCounter);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E,
			new Object[] {
				socialActivityCounter.getGroupId(),
				socialActivityCounter.getClassNameId(),
				socialActivityCounter.getClassPK(),
				socialActivityCounter.getName(),
				socialActivityCounter.getOwnerType(),
				socialActivityCounter.getEndPeriod()
			}, socialActivityCounter);

		socialActivityCounter.resetOriginalValues();
	}

	/**
	 * Caches the social activity counters in the entity cache if it is enabled.
	 *
	 * @param socialActivityCounters the social activity counters
	 */
	@Override
	public void cacheResult(List<SocialActivityCounter> socialActivityCounters) {
		for (SocialActivityCounter socialActivityCounter : socialActivityCounters) {
			if (entityCache.getResult(
						SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityCounterImpl.class,
						socialActivityCounter.getPrimaryKey()) == null) {
				cacheResult(socialActivityCounter);
			}
			else {
				socialActivityCounter.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity counters.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SocialActivityCounterImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity counter.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivityCounter socialActivityCounter) {
		entityCache.removeResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			socialActivityCounter.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SocialActivityCounterModelImpl)socialActivityCounter);
	}

	@Override
	public void clearCache(List<SocialActivityCounter> socialActivityCounters) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivityCounter socialActivityCounter : socialActivityCounters) {
			entityCache.removeResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityCounterImpl.class,
				socialActivityCounter.getPrimaryKey());

			clearUniqueFindersCache((SocialActivityCounterModelImpl)socialActivityCounter);
		}
	}

	protected void cacheUniqueFindersCache(
		SocialActivityCounterModelImpl socialActivityCounterModelImpl,
		boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					socialActivityCounterModelImpl.getGroupId(),
					socialActivityCounterModelImpl.getClassNameId(),
					socialActivityCounterModelImpl.getClassPK(),
					socialActivityCounterModelImpl.getName(),
					socialActivityCounterModelImpl.getOwnerType(),
					socialActivityCounterModelImpl.getStartPeriod()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_S, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S, args,
				socialActivityCounterModelImpl);

			args = new Object[] {
					socialActivityCounterModelImpl.getGroupId(),
					socialActivityCounterModelImpl.getClassNameId(),
					socialActivityCounterModelImpl.getClassPK(),
					socialActivityCounterModelImpl.getName(),
					socialActivityCounterModelImpl.getOwnerType(),
					socialActivityCounterModelImpl.getEndPeriod()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_E, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E, args,
				socialActivityCounterModelImpl);
		}
		else {
			if ((socialActivityCounterModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_C_C_N_O_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityCounterModelImpl.getGroupId(),
						socialActivityCounterModelImpl.getClassNameId(),
						socialActivityCounterModelImpl.getClassPK(),
						socialActivityCounterModelImpl.getName(),
						socialActivityCounterModelImpl.getOwnerType(),
						socialActivityCounterModelImpl.getStartPeriod()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_S, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S, args,
					socialActivityCounterModelImpl);
			}

			if ((socialActivityCounterModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_C_C_N_O_E.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityCounterModelImpl.getGroupId(),
						socialActivityCounterModelImpl.getClassNameId(),
						socialActivityCounterModelImpl.getClassPK(),
						socialActivityCounterModelImpl.getName(),
						socialActivityCounterModelImpl.getOwnerType(),
						socialActivityCounterModelImpl.getEndPeriod()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_E, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E, args,
					socialActivityCounterModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		SocialActivityCounterModelImpl socialActivityCounterModelImpl) {
		Object[] args = new Object[] {
				socialActivityCounterModelImpl.getGroupId(),
				socialActivityCounterModelImpl.getClassNameId(),
				socialActivityCounterModelImpl.getClassPK(),
				socialActivityCounterModelImpl.getName(),
				socialActivityCounterModelImpl.getOwnerType(),
				socialActivityCounterModelImpl.getStartPeriod()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_S, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S, args);

		if ((socialActivityCounterModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C_N_O_S.getColumnBitmask()) != 0) {
			args = new Object[] {
					socialActivityCounterModelImpl.getOriginalGroupId(),
					socialActivityCounterModelImpl.getOriginalClassNameId(),
					socialActivityCounterModelImpl.getOriginalClassPK(),
					socialActivityCounterModelImpl.getOriginalName(),
					socialActivityCounterModelImpl.getOriginalOwnerType(),
					socialActivityCounterModelImpl.getOriginalStartPeriod()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_S, args);
		}

		args = new Object[] {
				socialActivityCounterModelImpl.getGroupId(),
				socialActivityCounterModelImpl.getClassNameId(),
				socialActivityCounterModelImpl.getClassPK(),
				socialActivityCounterModelImpl.getName(),
				socialActivityCounterModelImpl.getOwnerType(),
				socialActivityCounterModelImpl.getEndPeriod()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_E, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E, args);

		if ((socialActivityCounterModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C_N_O_E.getColumnBitmask()) != 0) {
			args = new Object[] {
					socialActivityCounterModelImpl.getOriginalGroupId(),
					socialActivityCounterModelImpl.getOriginalClassNameId(),
					socialActivityCounterModelImpl.getOriginalClassPK(),
					socialActivityCounterModelImpl.getOriginalName(),
					socialActivityCounterModelImpl.getOriginalOwnerType(),
					socialActivityCounterModelImpl.getOriginalEndPeriod()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_N_O_E, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_N_O_E, args);
		}
	}

	/**
	 * Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	 *
	 * @param activityCounterId the primary key for the new social activity counter
	 * @return the new social activity counter
	 */
	@Override
	public SocialActivityCounter create(long activityCounterId) {
		SocialActivityCounter socialActivityCounter = new SocialActivityCounterImpl();

		socialActivityCounter.setNew(true);
		socialActivityCounter.setPrimaryKey(activityCounterId);

		return socialActivityCounter;
	}

	/**
	 * Removes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter that was removed
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter remove(long activityCounterId)
		throws NoSuchActivityCounterException {
		return remove((Serializable)activityCounterId);
	}

	/**
	 * Removes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity counter
	 * @return the social activity counter that was removed
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter remove(Serializable primaryKey)
		throws NoSuchActivityCounterException {
		Session session = null;

		try {
			session = openSession();

			SocialActivityCounter socialActivityCounter = (SocialActivityCounter)session.get(SocialActivityCounterImpl.class,
					primaryKey);

			if (socialActivityCounter == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivityCounterException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(socialActivityCounter);
		}
		catch (NoSuchActivityCounterException nsee) {
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
	protected SocialActivityCounter removeImpl(
		SocialActivityCounter socialActivityCounter) {
		socialActivityCounter = toUnwrappedModel(socialActivityCounter);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(socialActivityCounter)) {
				socialActivityCounter = (SocialActivityCounter)session.get(SocialActivityCounterImpl.class,
						socialActivityCounter.getPrimaryKeyObj());
			}

			if (socialActivityCounter != null) {
				session.delete(socialActivityCounter);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialActivityCounter != null) {
			clearCache(socialActivityCounter);
		}

		return socialActivityCounter;
	}

	@Override
	public SocialActivityCounter updateImpl(
		SocialActivityCounter socialActivityCounter) {
		socialActivityCounter = toUnwrappedModel(socialActivityCounter);

		boolean isNew = socialActivityCounter.isNew();

		SocialActivityCounterModelImpl socialActivityCounterModelImpl = (SocialActivityCounterModelImpl)socialActivityCounter;

		Session session = null;

		try {
			session = openSession();

			if (socialActivityCounter.isNew()) {
				session.save(socialActivityCounter);

				socialActivityCounter.setNew(false);
			}
			else {
				socialActivityCounter = (SocialActivityCounter)session.merge(socialActivityCounter);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialActivityCounterModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((socialActivityCounterModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityCounterModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { socialActivityCounterModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((socialActivityCounterModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityCounterModelImpl.getOriginalClassNameId(),
						socialActivityCounterModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						socialActivityCounterModelImpl.getClassNameId(),
						socialActivityCounterModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((socialActivityCounterModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						socialActivityCounterModelImpl.getOriginalGroupId(),
						socialActivityCounterModelImpl.getOriginalClassNameId(),
						socialActivityCounterModelImpl.getOriginalClassPK(),
						socialActivityCounterModelImpl.getOriginalOwnerType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_O, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O,
					args);

				args = new Object[] {
						socialActivityCounterModelImpl.getGroupId(),
						socialActivityCounterModelImpl.getClassNameId(),
						socialActivityCounterModelImpl.getClassPK(),
						socialActivityCounterModelImpl.getOwnerType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_O, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_O,
					args);
			}
		}

		entityCache.putResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivityCounterImpl.class,
			socialActivityCounter.getPrimaryKey(), socialActivityCounter, false);

		clearUniqueFindersCache(socialActivityCounterModelImpl);
		cacheUniqueFindersCache(socialActivityCounterModelImpl, isNew);

		socialActivityCounter.resetOriginalValues();

		return socialActivityCounter;
	}

	protected SocialActivityCounter toUnwrappedModel(
		SocialActivityCounter socialActivityCounter) {
		if (socialActivityCounter instanceof SocialActivityCounterImpl) {
			return socialActivityCounter;
		}

		SocialActivityCounterImpl socialActivityCounterImpl = new SocialActivityCounterImpl();

		socialActivityCounterImpl.setNew(socialActivityCounter.isNew());
		socialActivityCounterImpl.setPrimaryKey(socialActivityCounter.getPrimaryKey());

		socialActivityCounterImpl.setActivityCounterId(socialActivityCounter.getActivityCounterId());
		socialActivityCounterImpl.setGroupId(socialActivityCounter.getGroupId());
		socialActivityCounterImpl.setCompanyId(socialActivityCounter.getCompanyId());
		socialActivityCounterImpl.setClassNameId(socialActivityCounter.getClassNameId());
		socialActivityCounterImpl.setClassPK(socialActivityCounter.getClassPK());
		socialActivityCounterImpl.setName(socialActivityCounter.getName());
		socialActivityCounterImpl.setOwnerType(socialActivityCounter.getOwnerType());
		socialActivityCounterImpl.setCurrentValue(socialActivityCounter.getCurrentValue());
		socialActivityCounterImpl.setTotalValue(socialActivityCounter.getTotalValue());
		socialActivityCounterImpl.setGraceValue(socialActivityCounter.getGraceValue());
		socialActivityCounterImpl.setStartPeriod(socialActivityCounter.getStartPeriod());
		socialActivityCounterImpl.setEndPeriod(socialActivityCounter.getEndPeriod());
		socialActivityCounterImpl.setActive(socialActivityCounter.isActive());

		return socialActivityCounterImpl;
	}

	/**
	 * Returns the social activity counter with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity counter
	 * @return the social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActivityCounterException {
		SocialActivityCounter socialActivityCounter = fetchByPrimaryKey(primaryKey);

		if (socialActivityCounter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActivityCounterException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter with the primary key or throws a {@link NoSuchActivityCounterException} if it could not be found.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter
	 * @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter findByPrimaryKey(long activityCounterId)
		throws NoSuchActivityCounterException {
		return findByPrimaryKey((Serializable)activityCounterId);
	}

	/**
	 * Returns the social activity counter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity counter
	 * @return the social activity counter, or <code>null</code> if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter fetchByPrimaryKey(Serializable primaryKey) {
		SocialActivityCounter socialActivityCounter = (SocialActivityCounter)entityCache.getResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivityCounterImpl.class, primaryKey);

		if (socialActivityCounter == _nullSocialActivityCounter) {
			return null;
		}

		if (socialActivityCounter == null) {
			Session session = null;

			try {
				session = openSession();

				socialActivityCounter = (SocialActivityCounter)session.get(SocialActivityCounterImpl.class,
						primaryKey);

				if (socialActivityCounter != null) {
					cacheResult(socialActivityCounter);
				}
				else {
					entityCache.putResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivityCounterImpl.class, primaryKey,
						_nullSocialActivityCounter);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivityCounterImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return socialActivityCounter;
	}

	/**
	 * Returns the social activity counter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter, or <code>null</code> if a social activity counter with the primary key could not be found
	 */
	@Override
	public SocialActivityCounter fetchByPrimaryKey(long activityCounterId) {
		return fetchByPrimaryKey((Serializable)activityCounterId);
	}

	@Override
	public Map<Serializable, SocialActivityCounter> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SocialActivityCounter> map = new HashMap<Serializable, SocialActivityCounter>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SocialActivityCounter socialActivityCounter = fetchByPrimaryKey(primaryKey);

			if (socialActivityCounter != null) {
				map.put(primaryKey, socialActivityCounter);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			SocialActivityCounter socialActivityCounter = (SocialActivityCounter)entityCache.getResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivityCounterImpl.class, primaryKey);

			if (socialActivityCounter == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, socialActivityCounter);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE_PKS_IN);

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

			for (SocialActivityCounter socialActivityCounter : (List<SocialActivityCounter>)q.list()) {
				map.put(socialActivityCounter.getPrimaryKeyObj(),
					socialActivityCounter);

				cacheResult(socialActivityCounter);

				uncachedPrimaryKeys.remove(socialActivityCounter.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SocialActivityCounterModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivityCounterImpl.class, primaryKey,
					_nullSocialActivityCounter);
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
	 * Returns all the social activity counters.
	 *
	 * @return the social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findAll(int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the social activity counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of social activity counters
	 */
	@Override
	public List<SocialActivityCounter> findAll(int start, int end,
		OrderByComparator<SocialActivityCounter> orderByComparator,
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

		List<SocialActivityCounter> list = null;

		if (retrieveFromCache) {
			list = (List<SocialActivityCounter>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALACTIVITYCOUNTER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYCOUNTER;

				if (pagination) {
					sql = sql.concat(SocialActivityCounterModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SocialActivityCounter>)QueryUtil.list(q,
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
	 * Removes all the social activity counters from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SocialActivityCounter socialActivityCounter : findAll()) {
			remove(socialActivityCounter);
		}
	}

	/**
	 * Returns the number of social activity counters.
	 *
	 * @return the number of social activity counters
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYCOUNTER);

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
		return SocialActivityCounterModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the social activity counter persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SocialActivityCounterImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_SOCIALACTIVITYCOUNTER = "SELECT socialActivityCounter FROM SocialActivityCounter socialActivityCounter";
	private static final String _SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE_PKS_IN = "SELECT socialActivityCounter FROM SocialActivityCounter socialActivityCounter WHERE activityCounterId IN (";
	private static final String _SQL_SELECT_SOCIALACTIVITYCOUNTER_WHERE = "SELECT socialActivityCounter FROM SocialActivityCounter socialActivityCounter WHERE ";
	private static final String _SQL_COUNT_SOCIALACTIVITYCOUNTER = "SELECT COUNT(socialActivityCounter) FROM SocialActivityCounter socialActivityCounter";
	private static final String _SQL_COUNT_SOCIALACTIVITYCOUNTER_WHERE = "SELECT COUNT(socialActivityCounter) FROM SocialActivityCounter socialActivityCounter WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivityCounter.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialActivityCounter exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialActivityCounter exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SocialActivityCounterPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
	private static final SocialActivityCounter _nullSocialActivityCounter = new SocialActivityCounterImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialActivityCounter> toCacheModel() {
				return _nullSocialActivityCounterCacheModel;
			}
		};

	private static final CacheModel<SocialActivityCounter> _nullSocialActivityCounterCacheModel =
		new CacheModel<SocialActivityCounter>() {
			@Override
			public SocialActivityCounter toEntityModel() {
				return _nullSocialActivityCounter;
			}
		};
}