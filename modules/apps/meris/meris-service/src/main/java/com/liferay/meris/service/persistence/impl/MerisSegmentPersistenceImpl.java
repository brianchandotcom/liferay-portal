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

package com.liferay.meris.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.meris.exception.NoSuchSegmentException;
import com.liferay.meris.model.MerisSegment;
import com.liferay.meris.model.impl.MerisSegmentImpl;
import com.liferay.meris.model.impl.MerisSegmentModelImpl;
import com.liferay.meris.service.persistence.MerisSegmentPersistence;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the meris segment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see MerisSegmentPersistence
 * @see com.liferay.meris.service.persistence.MerisSegmentUtil
 * @generated
 */
@ProviderType
public class MerisSegmentPersistenceImpl extends BasePersistenceImpl<MerisSegment>
	implements MerisSegmentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MerisSegmentUtil} to access the meris segment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MerisSegmentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, MerisSegmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, MerisSegmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, MerisSegmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, MerisSegmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			MerisSegmentModelImpl.GROUPID_COLUMN_BITMASK |
			MerisSegmentModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the meris segments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching meris segments
	 */
	@Override
	public List<MerisSegment> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the meris segments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of meris segments
	 * @param end the upper bound of the range of meris segments (not inclusive)
	 * @return the range of matching meris segments
	 */
	@Override
	public List<MerisSegment> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the meris segments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of meris segments
	 * @param end the upper bound of the range of meris segments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching meris segments
	 */
	@Override
	public List<MerisSegment> findByGroupId(long groupId, int start, int end,
		OrderByComparator<MerisSegment> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the meris segments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of meris segments
	 * @param end the upper bound of the range of meris segments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching meris segments
	 */
	@Override
	public List<MerisSegment> findByGroupId(long groupId, int start, int end,
		OrderByComparator<MerisSegment> orderByComparator,
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

		List<MerisSegment> list = null;

		if (retrieveFromCache) {
			list = (List<MerisSegment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MerisSegment merisSegment : list) {
					if ((groupId != merisSegment.getGroupId())) {
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

			query.append(_SQL_SELECT_MERISSEGMENT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MerisSegmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<MerisSegment>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MerisSegment>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first meris segment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching meris segment
	 * @throws NoSuchSegmentException if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment findByGroupId_First(long groupId,
		OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException {
		MerisSegment merisSegment = fetchByGroupId_First(groupId,
				orderByComparator);

		if (merisSegment != null) {
			return merisSegment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchSegmentException(msg.toString());
	}

	/**
	 * Returns the first meris segment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching meris segment, or <code>null</code> if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment fetchByGroupId_First(long groupId,
		OrderByComparator<MerisSegment> orderByComparator) {
		List<MerisSegment> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last meris segment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching meris segment
	 * @throws NoSuchSegmentException if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment findByGroupId_Last(long groupId,
		OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException {
		MerisSegment merisSegment = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (merisSegment != null) {
			return merisSegment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchSegmentException(msg.toString());
	}

	/**
	 * Returns the last meris segment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching meris segment, or <code>null</code> if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment fetchByGroupId_Last(long groupId,
		OrderByComparator<MerisSegment> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<MerisSegment> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the meris segments before and after the current meris segment in the ordered set where groupId = &#63;.
	 *
	 * @param merisSegmentId the primary key of the current meris segment
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next meris segment
	 * @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	 */
	@Override
	public MerisSegment[] findByGroupId_PrevAndNext(long merisSegmentId,
		long groupId, OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException {
		MerisSegment merisSegment = findByPrimaryKey(merisSegmentId);

		Session session = null;

		try {
			session = openSession();

			MerisSegment[] array = new MerisSegmentImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, merisSegment, groupId,
					orderByComparator, true);

			array[1] = merisSegment;

			array[2] = getByGroupId_PrevAndNext(session, merisSegment, groupId,
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

	protected MerisSegment getByGroupId_PrevAndNext(Session session,
		MerisSegment merisSegment, long groupId,
		OrderByComparator<MerisSegment> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MERISSEGMENT_WHERE);

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
			query.append(MerisSegmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(merisSegment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MerisSegment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the meris segments where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (MerisSegment merisSegment : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(merisSegment);
		}
	}

	/**
	 * Returns the number of meris segments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching meris segments
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MERISSEGMENT_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "merisSegment.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_K = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, MerisSegmentImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_K",
			new String[] { Long.class.getName(), String.class.getName() },
			MerisSegmentModelImpl.GROUPID_COLUMN_BITMASK |
			MerisSegmentModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_K = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_K",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the meris segment where groupId = &#63; and key = &#63; or throws a {@link NoSuchSegmentException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @return the matching meris segment
	 * @throws NoSuchSegmentException if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment findByG_K(long groupId, String key)
		throws NoSuchSegmentException {
		MerisSegment merisSegment = fetchByG_K(groupId, key);

		if (merisSegment == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", key=");
			msg.append(key);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSegmentException(msg.toString());
		}

		return merisSegment;
	}

	/**
	 * Returns the meris segment where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @return the matching meris segment, or <code>null</code> if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment fetchByG_K(long groupId, String key) {
		return fetchByG_K(groupId, key, true);
	}

	/**
	 * Returns the meris segment where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching meris segment, or <code>null</code> if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment fetchByG_K(long groupId, String key,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, key };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_K,
					finderArgs, this);
		}

		if (result instanceof MerisSegment) {
			MerisSegment merisSegment = (MerisSegment)result;

			if ((groupId != merisSegment.getGroupId()) ||
					!Objects.equals(key, merisSegment.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MERISSEGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_K_GROUPID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_G_K_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_G_K_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_G_K_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindKey) {
					qPos.add(key);
				}

				List<MerisSegment> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_K, finderArgs,
						list);
				}
				else {
					MerisSegment merisSegment = list.get(0);

					result = merisSegment;

					cacheResult(merisSegment);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_K, finderArgs);

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
			return (MerisSegment)result;
		}
	}

	/**
	 * Removes the meris segment where groupId = &#63; and key = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @return the meris segment that was removed
	 */
	@Override
	public MerisSegment removeByG_K(long groupId, String key)
		throws NoSuchSegmentException {
		MerisSegment merisSegment = findByG_K(groupId, key);

		return remove(merisSegment);
	}

	/**
	 * Returns the number of meris segments where groupId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @return the number of matching meris segments
	 */
	@Override
	public int countByG_K(long groupId, String key) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_K;

		Object[] finderArgs = new Object[] { groupId, key };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MERISSEGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_K_GROUPID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_G_K_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_G_K_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_G_K_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindKey) {
					qPos.add(key);
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

	private static final String _FINDER_COLUMN_G_K_GROUPID_2 = "merisSegment.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_K_KEY_1 = "merisSegment.key IS NULL";
	private static final String _FINDER_COLUMN_G_K_KEY_2 = "merisSegment.key = ?";
	private static final String _FINDER_COLUMN_G_K_KEY_3 = "(merisSegment.key IS NULL OR merisSegment.key = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, MerisSegmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, MerisSegmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			MerisSegmentModelImpl.GROUPID_COLUMN_BITMASK |
			MerisSegmentModelImpl.ACTIVE_COLUMN_BITMASK |
			MerisSegmentModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the meris segments where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching meris segments
	 */
	@Override
	public List<MerisSegment> findByG_A(long groupId, boolean active) {
		return findByG_A(groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the meris segments where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of meris segments
	 * @param end the upper bound of the range of meris segments (not inclusive)
	 * @return the range of matching meris segments
	 */
	@Override
	public List<MerisSegment> findByG_A(long groupId, boolean active,
		int start, int end) {
		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the meris segments where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of meris segments
	 * @param end the upper bound of the range of meris segments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching meris segments
	 */
	@Override
	public List<MerisSegment> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<MerisSegment> orderByComparator) {
		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the meris segments where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of meris segments
	 * @param end the upper bound of the range of meris segments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching meris segments
	 */
	@Override
	public List<MerisSegment> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<MerisSegment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, active,
					
					start, end, orderByComparator
				};
		}

		List<MerisSegment> list = null;

		if (retrieveFromCache) {
			list = (List<MerisSegment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MerisSegment merisSegment : list) {
					if ((groupId != merisSegment.getGroupId()) ||
							(active != merisSegment.isActive())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MERISSEGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MerisSegmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				if (!pagination) {
					list = (List<MerisSegment>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MerisSegment>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first meris segment in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching meris segment
	 * @throws NoSuchSegmentException if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment findByG_A_First(long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException {
		MerisSegment merisSegment = fetchByG_A_First(groupId, active,
				orderByComparator);

		if (merisSegment != null) {
			return merisSegment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchSegmentException(msg.toString());
	}

	/**
	 * Returns the first meris segment in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching meris segment, or <code>null</code> if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment fetchByG_A_First(long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator) {
		List<MerisSegment> list = findByG_A(groupId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last meris segment in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching meris segment
	 * @throws NoSuchSegmentException if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment findByG_A_Last(long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException {
		MerisSegment merisSegment = fetchByG_A_Last(groupId, active,
				orderByComparator);

		if (merisSegment != null) {
			return merisSegment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchSegmentException(msg.toString());
	}

	/**
	 * Returns the last meris segment in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching meris segment, or <code>null</code> if a matching meris segment could not be found
	 */
	@Override
	public MerisSegment fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator) {
		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<MerisSegment> list = findByG_A(groupId, active, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the meris segments before and after the current meris segment in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param merisSegmentId the primary key of the current meris segment
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next meris segment
	 * @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	 */
	@Override
	public MerisSegment[] findByG_A_PrevAndNext(long merisSegmentId,
		long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException {
		MerisSegment merisSegment = findByPrimaryKey(merisSegmentId);

		Session session = null;

		try {
			session = openSession();

			MerisSegment[] array = new MerisSegmentImpl[3];

			array[0] = getByG_A_PrevAndNext(session, merisSegment, groupId,
					active, orderByComparator, true);

			array[1] = merisSegment;

			array[2] = getByG_A_PrevAndNext(session, merisSegment, groupId,
					active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MerisSegment getByG_A_PrevAndNext(Session session,
		MerisSegment merisSegment, long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_MERISSEGMENT_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

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
			query.append(MerisSegmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(merisSegment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MerisSegment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the meris segments where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		for (MerisSegment merisSegment : findByG_A(groupId, active,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(merisSegment);
		}
	}

	/**
	 * Returns the number of meris segments where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching meris segments
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MERISSEGMENT_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "merisSegment.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 = "merisSegment.active = ?";

	public MerisSegmentPersistenceImpl() {
		setModelClass(MerisSegment.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("key", "key_");
			dbColumnNames.put("active", "active_");
			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the meris segment in the entity cache if it is enabled.
	 *
	 * @param merisSegment the meris segment
	 */
	@Override
	public void cacheResult(MerisSegment merisSegment) {
		entityCache.putResult(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentImpl.class, merisSegment.getPrimaryKey(), merisSegment);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_K,
			new Object[] { merisSegment.getGroupId(), merisSegment.getKey() },
			merisSegment);

		merisSegment.resetOriginalValues();
	}

	/**
	 * Caches the meris segments in the entity cache if it is enabled.
	 *
	 * @param merisSegments the meris segments
	 */
	@Override
	public void cacheResult(List<MerisSegment> merisSegments) {
		for (MerisSegment merisSegment : merisSegments) {
			if (entityCache.getResult(
						MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
						MerisSegmentImpl.class, merisSegment.getPrimaryKey()) == null) {
				cacheResult(merisSegment);
			}
			else {
				merisSegment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all meris segments.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MerisSegmentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the meris segment.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MerisSegment merisSegment) {
		entityCache.removeResult(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentImpl.class, merisSegment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MerisSegmentModelImpl)merisSegment, true);
	}

	@Override
	public void clearCache(List<MerisSegment> merisSegments) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MerisSegment merisSegment : merisSegments) {
			entityCache.removeResult(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
				MerisSegmentImpl.class, merisSegment.getPrimaryKey());

			clearUniqueFindersCache((MerisSegmentModelImpl)merisSegment, true);
		}
	}

	protected void cacheUniqueFindersCache(
		MerisSegmentModelImpl merisSegmentModelImpl) {
		Object[] args = new Object[] {
				merisSegmentModelImpl.getGroupId(),
				merisSegmentModelImpl.getKey()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_K, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_K, args,
			merisSegmentModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		MerisSegmentModelImpl merisSegmentModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					merisSegmentModelImpl.getGroupId(),
					merisSegmentModelImpl.getKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_K, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_K, args);
		}

		if ((merisSegmentModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_K.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					merisSegmentModelImpl.getOriginalGroupId(),
					merisSegmentModelImpl.getOriginalKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_K, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_K, args);
		}
	}

	/**
	 * Creates a new meris segment with the primary key. Does not add the meris segment to the database.
	 *
	 * @param merisSegmentId the primary key for the new meris segment
	 * @return the new meris segment
	 */
	@Override
	public MerisSegment create(long merisSegmentId) {
		MerisSegment merisSegment = new MerisSegmentImpl();

		merisSegment.setNew(true);
		merisSegment.setPrimaryKey(merisSegmentId);

		merisSegment.setCompanyId(companyProvider.getCompanyId());

		return merisSegment;
	}

	/**
	 * Removes the meris segment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param merisSegmentId the primary key of the meris segment
	 * @return the meris segment that was removed
	 * @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	 */
	@Override
	public MerisSegment remove(long merisSegmentId)
		throws NoSuchSegmentException {
		return remove((Serializable)merisSegmentId);
	}

	/**
	 * Removes the meris segment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the meris segment
	 * @return the meris segment that was removed
	 * @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	 */
	@Override
	public MerisSegment remove(Serializable primaryKey)
		throws NoSuchSegmentException {
		Session session = null;

		try {
			session = openSession();

			MerisSegment merisSegment = (MerisSegment)session.get(MerisSegmentImpl.class,
					primaryKey);

			if (merisSegment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSegmentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(merisSegment);
		}
		catch (NoSuchSegmentException nsee) {
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
	protected MerisSegment removeImpl(MerisSegment merisSegment) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(merisSegment)) {
				merisSegment = (MerisSegment)session.get(MerisSegmentImpl.class,
						merisSegment.getPrimaryKeyObj());
			}

			if (merisSegment != null) {
				session.delete(merisSegment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (merisSegment != null) {
			clearCache(merisSegment);
		}

		return merisSegment;
	}

	@Override
	public MerisSegment updateImpl(MerisSegment merisSegment) {
		boolean isNew = merisSegment.isNew();

		if (!(merisSegment instanceof MerisSegmentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(merisSegment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(merisSegment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in merisSegment proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MerisSegment implementation " +
				merisSegment.getClass());
		}

		MerisSegmentModelImpl merisSegmentModelImpl = (MerisSegmentModelImpl)merisSegment;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (merisSegment.getCreateDate() == null)) {
			if (serviceContext == null) {
				merisSegment.setCreateDate(now);
			}
			else {
				merisSegment.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!merisSegmentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				merisSegment.setModifiedDate(now);
			}
			else {
				merisSegment.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (merisSegment.isNew()) {
				session.save(merisSegment);

				merisSegment.setNew(false);
			}
			else {
				merisSegment = (MerisSegment)session.merge(merisSegment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!MerisSegmentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { merisSegmentModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					merisSegmentModelImpl.getGroupId(),
					merisSegmentModelImpl.isActive()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((merisSegmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						merisSegmentModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { merisSegmentModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((merisSegmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						merisSegmentModelImpl.getOriginalGroupId(),
						merisSegmentModelImpl.getOriginalActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						merisSegmentModelImpl.getGroupId(),
						merisSegmentModelImpl.isActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}
		}

		entityCache.putResult(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentImpl.class, merisSegment.getPrimaryKey(), merisSegment,
			false);

		clearUniqueFindersCache(merisSegmentModelImpl, false);
		cacheUniqueFindersCache(merisSegmentModelImpl);

		merisSegment.resetOriginalValues();

		return merisSegment;
	}

	/**
	 * Returns the meris segment with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the meris segment
	 * @return the meris segment
	 * @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	 */
	@Override
	public MerisSegment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSegmentException {
		MerisSegment merisSegment = fetchByPrimaryKey(primaryKey);

		if (merisSegment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSegmentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return merisSegment;
	}

	/**
	 * Returns the meris segment with the primary key or throws a {@link NoSuchSegmentException} if it could not be found.
	 *
	 * @param merisSegmentId the primary key of the meris segment
	 * @return the meris segment
	 * @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	 */
	@Override
	public MerisSegment findByPrimaryKey(long merisSegmentId)
		throws NoSuchSegmentException {
		return findByPrimaryKey((Serializable)merisSegmentId);
	}

	/**
	 * Returns the meris segment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the meris segment
	 * @return the meris segment, or <code>null</code> if a meris segment with the primary key could not be found
	 */
	@Override
	public MerisSegment fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
				MerisSegmentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		MerisSegment merisSegment = (MerisSegment)serializable;

		if (merisSegment == null) {
			Session session = null;

			try {
				session = openSession();

				merisSegment = (MerisSegment)session.get(MerisSegmentImpl.class,
						primaryKey);

				if (merisSegment != null) {
					cacheResult(merisSegment);
				}
				else {
					entityCache.putResult(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
						MerisSegmentImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
					MerisSegmentImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return merisSegment;
	}

	/**
	 * Returns the meris segment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param merisSegmentId the primary key of the meris segment
	 * @return the meris segment, or <code>null</code> if a meris segment with the primary key could not be found
	 */
	@Override
	public MerisSegment fetchByPrimaryKey(long merisSegmentId) {
		return fetchByPrimaryKey((Serializable)merisSegmentId);
	}

	@Override
	public Map<Serializable, MerisSegment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MerisSegment> map = new HashMap<Serializable, MerisSegment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MerisSegment merisSegment = fetchByPrimaryKey(primaryKey);

			if (merisSegment != null) {
				map.put(primaryKey, merisSegment);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
					MerisSegmentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (MerisSegment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_MERISSEGMENT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (MerisSegment merisSegment : (List<MerisSegment>)q.list()) {
				map.put(merisSegment.getPrimaryKeyObj(), merisSegment);

				cacheResult(merisSegment);

				uncachedPrimaryKeys.remove(merisSegment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(MerisSegmentModelImpl.ENTITY_CACHE_ENABLED,
					MerisSegmentImpl.class, primaryKey, nullModel);
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
	 * Returns all the meris segments.
	 *
	 * @return the meris segments
	 */
	@Override
	public List<MerisSegment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the meris segments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of meris segments
	 * @param end the upper bound of the range of meris segments (not inclusive)
	 * @return the range of meris segments
	 */
	@Override
	public List<MerisSegment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the meris segments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of meris segments
	 * @param end the upper bound of the range of meris segments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of meris segments
	 */
	@Override
	public List<MerisSegment> findAll(int start, int end,
		OrderByComparator<MerisSegment> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the meris segments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of meris segments
	 * @param end the upper bound of the range of meris segments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of meris segments
	 */
	@Override
	public List<MerisSegment> findAll(int start, int end,
		OrderByComparator<MerisSegment> orderByComparator,
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

		List<MerisSegment> list = null;

		if (retrieveFromCache) {
			list = (List<MerisSegment>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_MERISSEGMENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MERISSEGMENT;

				if (pagination) {
					sql = sql.concat(MerisSegmentModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MerisSegment>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MerisSegment>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the meris segments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MerisSegment merisSegment : findAll()) {
			remove(merisSegment);
		}
	}

	/**
	 * Returns the number of meris segments.
	 *
	 * @return the number of meris segments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MERISSEGMENT);

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
		return MerisSegmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the meris segment persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(MerisSegmentImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_MERISSEGMENT = "SELECT merisSegment FROM MerisSegment merisSegment";
	private static final String _SQL_SELECT_MERISSEGMENT_WHERE_PKS_IN = "SELECT merisSegment FROM MerisSegment merisSegment WHERE merisSegmentId IN (";
	private static final String _SQL_SELECT_MERISSEGMENT_WHERE = "SELECT merisSegment FROM MerisSegment merisSegment WHERE ";
	private static final String _SQL_COUNT_MERISSEGMENT = "SELECT COUNT(merisSegment) FROM MerisSegment merisSegment";
	private static final String _SQL_COUNT_MERISSEGMENT_WHERE = "SELECT COUNT(merisSegment) FROM MerisSegment merisSegment WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "merisSegment.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MerisSegment exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MerisSegment exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(MerisSegmentPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"key", "active", "type"
			});
}