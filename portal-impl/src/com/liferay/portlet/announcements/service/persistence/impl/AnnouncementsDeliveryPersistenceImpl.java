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

package com.liferay.portlet.announcements.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.announcements.kernel.exception.NoSuchDeliveryException;
import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.announcements.kernel.service.persistence.AnnouncementsDeliveryPersistence;

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
import com.liferay.portal.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the announcements delivery service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDeliveryPersistence
 * @see com.liferay.announcements.kernel.service.persistence.AnnouncementsDeliveryUtil
 * @generated
 */
@ProviderType
public class AnnouncementsDeliveryPersistenceImpl extends BasePersistenceImpl<AnnouncementsDelivery>
	implements AnnouncementsDeliveryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AnnouncementsDeliveryUtil} to access the announcements delivery persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsDeliveryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			AnnouncementsDeliveryModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the announcements deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @return the range of matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end, OrderByComparator<AnnouncementsDelivery> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end, OrderByComparator<AnnouncementsDelivery> orderByComparator,
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

		List<AnnouncementsDelivery> list = null;

		if (retrieveFromCache) {
			list = (List<AnnouncementsDelivery>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnnouncementsDelivery announcementsDelivery : list) {
					if ((userId != announcementsDelivery.getUserId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AnnouncementsDeliveryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
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
	 * Returns the first announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements delivery
	 * @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery findByUserId_First(long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws NoSuchDeliveryException {
		AnnouncementsDelivery announcementsDelivery = fetchByUserId_First(userId,
				orderByComparator);

		if (announcementsDelivery != null) {
			return announcementsDelivery;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDeliveryException(msg.toString());
	}

	/**
	 * Returns the first announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByUserId_First(long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {
		List<AnnouncementsDelivery> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements delivery
	 * @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery findByUserId_Last(long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws NoSuchDeliveryException {
		AnnouncementsDelivery announcementsDelivery = fetchByUserId_Last(userId,
				orderByComparator);

		if (announcementsDelivery != null) {
			return announcementsDelivery;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDeliveryException(msg.toString());
	}

	/**
	 * Returns the last announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByUserId_Last(long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<AnnouncementsDelivery> list = findByUserId(userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements deliveries before and after the current announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param deliveryId the primary key of the current announcements delivery
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements delivery
	 * @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery[] findByUserId_PrevAndNext(long deliveryId,
		long userId, OrderByComparator<AnnouncementsDelivery> orderByComparator)
		throws NoSuchDeliveryException {
		AnnouncementsDelivery announcementsDelivery = findByPrimaryKey(deliveryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsDelivery[] array = new AnnouncementsDeliveryImpl[3];

			array[0] = getByUserId_PrevAndNext(session, announcementsDelivery,
					userId, orderByComparator, true);

			array[1] = announcementsDelivery;

			array[2] = getByUserId_PrevAndNext(session, announcementsDelivery,
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

	protected AnnouncementsDelivery getByUserId_PrevAndNext(Session session,
		AnnouncementsDelivery announcementsDelivery, long userId,
		OrderByComparator<AnnouncementsDelivery> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE);

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
			query.append(AnnouncementsDeliveryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsDelivery);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsDelivery> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the announcements deliveries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (AnnouncementsDelivery announcementsDelivery : findByUserId(
				userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(announcementsDelivery);
		}
	}

	/**
	 * Returns the number of announcements deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching announcements deliveries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "announcementsDelivery.userId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_U_T = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_T",
			new String[] { Long.class.getName(), String.class.getName() },
			AnnouncementsDeliveryModelImpl.USERID_COLUMN_BITMASK |
			AnnouncementsDeliveryModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the announcements delivery where userId = &#63; and type = &#63; or throws a {@link NoSuchDeliveryException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching announcements delivery
	 * @throws NoSuchDeliveryException if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery findByU_T(long userId, String type)
		throws NoSuchDeliveryException {
		AnnouncementsDelivery announcementsDelivery = fetchByU_T(userId, type);

		if (announcementsDelivery == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchDeliveryException(msg.toString());
		}

		return announcementsDelivery;
	}

	/**
	 * Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByU_T(long userId, String type) {
		return fetchByU_T(userId, type, true);
	}

	/**
	 * Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByU_T(long userId, String type,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { userId, type };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_U_T,
					finderArgs, this);
		}

		if (result instanceof AnnouncementsDelivery) {
			AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)result;

			if ((userId != announcementsDelivery.getUserId()) ||
					!Validator.equals(type, announcementsDelivery.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_U_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_U_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindType) {
					qPos.add(type);
				}

				List<AnnouncementsDelivery> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_U_T, finderArgs,
						list);
				}
				else {
					AnnouncementsDelivery announcementsDelivery = list.get(0);

					result = announcementsDelivery;

					cacheResult(announcementsDelivery);

					if ((announcementsDelivery.getUserId() != userId) ||
							(announcementsDelivery.getType() == null) ||
							!announcementsDelivery.getType().equals(type)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_U_T,
							finderArgs, announcementsDelivery);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_U_T, finderArgs);

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
			return (AnnouncementsDelivery)result;
		}
	}

	/**
	 * Removes the announcements delivery where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the announcements delivery that was removed
	 */
	@Override
	public AnnouncementsDelivery removeByU_T(long userId, String type)
		throws NoSuchDeliveryException {
		AnnouncementsDelivery announcementsDelivery = findByU_T(userId, type);

		return remove(announcementsDelivery);
	}

	/**
	 * Returns the number of announcements deliveries where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching announcements deliveries
	 */
	@Override
	public int countByU_T(long userId, String type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_T;

		Object[] finderArgs = new Object[] { userId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_U_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_U_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindType) {
					qPos.add(type);
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

	private static final String _FINDER_COLUMN_U_T_USERID_2 = "announcementsDelivery.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_TYPE_1 = "announcementsDelivery.type IS NULL";
	private static final String _FINDER_COLUMN_U_T_TYPE_2 = "announcementsDelivery.type = ?";
	private static final String _FINDER_COLUMN_U_T_TYPE_3 = "(announcementsDelivery.type IS NULL OR announcementsDelivery.type = '')";

	public AnnouncementsDeliveryPersistenceImpl() {
		setModelClass(AnnouncementsDelivery.class);
	}

	/**
	 * Caches the announcements delivery in the entity cache if it is enabled.
	 *
	 * @param announcementsDelivery the announcements delivery
	 */
	@Override
	public void cacheResult(AnnouncementsDelivery announcementsDelivery) {
		entityCache.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey(), announcementsDelivery);

		finderCache.putResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				announcementsDelivery.getUserId(),
				announcementsDelivery.getType()
			}, announcementsDelivery);

		announcementsDelivery.resetOriginalValues();
	}

	/**
	 * Caches the announcements deliveries in the entity cache if it is enabled.
	 *
	 * @param announcementsDeliveries the announcements deliveries
	 */
	@Override
	public void cacheResult(List<AnnouncementsDelivery> announcementsDeliveries) {
		for (AnnouncementsDelivery announcementsDelivery : announcementsDeliveries) {
			if (entityCache.getResult(
						AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKey()) == null) {
				cacheResult(announcementsDelivery);
			}
			else {
				announcementsDelivery.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all announcements deliveries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AnnouncementsDeliveryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the announcements delivery.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnnouncementsDelivery announcementsDelivery) {
		entityCache.removeResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AnnouncementsDeliveryModelImpl)announcementsDelivery);
	}

	@Override
	public void clearCache(List<AnnouncementsDelivery> announcementsDeliveries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AnnouncementsDelivery announcementsDelivery : announcementsDeliveries) {
			entityCache.removeResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsDeliveryImpl.class,
				announcementsDelivery.getPrimaryKey());

			clearUniqueFindersCache((AnnouncementsDeliveryModelImpl)announcementsDelivery);
		}
	}

	protected void cacheUniqueFindersCache(
		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl,
		boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					announcementsDeliveryModelImpl.getUserId(),
					announcementsDeliveryModelImpl.getType()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_U_T, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_U_T, args,
				announcementsDeliveryModelImpl);
		}
		else {
			if ((announcementsDeliveryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						announcementsDeliveryModelImpl.getUserId(),
						announcementsDeliveryModelImpl.getType()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_U_T, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_U_T, args,
					announcementsDeliveryModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl) {
		Object[] args = new Object[] {
				announcementsDeliveryModelImpl.getUserId(),
				announcementsDeliveryModelImpl.getType()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_U_T, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_U_T, args);

		if ((announcementsDeliveryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_U_T.getColumnBitmask()) != 0) {
			args = new Object[] {
					announcementsDeliveryModelImpl.getOriginalUserId(),
					announcementsDeliveryModelImpl.getOriginalType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_U_T, args);
		}
	}

	/**
	 * Creates a new announcements delivery with the primary key. Does not add the announcements delivery to the database.
	 *
	 * @param deliveryId the primary key for the new announcements delivery
	 * @return the new announcements delivery
	 */
	@Override
	public AnnouncementsDelivery create(long deliveryId) {
		AnnouncementsDelivery announcementsDelivery = new AnnouncementsDeliveryImpl();

		announcementsDelivery.setNew(true);
		announcementsDelivery.setPrimaryKey(deliveryId);

		announcementsDelivery.setCompanyId(companyProvider.getCompanyId());

		return announcementsDelivery;
	}

	/**
	 * Removes the announcements delivery with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deliveryId the primary key of the announcements delivery
	 * @return the announcements delivery that was removed
	 * @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery remove(long deliveryId)
		throws NoSuchDeliveryException {
		return remove((Serializable)deliveryId);
	}

	/**
	 * Removes the announcements delivery with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the announcements delivery
	 * @return the announcements delivery that was removed
	 * @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery remove(Serializable primaryKey)
		throws NoSuchDeliveryException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
					primaryKey);

			if (announcementsDelivery == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDeliveryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(announcementsDelivery);
		}
		catch (NoSuchDeliveryException nsee) {
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
	protected AnnouncementsDelivery removeImpl(
		AnnouncementsDelivery announcementsDelivery) {
		announcementsDelivery = toUnwrappedModel(announcementsDelivery);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(announcementsDelivery)) {
				announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKeyObj());
			}

			if (announcementsDelivery != null) {
				session.delete(announcementsDelivery);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (announcementsDelivery != null) {
			clearCache(announcementsDelivery);
		}

		return announcementsDelivery;
	}

	@Override
	public AnnouncementsDelivery updateImpl(
		AnnouncementsDelivery announcementsDelivery) {
		announcementsDelivery = toUnwrappedModel(announcementsDelivery);

		boolean isNew = announcementsDelivery.isNew();

		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl = (AnnouncementsDeliveryModelImpl)announcementsDelivery;

		Session session = null;

		try {
			session = openSession();

			if (announcementsDelivery.isNew()) {
				session.save(announcementsDelivery);

				announcementsDelivery.setNew(false);
			}
			else {
				announcementsDelivery = (AnnouncementsDelivery)session.merge(announcementsDelivery);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AnnouncementsDeliveryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((announcementsDeliveryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						announcementsDeliveryModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { announcementsDeliveryModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		entityCache.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey(), announcementsDelivery, false);

		clearUniqueFindersCache(announcementsDeliveryModelImpl);
		cacheUniqueFindersCache(announcementsDeliveryModelImpl, isNew);

		announcementsDelivery.resetOriginalValues();

		return announcementsDelivery;
	}

	protected AnnouncementsDelivery toUnwrappedModel(
		AnnouncementsDelivery announcementsDelivery) {
		if (announcementsDelivery instanceof AnnouncementsDeliveryImpl) {
			return announcementsDelivery;
		}

		AnnouncementsDeliveryImpl announcementsDeliveryImpl = new AnnouncementsDeliveryImpl();

		announcementsDeliveryImpl.setNew(announcementsDelivery.isNew());
		announcementsDeliveryImpl.setPrimaryKey(announcementsDelivery.getPrimaryKey());

		announcementsDeliveryImpl.setDeliveryId(announcementsDelivery.getDeliveryId());
		announcementsDeliveryImpl.setCompanyId(announcementsDelivery.getCompanyId());
		announcementsDeliveryImpl.setUserId(announcementsDelivery.getUserId());
		announcementsDeliveryImpl.setType(announcementsDelivery.getType());
		announcementsDeliveryImpl.setEmail(announcementsDelivery.isEmail());
		announcementsDeliveryImpl.setSms(announcementsDelivery.isSms());
		announcementsDeliveryImpl.setWebsite(announcementsDelivery.isWebsite());

		return announcementsDeliveryImpl;
	}

	/**
	 * Returns the announcements delivery with the primary key or throws a {@link com.liferay.portal.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the announcements delivery
	 * @return the announcements delivery
	 * @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDeliveryException {
		AnnouncementsDelivery announcementsDelivery = fetchByPrimaryKey(primaryKey);

		if (announcementsDelivery == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDeliveryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return announcementsDelivery;
	}

	/**
	 * Returns the announcements delivery with the primary key or throws a {@link NoSuchDeliveryException} if it could not be found.
	 *
	 * @param deliveryId the primary key of the announcements delivery
	 * @return the announcements delivery
	 * @throws NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery findByPrimaryKey(long deliveryId)
		throws NoSuchDeliveryException {
		return findByPrimaryKey((Serializable)deliveryId);
	}

	/**
	 * Returns the announcements delivery with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the announcements delivery
	 * @return the announcements delivery, or <code>null</code> if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByPrimaryKey(Serializable primaryKey) {
		AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)entityCache.getResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsDeliveryImpl.class, primaryKey);

		if (announcementsDelivery == _nullAnnouncementsDelivery) {
			return null;
		}

		if (announcementsDelivery == null) {
			Session session = null;

			try {
				session = openSession();

				announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
						primaryKey);

				if (announcementsDelivery != null) {
					cacheResult(announcementsDelivery);
				}
				else {
					entityCache.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsDeliveryImpl.class, primaryKey,
						_nullAnnouncementsDelivery);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
					AnnouncementsDeliveryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return announcementsDelivery;
	}

	/**
	 * Returns the announcements delivery with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deliveryId the primary key of the announcements delivery
	 * @return the announcements delivery, or <code>null</code> if a announcements delivery with the primary key could not be found
	 */
	@Override
	public AnnouncementsDelivery fetchByPrimaryKey(long deliveryId) {
		return fetchByPrimaryKey((Serializable)deliveryId);
	}

	@Override
	public Map<Serializable, AnnouncementsDelivery> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AnnouncementsDelivery> map = new HashMap<Serializable, AnnouncementsDelivery>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AnnouncementsDelivery announcementsDelivery = fetchByPrimaryKey(primaryKey);

			if (announcementsDelivery != null) {
				map.put(primaryKey, announcementsDelivery);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)entityCache.getResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
					AnnouncementsDeliveryImpl.class, primaryKey);

			if (announcementsDelivery == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, announcementsDelivery);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE_PKS_IN);

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

			for (AnnouncementsDelivery announcementsDelivery : (List<AnnouncementsDelivery>)q.list()) {
				map.put(announcementsDelivery.getPrimaryKeyObj(),
					announcementsDelivery);

				cacheResult(announcementsDelivery);

				uncachedPrimaryKeys.remove(announcementsDelivery.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
					AnnouncementsDeliveryImpl.class, primaryKey,
					_nullAnnouncementsDelivery);
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
	 * Returns all the announcements deliveries.
	 *
	 * @return the announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @return the range of announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findAll(int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsDeliveryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of announcements deliveries
	 */
	@Override
	public List<AnnouncementsDelivery> findAll(int start, int end,
		OrderByComparator<AnnouncementsDelivery> orderByComparator,
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

		List<AnnouncementsDelivery> list = null;

		if (retrieveFromCache) {
			list = (List<AnnouncementsDelivery>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ANNOUNCEMENTSDELIVERY;

				if (pagination) {
					sql = sql.concat(AnnouncementsDeliveryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
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
	 * Removes all the announcements deliveries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AnnouncementsDelivery announcementsDelivery : findAll()) {
			remove(announcementsDelivery);
		}
	}

	/**
	 * Returns the number of announcements deliveries.
	 *
	 * @return the number of announcements deliveries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ANNOUNCEMENTSDELIVERY);

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
		return AnnouncementsDeliveryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the announcements delivery persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AnnouncementsDeliveryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_ANNOUNCEMENTSDELIVERY = "SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery";
	private static final String _SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE_PKS_IN = "SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery WHERE deliveryId IN (";
	private static final String _SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE = "SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery WHERE ";
	private static final String _SQL_COUNT_ANNOUNCEMENTSDELIVERY = "SELECT COUNT(announcementsDelivery) FROM AnnouncementsDelivery announcementsDelivery";
	private static final String _SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE = "SELECT COUNT(announcementsDelivery) FROM AnnouncementsDelivery announcementsDelivery WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "announcementsDelivery.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AnnouncementsDelivery exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AnnouncementsDelivery exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AnnouncementsDeliveryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
	private static final AnnouncementsDelivery _nullAnnouncementsDelivery = new AnnouncementsDeliveryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AnnouncementsDelivery> toCacheModel() {
				return _nullAnnouncementsDeliveryCacheModel;
			}
		};

	private static final CacheModel<AnnouncementsDelivery> _nullAnnouncementsDeliveryCacheModel =
		new CacheModel<AnnouncementsDelivery>() {
			@Override
			public AnnouncementsDelivery toEntityModel() {
				return _nullAnnouncementsDelivery;
			}
		};
}