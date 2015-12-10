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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

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

import com.liferay.portlet.documentlibrary.NoSuchSyncEventException;
import com.liferay.portlet.documentlibrary.model.DLSyncEvent;
import com.liferay.portlet.documentlibrary.model.impl.DLSyncEventImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLSyncEventModelImpl;
import com.liferay.portlet.documentlibrary.service.persistence.DLSyncEventPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the d l sync event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.DLSyncEventUtil
 * @generated
 */
@ProviderType
public class DLSyncEventPersistenceImpl extends BasePersistenceImpl<DLSyncEvent>
	implements DLSyncEventPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLSyncEventUtil} to access the d l sync event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLSyncEventImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventModelImpl.FINDER_CACHE_ENABLED, DLSyncEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventModelImpl.FINDER_CACHE_ENABLED, DLSyncEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_MODIFIEDTIME =
		new FinderPath(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventModelImpl.FINDER_CACHE_ENABLED, DLSyncEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByModifiedTime",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_MODIFIEDTIME =
		new FinderPath(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByModifiedTime",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d l sync events where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @return the matching d l sync events
	 */
	@Override
	public List<DLSyncEvent> findByModifiedTime(long modifiedTime) {
		return findByModifiedTime(modifiedTime, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d l sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of d l sync events
	 * @param end the upper bound of the range of d l sync events (not inclusive)
	 * @return the range of matching d l sync events
	 */
	@Override
	public List<DLSyncEvent> findByModifiedTime(long modifiedTime, int start,
		int end) {
		return findByModifiedTime(modifiedTime, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d l sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of d l sync events
	 * @param end the upper bound of the range of d l sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d l sync events
	 */
	@Override
	public List<DLSyncEvent> findByModifiedTime(long modifiedTime, int start,
		int end, OrderByComparator<DLSyncEvent> orderByComparator) {
		return findByModifiedTime(modifiedTime, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the d l sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of d l sync events
	 * @param end the upper bound of the range of d l sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d l sync events
	 */
	@Override
	public List<DLSyncEvent> findByModifiedTime(long modifiedTime, int start,
		int end, OrderByComparator<DLSyncEvent> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_MODIFIEDTIME;
		finderArgs = new Object[] { modifiedTime, start, end, orderByComparator };

		List<DLSyncEvent> list = null;

		if (retrieveFromCache) {
			list = (List<DLSyncEvent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLSyncEvent dlSyncEvent : list) {
					if ((modifiedTime >= dlSyncEvent.getModifiedTime())) {
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

			query.append(_SQL_SELECT_DLSYNCEVENT_WHERE);

			query.append(_FINDER_COLUMN_MODIFIEDTIME_MODIFIEDTIME_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLSyncEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modifiedTime);

				if (!pagination) {
					list = (List<DLSyncEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLSyncEvent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first d l sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d l sync event
	 * @throws NoSuchSyncEventException if a matching d l sync event could not be found
	 */
	@Override
	public DLSyncEvent findByModifiedTime_First(long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator)
		throws NoSuchSyncEventException {
		DLSyncEvent dlSyncEvent = fetchByModifiedTime_First(modifiedTime,
				orderByComparator);

		if (dlSyncEvent != null) {
			return dlSyncEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modifiedTime=");
		msg.append(modifiedTime);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSyncEventException(msg.toString());
	}

	/**
	 * Returns the first d l sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	 */
	@Override
	public DLSyncEvent fetchByModifiedTime_First(long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator) {
		List<DLSyncEvent> list = findByModifiedTime(modifiedTime, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d l sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d l sync event
	 * @throws NoSuchSyncEventException if a matching d l sync event could not be found
	 */
	@Override
	public DLSyncEvent findByModifiedTime_Last(long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator)
		throws NoSuchSyncEventException {
		DLSyncEvent dlSyncEvent = fetchByModifiedTime_Last(modifiedTime,
				orderByComparator);

		if (dlSyncEvent != null) {
			return dlSyncEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modifiedTime=");
		msg.append(modifiedTime);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSyncEventException(msg.toString());
	}

	/**
	 * Returns the last d l sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	 */
	@Override
	public DLSyncEvent fetchByModifiedTime_Last(long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator) {
		int count = countByModifiedTime(modifiedTime);

		if (count == 0) {
			return null;
		}

		List<DLSyncEvent> list = findByModifiedTime(modifiedTime, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d l sync events before and after the current d l sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param syncEventId the primary key of the current d l sync event
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d l sync event
	 * @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent[] findByModifiedTime_PrevAndNext(long syncEventId,
		long modifiedTime, OrderByComparator<DLSyncEvent> orderByComparator)
		throws NoSuchSyncEventException {
		DLSyncEvent dlSyncEvent = findByPrimaryKey(syncEventId);

		Session session = null;

		try {
			session = openSession();

			DLSyncEvent[] array = new DLSyncEventImpl[3];

			array[0] = getByModifiedTime_PrevAndNext(session, dlSyncEvent,
					modifiedTime, orderByComparator, true);

			array[1] = dlSyncEvent;

			array[2] = getByModifiedTime_PrevAndNext(session, dlSyncEvent,
					modifiedTime, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLSyncEvent getByModifiedTime_PrevAndNext(Session session,
		DLSyncEvent dlSyncEvent, long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLSYNCEVENT_WHERE);

		query.append(_FINDER_COLUMN_MODIFIEDTIME_MODIFIEDTIME_2);

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
			query.append(DLSyncEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(modifiedTime);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlSyncEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLSyncEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d l sync events where modifiedTime &gt; &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 */
	@Override
	public void removeByModifiedTime(long modifiedTime) {
		for (DLSyncEvent dlSyncEvent : findByModifiedTime(modifiedTime,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlSyncEvent);
		}
	}

	/**
	 * Returns the number of d l sync events where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @return the number of matching d l sync events
	 */
	@Override
	public int countByModifiedTime(long modifiedTime) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_MODIFIEDTIME;

		Object[] finderArgs = new Object[] { modifiedTime };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLSYNCEVENT_WHERE);

			query.append(_FINDER_COLUMN_MODIFIEDTIME_MODIFIEDTIME_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modifiedTime);

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

	private static final String _FINDER_COLUMN_MODIFIEDTIME_MODIFIEDTIME_2 = "dlSyncEvent.modifiedTime > ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_TYPEPK = new FinderPath(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventModelImpl.FINDER_CACHE_ENABLED, DLSyncEventImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByTypePK",
			new String[] { Long.class.getName() },
			DLSyncEventModelImpl.TYPEPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TYPEPK = new FinderPath(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTypePK",
			new String[] { Long.class.getName() });

	/**
	 * Returns the d l sync event where typePK = &#63; or throws a {@link NoSuchSyncEventException} if it could not be found.
	 *
	 * @param typePK the type p k
	 * @return the matching d l sync event
	 * @throws NoSuchSyncEventException if a matching d l sync event could not be found
	 */
	@Override
	public DLSyncEvent findByTypePK(long typePK)
		throws NoSuchSyncEventException {
		DLSyncEvent dlSyncEvent = fetchByTypePK(typePK);

		if (dlSyncEvent == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("typePK=");
			msg.append(typePK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchSyncEventException(msg.toString());
		}

		return dlSyncEvent;
	}

	/**
	 * Returns the d l sync event where typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param typePK the type p k
	 * @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	 */
	@Override
	public DLSyncEvent fetchByTypePK(long typePK) {
		return fetchByTypePK(typePK, true);
	}

	/**
	 * Returns the d l sync event where typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param typePK the type p k
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d l sync event, or <code>null</code> if a matching d l sync event could not be found
	 */
	@Override
	public DLSyncEvent fetchByTypePK(long typePK, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { typePK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_TYPEPK,
					finderArgs, this);
		}

		if (result instanceof DLSyncEvent) {
			DLSyncEvent dlSyncEvent = (DLSyncEvent)result;

			if ((typePK != dlSyncEvent.getTypePK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DLSYNCEVENT_WHERE);

			query.append(_FINDER_COLUMN_TYPEPK_TYPEPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(typePK);

				List<DLSyncEvent> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_TYPEPK,
						finderArgs, list);
				}
				else {
					DLSyncEvent dlSyncEvent = list.get(0);

					result = dlSyncEvent;

					cacheResult(dlSyncEvent);

					if ((dlSyncEvent.getTypePK() != typePK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_TYPEPK,
							finderArgs, dlSyncEvent);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_TYPEPK, finderArgs);

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
			return (DLSyncEvent)result;
		}
	}

	/**
	 * Removes the d l sync event where typePK = &#63; from the database.
	 *
	 * @param typePK the type p k
	 * @return the d l sync event that was removed
	 */
	@Override
	public DLSyncEvent removeByTypePK(long typePK)
		throws NoSuchSyncEventException {
		DLSyncEvent dlSyncEvent = findByTypePK(typePK);

		return remove(dlSyncEvent);
	}

	/**
	 * Returns the number of d l sync events where typePK = &#63;.
	 *
	 * @param typePK the type p k
	 * @return the number of matching d l sync events
	 */
	@Override
	public int countByTypePK(long typePK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TYPEPK;

		Object[] finderArgs = new Object[] { typePK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLSYNCEVENT_WHERE);

			query.append(_FINDER_COLUMN_TYPEPK_TYPEPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(typePK);

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

	private static final String _FINDER_COLUMN_TYPEPK_TYPEPK_2 = "dlSyncEvent.typePK = ?";

	public DLSyncEventPersistenceImpl() {
		setModelClass(DLSyncEvent.class);
	}

	/**
	 * Caches the d l sync event in the entity cache if it is enabled.
	 *
	 * @param dlSyncEvent the d l sync event
	 */
	@Override
	public void cacheResult(DLSyncEvent dlSyncEvent) {
		entityCache.putResult(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventImpl.class, dlSyncEvent.getPrimaryKey(), dlSyncEvent);

		finderCache.putResult(FINDER_PATH_FETCH_BY_TYPEPK,
			new Object[] { dlSyncEvent.getTypePK() }, dlSyncEvent);

		dlSyncEvent.resetOriginalValues();
	}

	/**
	 * Caches the d l sync events in the entity cache if it is enabled.
	 *
	 * @param dlSyncEvents the d l sync events
	 */
	@Override
	public void cacheResult(List<DLSyncEvent> dlSyncEvents) {
		for (DLSyncEvent dlSyncEvent : dlSyncEvents) {
			if (entityCache.getResult(
						DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
						DLSyncEventImpl.class, dlSyncEvent.getPrimaryKey()) == null) {
				cacheResult(dlSyncEvent);
			}
			else {
				dlSyncEvent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d l sync events.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLSyncEventImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d l sync event.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLSyncEvent dlSyncEvent) {
		entityCache.removeResult(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventImpl.class, dlSyncEvent.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLSyncEventModelImpl)dlSyncEvent);
	}

	@Override
	public void clearCache(List<DLSyncEvent> dlSyncEvents) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLSyncEvent dlSyncEvent : dlSyncEvents) {
			entityCache.removeResult(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
				DLSyncEventImpl.class, dlSyncEvent.getPrimaryKey());

			clearUniqueFindersCache((DLSyncEventModelImpl)dlSyncEvent);
		}
	}

	protected void cacheUniqueFindersCache(
		DLSyncEventModelImpl dlSyncEventModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] { dlSyncEventModelImpl.getTypePK() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_TYPEPK, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_TYPEPK, args,
				dlSyncEventModelImpl);
		}
		else {
			if ((dlSyncEventModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_TYPEPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { dlSyncEventModelImpl.getTypePK() };

				finderCache.putResult(FINDER_PATH_COUNT_BY_TYPEPK, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_TYPEPK, args,
					dlSyncEventModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DLSyncEventModelImpl dlSyncEventModelImpl) {
		Object[] args = new Object[] { dlSyncEventModelImpl.getTypePK() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_TYPEPK, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_TYPEPK, args);

		if ((dlSyncEventModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_TYPEPK.getColumnBitmask()) != 0) {
			args = new Object[] { dlSyncEventModelImpl.getOriginalTypePK() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_TYPEPK, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_TYPEPK, args);
		}
	}

	/**
	 * Creates a new d l sync event with the primary key. Does not add the d l sync event to the database.
	 *
	 * @param syncEventId the primary key for the new d l sync event
	 * @return the new d l sync event
	 */
	@Override
	public DLSyncEvent create(long syncEventId) {
		DLSyncEvent dlSyncEvent = new DLSyncEventImpl();

		dlSyncEvent.setNew(true);
		dlSyncEvent.setPrimaryKey(syncEventId);

		dlSyncEvent.setCompanyId(companyProvider.getCompanyId());

		return dlSyncEvent;
	}

	/**
	 * Removes the d l sync event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncEventId the primary key of the d l sync event
	 * @return the d l sync event that was removed
	 * @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent remove(long syncEventId) throws NoSuchSyncEventException {
		return remove((Serializable)syncEventId);
	}

	/**
	 * Removes the d l sync event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d l sync event
	 * @return the d l sync event that was removed
	 * @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent remove(Serializable primaryKey)
		throws NoSuchSyncEventException {
		Session session = null;

		try {
			session = openSession();

			DLSyncEvent dlSyncEvent = (DLSyncEvent)session.get(DLSyncEventImpl.class,
					primaryKey);

			if (dlSyncEvent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSyncEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlSyncEvent);
		}
		catch (NoSuchSyncEventException nsee) {
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
	protected DLSyncEvent removeImpl(DLSyncEvent dlSyncEvent) {
		dlSyncEvent = toUnwrappedModel(dlSyncEvent);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlSyncEvent)) {
				dlSyncEvent = (DLSyncEvent)session.get(DLSyncEventImpl.class,
						dlSyncEvent.getPrimaryKeyObj());
			}

			if (dlSyncEvent != null) {
				session.delete(dlSyncEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlSyncEvent != null) {
			clearCache(dlSyncEvent);
		}

		return dlSyncEvent;
	}

	@Override
	public DLSyncEvent updateImpl(DLSyncEvent dlSyncEvent) {
		dlSyncEvent = toUnwrappedModel(dlSyncEvent);

		boolean isNew = dlSyncEvent.isNew();

		DLSyncEventModelImpl dlSyncEventModelImpl = (DLSyncEventModelImpl)dlSyncEvent;

		Session session = null;

		try {
			session = openSession();

			if (dlSyncEvent.isNew()) {
				session.save(dlSyncEvent);

				dlSyncEvent.setNew(false);
			}
			else {
				dlSyncEvent = (DLSyncEvent)session.merge(dlSyncEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLSyncEventModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		entityCache.putResult(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncEventImpl.class, dlSyncEvent.getPrimaryKey(), dlSyncEvent,
			false);

		clearUniqueFindersCache(dlSyncEventModelImpl);
		cacheUniqueFindersCache(dlSyncEventModelImpl, isNew);

		dlSyncEvent.resetOriginalValues();

		return dlSyncEvent;
	}

	protected DLSyncEvent toUnwrappedModel(DLSyncEvent dlSyncEvent) {
		if (dlSyncEvent instanceof DLSyncEventImpl) {
			return dlSyncEvent;
		}

		DLSyncEventImpl dlSyncEventImpl = new DLSyncEventImpl();

		dlSyncEventImpl.setNew(dlSyncEvent.isNew());
		dlSyncEventImpl.setPrimaryKey(dlSyncEvent.getPrimaryKey());

		dlSyncEventImpl.setSyncEventId(dlSyncEvent.getSyncEventId());
		dlSyncEventImpl.setCompanyId(dlSyncEvent.getCompanyId());
		dlSyncEventImpl.setModifiedTime(dlSyncEvent.getModifiedTime());
		dlSyncEventImpl.setEvent(dlSyncEvent.getEvent());
		dlSyncEventImpl.setType(dlSyncEvent.getType());
		dlSyncEventImpl.setTypePK(dlSyncEvent.getTypePK());

		return dlSyncEventImpl;
	}

	/**
	 * Returns the d l sync event with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l sync event
	 * @return the d l sync event
	 * @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSyncEventException {
		DLSyncEvent dlSyncEvent = fetchByPrimaryKey(primaryKey);

		if (dlSyncEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSyncEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dlSyncEvent;
	}

	/**
	 * Returns the d l sync event with the primary key or throws a {@link NoSuchSyncEventException} if it could not be found.
	 *
	 * @param syncEventId the primary key of the d l sync event
	 * @return the d l sync event
	 * @throws NoSuchSyncEventException if a d l sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent findByPrimaryKey(long syncEventId)
		throws NoSuchSyncEventException {
		return findByPrimaryKey((Serializable)syncEventId);
	}

	/**
	 * Returns the d l sync event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l sync event
	 * @return the d l sync event, or <code>null</code> if a d l sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent fetchByPrimaryKey(Serializable primaryKey) {
		DLSyncEvent dlSyncEvent = (DLSyncEvent)entityCache.getResult(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
				DLSyncEventImpl.class, primaryKey);

		if (dlSyncEvent == _nullDLSyncEvent) {
			return null;
		}

		if (dlSyncEvent == null) {
			Session session = null;

			try {
				session = openSession();

				dlSyncEvent = (DLSyncEvent)session.get(DLSyncEventImpl.class,
						primaryKey);

				if (dlSyncEvent != null) {
					cacheResult(dlSyncEvent);
				}
				else {
					entityCache.putResult(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
						DLSyncEventImpl.class, primaryKey, _nullDLSyncEvent);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
					DLSyncEventImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return dlSyncEvent;
	}

	/**
	 * Returns the d l sync event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncEventId the primary key of the d l sync event
	 * @return the d l sync event, or <code>null</code> if a d l sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent fetchByPrimaryKey(long syncEventId) {
		return fetchByPrimaryKey((Serializable)syncEventId);
	}

	@Override
	public Map<Serializable, DLSyncEvent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLSyncEvent> map = new HashMap<Serializable, DLSyncEvent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLSyncEvent dlSyncEvent = fetchByPrimaryKey(primaryKey);

			if (dlSyncEvent != null) {
				map.put(primaryKey, dlSyncEvent);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DLSyncEvent dlSyncEvent = (DLSyncEvent)entityCache.getResult(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
					DLSyncEventImpl.class, primaryKey);

			if (dlSyncEvent == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, dlSyncEvent);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DLSYNCEVENT_WHERE_PKS_IN);

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

			for (DLSyncEvent dlSyncEvent : (List<DLSyncEvent>)q.list()) {
				map.put(dlSyncEvent.getPrimaryKeyObj(), dlSyncEvent);

				cacheResult(dlSyncEvent);

				uncachedPrimaryKeys.remove(dlSyncEvent.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DLSyncEventModelImpl.ENTITY_CACHE_ENABLED,
					DLSyncEventImpl.class, primaryKey, _nullDLSyncEvent);
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
	 * Returns all the d l sync events.
	 *
	 * @return the d l sync events
	 */
	@Override
	public List<DLSyncEvent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d l sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l sync events
	 * @param end the upper bound of the range of d l sync events (not inclusive)
	 * @return the range of d l sync events
	 */
	@Override
	public List<DLSyncEvent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d l sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l sync events
	 * @param end the upper bound of the range of d l sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d l sync events
	 */
	@Override
	public List<DLSyncEvent> findAll(int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d l sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLSyncEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l sync events
	 * @param end the upper bound of the range of d l sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of d l sync events
	 */
	@Override
	public List<DLSyncEvent> findAll(int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator,
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

		List<DLSyncEvent> list = null;

		if (retrieveFromCache) {
			list = (List<DLSyncEvent>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLSYNCEVENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLSYNCEVENT;

				if (pagination) {
					sql = sql.concat(DLSyncEventModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DLSyncEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLSyncEvent>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the d l sync events from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLSyncEvent dlSyncEvent : findAll()) {
			remove(dlSyncEvent);
		}
	}

	/**
	 * Returns the number of d l sync events.
	 *
	 * @return the number of d l sync events
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLSYNCEVENT);

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
		return DLSyncEventModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the d l sync event persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DLSyncEventImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_DLSYNCEVENT = "SELECT dlSyncEvent FROM DLSyncEvent dlSyncEvent";
	private static final String _SQL_SELECT_DLSYNCEVENT_WHERE_PKS_IN = "SELECT dlSyncEvent FROM DLSyncEvent dlSyncEvent WHERE syncEventId IN (";
	private static final String _SQL_SELECT_DLSYNCEVENT_WHERE = "SELECT dlSyncEvent FROM DLSyncEvent dlSyncEvent WHERE ";
	private static final String _SQL_COUNT_DLSYNCEVENT = "SELECT COUNT(dlSyncEvent) FROM DLSyncEvent dlSyncEvent";
	private static final String _SQL_COUNT_DLSYNCEVENT_WHERE = "SELECT COUNT(dlSyncEvent) FROM DLSyncEvent dlSyncEvent WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlSyncEvent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLSyncEvent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLSyncEvent exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DLSyncEventPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
	private static final DLSyncEvent _nullDLSyncEvent = new DLSyncEventImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLSyncEvent> toCacheModel() {
				return _nullDLSyncEventCacheModel;
			}
		};

	private static final CacheModel<DLSyncEvent> _nullDLSyncEventCacheModel = new CacheModel<DLSyncEvent>() {
			@Override
			public DLSyncEvent toEntityModel() {
				return _nullDLSyncEvent;
			}
		};
}