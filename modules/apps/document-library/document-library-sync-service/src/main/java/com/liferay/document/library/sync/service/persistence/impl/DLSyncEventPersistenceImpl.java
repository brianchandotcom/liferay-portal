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

package com.liferay.document.library.sync.service.persistence.impl;

import com.liferay.document.library.sync.exception.NoSuchEventException;
import com.liferay.document.library.sync.model.DLSyncEvent;
import com.liferay.document.library.sync.model.impl.DLSyncEventImpl;
import com.liferay.document.library.sync.model.impl.DLSyncEventModelImpl;
import com.liferay.document.library.sync.service.persistence.DLSyncEventPersistence;
import com.liferay.document.library.sync.service.persistence.impl.constants.DLSyncPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the dl sync event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DLSyncEventPersistence.class)
public class DLSyncEventPersistenceImpl
	extends BasePersistenceImpl<DLSyncEvent> implements DLSyncEventPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLSyncEventUtil</code> to access the dl sync event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLSyncEventImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByModifiedTime;
	private FinderPath _finderPathWithPaginationCountByModifiedTime;

	/**
	 * Returns all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @return the matching dl sync events
	 */
	@Override
	public List<DLSyncEvent> findByModifiedTime(long modifiedTime) {
		return findByModifiedTime(
			modifiedTime, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @return the range of matching dl sync events
	 */
	@Override
	public List<DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end) {

		return findByModifiedTime(modifiedTime, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dl sync events
	 */
	@Override
	public List<DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator) {

		return findByModifiedTime(
			modifiedTime, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dl sync events where modifiedTime &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dl sync events
	 */
	@Override
	public List<DLSyncEvent> findByModifiedTime(
		long modifiedTime, int start, int end,
		OrderByComparator<DLSyncEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByModifiedTime;
		finderArgs = new Object[] {modifiedTime, start, end, orderByComparator};

		List<DLSyncEvent> list = null;

		if (useFinderCache) {
			list = (List<DLSyncEvent>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLSyncEvent dlSyncEvent : list) {
					if (modifiedTime >= dlSyncEvent.getModifiedTime()) {
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

			query.append(_SQL_SELECT_DLSYNCEVENT_WHERE);

			query.append(_FINDER_COLUMN_MODIFIEDTIME_MODIFIEDTIME_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLSyncEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modifiedTime);

				list = (List<DLSyncEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl sync event
	 * @throws NoSuchEventException if a matching dl sync event could not be found
	 */
	@Override
	public DLSyncEvent findByModifiedTime_First(
			long modifiedTime, OrderByComparator<DLSyncEvent> orderByComparator)
		throws NoSuchEventException {

		DLSyncEvent dlSyncEvent = fetchByModifiedTime_First(
			modifiedTime, orderByComparator);

		if (dlSyncEvent != null) {
			return dlSyncEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modifiedTime>");
		msg.append(modifiedTime);

		msg.append("}");

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	@Override
	public DLSyncEvent fetchByModifiedTime_First(
		long modifiedTime, OrderByComparator<DLSyncEvent> orderByComparator) {

		List<DLSyncEvent> list = findByModifiedTime(
			modifiedTime, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl sync event
	 * @throws NoSuchEventException if a matching dl sync event could not be found
	 */
	@Override
	public DLSyncEvent findByModifiedTime_Last(
			long modifiedTime, OrderByComparator<DLSyncEvent> orderByComparator)
		throws NoSuchEventException {

		DLSyncEvent dlSyncEvent = fetchByModifiedTime_Last(
			modifiedTime, orderByComparator);

		if (dlSyncEvent != null) {
			return dlSyncEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modifiedTime>");
		msg.append(modifiedTime);

		msg.append("}");

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	@Override
	public DLSyncEvent fetchByModifiedTime_Last(
		long modifiedTime, OrderByComparator<DLSyncEvent> orderByComparator) {

		int count = countByModifiedTime(modifiedTime);

		if (count == 0) {
			return null;
		}

		List<DLSyncEvent> list = findByModifiedTime(
			modifiedTime, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dl sync events before and after the current dl sync event in the ordered set where modifiedTime &gt; &#63;.
	 *
	 * @param syncEventId the primary key of the current dl sync event
	 * @param modifiedTime the modified time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dl sync event
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent[] findByModifiedTime_PrevAndNext(
			long syncEventId, long modifiedTime,
			OrderByComparator<DLSyncEvent> orderByComparator)
		throws NoSuchEventException {

		DLSyncEvent dlSyncEvent = findByPrimaryKey(syncEventId);

		Session session = null;

		try {
			session = openSession();

			DLSyncEvent[] array = new DLSyncEventImpl[3];

			array[0] = getByModifiedTime_PrevAndNext(
				session, dlSyncEvent, modifiedTime, orderByComparator, true);

			array[1] = dlSyncEvent;

			array[2] = getByModifiedTime_PrevAndNext(
				session, dlSyncEvent, modifiedTime, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLSyncEvent getByModifiedTime_PrevAndNext(
		Session session, DLSyncEvent dlSyncEvent, long modifiedTime,
		OrderByComparator<DLSyncEvent> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLSYNCEVENT_WHERE);

		query.append(_FINDER_COLUMN_MODIFIEDTIME_MODIFIEDTIME_2);

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
			query.append(DLSyncEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(modifiedTime);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dlSyncEvent)) {

				qPos.add(orderByConditionValue);
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
	 * Removes all the dl sync events where modifiedTime &gt; &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 */
	@Override
	public void removeByModifiedTime(long modifiedTime) {
		for (DLSyncEvent dlSyncEvent :
				findByModifiedTime(
					modifiedTime, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlSyncEvent);
		}
	}

	/**
	 * Returns the number of dl sync events where modifiedTime &gt; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @return the number of matching dl sync events
	 */
	@Override
	public int countByModifiedTime(long modifiedTime) {
		FinderPath finderPath = _finderPathWithPaginationCountByModifiedTime;

		Object[] finderArgs = new Object[] {modifiedTime};

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

	private static final String _FINDER_COLUMN_MODIFIEDTIME_MODIFIEDTIME_2 =
		"dlSyncEvent.modifiedTime > ?";

	private FinderPath _finderPathFetchByTypePK;
	private FinderPath _finderPathCountByTypePK;

	/**
	 * Returns the dl sync event where typePK = &#63; or throws a <code>NoSuchEventException</code> if it could not be found.
	 *
	 * @param typePK the type pk
	 * @return the matching dl sync event
	 * @throws NoSuchEventException if a matching dl sync event could not be found
	 */
	@Override
	public DLSyncEvent findByTypePK(long typePK) throws NoSuchEventException {
		DLSyncEvent dlSyncEvent = fetchByTypePK(typePK);

		if (dlSyncEvent == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("typePK=");
			msg.append(typePK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEventException(msg.toString());
		}

		return dlSyncEvent;
	}

	/**
	 * Returns the dl sync event where typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param typePK the type pk
	 * @return the matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	@Override
	public DLSyncEvent fetchByTypePK(long typePK) {
		return fetchByTypePK(typePK, true);
	}

	/**
	 * Returns the dl sync event where typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param typePK the type pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl sync event, or <code>null</code> if a matching dl sync event could not be found
	 */
	@Override
	public DLSyncEvent fetchByTypePK(long typePK, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {typePK};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByTypePK, finderArgs, this);
		}

		if (result instanceof DLSyncEvent) {
			DLSyncEvent dlSyncEvent = (DLSyncEvent)result;

			if (typePK != dlSyncEvent.getTypePK()) {
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
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByTypePK, finderArgs, list);
					}
				}
				else {
					DLSyncEvent dlSyncEvent = list.get(0);

					result = dlSyncEvent;

					cacheResult(dlSyncEvent);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByTypePK, finderArgs);
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
			return (DLSyncEvent)result;
		}
	}

	/**
	 * Removes the dl sync event where typePK = &#63; from the database.
	 *
	 * @param typePK the type pk
	 * @return the dl sync event that was removed
	 */
	@Override
	public DLSyncEvent removeByTypePK(long typePK) throws NoSuchEventException {
		DLSyncEvent dlSyncEvent = findByTypePK(typePK);

		return remove(dlSyncEvent);
	}

	/**
	 * Returns the number of dl sync events where typePK = &#63;.
	 *
	 * @param typePK the type pk
	 * @return the number of matching dl sync events
	 */
	@Override
	public int countByTypePK(long typePK) {
		FinderPath finderPath = _finderPathCountByTypePK;

		Object[] finderArgs = new Object[] {typePK};

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

	private static final String _FINDER_COLUMN_TYPEPK_TYPEPK_2 =
		"dlSyncEvent.typePK = ?";

	public DLSyncEventPersistenceImpl() {
		setModelClass(DLSyncEvent.class);

		setModelImplClass(DLSyncEventImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the dl sync event in the entity cache if it is enabled.
	 *
	 * @param dlSyncEvent the dl sync event
	 */
	@Override
	public void cacheResult(DLSyncEvent dlSyncEvent) {
		entityCache.putResult(
			entityCacheEnabled, DLSyncEventImpl.class,
			dlSyncEvent.getPrimaryKey(), dlSyncEvent);

		finderCache.putResult(
			_finderPathFetchByTypePK, new Object[] {dlSyncEvent.getTypePK()},
			dlSyncEvent);

		dlSyncEvent.resetOriginalValues();
	}

	/**
	 * Caches the dl sync events in the entity cache if it is enabled.
	 *
	 * @param dlSyncEvents the dl sync events
	 */
	@Override
	public void cacheResult(List<DLSyncEvent> dlSyncEvents) {
		for (DLSyncEvent dlSyncEvent : dlSyncEvents) {
			if (entityCache.getResult(
					entityCacheEnabled, DLSyncEventImpl.class,
					dlSyncEvent.getPrimaryKey()) == null) {

				cacheResult(dlSyncEvent);
			}
			else {
				dlSyncEvent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all dl sync events.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
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
	 * Clears the cache for the dl sync event.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLSyncEvent dlSyncEvent) {
		entityCache.removeResult(
			entityCacheEnabled, DLSyncEventImpl.class,
			dlSyncEvent.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLSyncEventModelImpl)dlSyncEvent, true);
	}

	@Override
	public void clearCache(List<DLSyncEvent> dlSyncEvents) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLSyncEvent dlSyncEvent : dlSyncEvents) {
			entityCache.removeResult(
				entityCacheEnabled, DLSyncEventImpl.class,
				dlSyncEvent.getPrimaryKey());

			clearUniqueFindersCache((DLSyncEventModelImpl)dlSyncEvent, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, DLSyncEventImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DLSyncEventModelImpl dlSyncEventModelImpl) {

		Object[] args = new Object[] {dlSyncEventModelImpl.getTypePK()};

		finderCache.putResult(
			_finderPathCountByTypePK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByTypePK, args, dlSyncEventModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DLSyncEventModelImpl dlSyncEventModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {dlSyncEventModelImpl.getTypePK()};

			finderCache.removeResult(_finderPathCountByTypePK, args);
			finderCache.removeResult(_finderPathFetchByTypePK, args);
		}

		if ((dlSyncEventModelImpl.getColumnBitmask() &
			 _finderPathFetchByTypePK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlSyncEventModelImpl.getOriginalTypePK()
			};

			finderCache.removeResult(_finderPathCountByTypePK, args);
			finderCache.removeResult(_finderPathFetchByTypePK, args);
		}
	}

	/**
	 * Creates a new dl sync event with the primary key. Does not add the dl sync event to the database.
	 *
	 * @param syncEventId the primary key for the new dl sync event
	 * @return the new dl sync event
	 */
	@Override
	public DLSyncEvent create(long syncEventId) {
		DLSyncEvent dlSyncEvent = new DLSyncEventImpl();

		dlSyncEvent.setNew(true);
		dlSyncEvent.setPrimaryKey(syncEventId);

		dlSyncEvent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dlSyncEvent;
	}

	/**
	 * Removes the dl sync event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event that was removed
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent remove(long syncEventId) throws NoSuchEventException {
		return remove((Serializable)syncEventId);
	}

	/**
	 * Removes the dl sync event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dl sync event
	 * @return the dl sync event that was removed
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent remove(Serializable primaryKey)
		throws NoSuchEventException {

		Session session = null;

		try {
			session = openSession();

			DLSyncEvent dlSyncEvent = (DLSyncEvent)session.get(
				DLSyncEventImpl.class, primaryKey);

			if (dlSyncEvent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEventException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dlSyncEvent);
		}
		catch (NoSuchEventException nsee) {
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
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlSyncEvent)) {
				dlSyncEvent = (DLSyncEvent)session.get(
					DLSyncEventImpl.class, dlSyncEvent.getPrimaryKeyObj());
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
		boolean isNew = dlSyncEvent.isNew();

		if (!(dlSyncEvent instanceof DLSyncEventModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlSyncEvent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(dlSyncEvent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlSyncEvent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLSyncEvent implementation " +
					dlSyncEvent.getClass());
		}

		DLSyncEventModelImpl dlSyncEventModelImpl =
			(DLSyncEventModelImpl)dlSyncEvent;

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

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			entityCacheEnabled, DLSyncEventImpl.class,
			dlSyncEvent.getPrimaryKey(), dlSyncEvent, false);

		clearUniqueFindersCache(dlSyncEventModelImpl, false);
		cacheUniqueFindersCache(dlSyncEventModelImpl);

		dlSyncEvent.resetOriginalValues();

		return dlSyncEvent;
	}

	/**
	 * Returns the dl sync event with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dl sync event
	 * @return the dl sync event
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEventException {

		DLSyncEvent dlSyncEvent = fetchByPrimaryKey(primaryKey);

		if (dlSyncEvent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEventException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dlSyncEvent;
	}

	/**
	 * Returns the dl sync event with the primary key or throws a <code>NoSuchEventException</code> if it could not be found.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event
	 * @throws NoSuchEventException if a dl sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent findByPrimaryKey(long syncEventId)
		throws NoSuchEventException {

		return findByPrimaryKey((Serializable)syncEventId);
	}

	/**
	 * Returns the dl sync event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event, or <code>null</code> if a dl sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent fetchByPrimaryKey(long syncEventId) {
		return fetchByPrimaryKey((Serializable)syncEventId);
	}

	/**
	 * Returns all the dl sync events.
	 *
	 * @return the dl sync events
	 */
	@Override
	public List<DLSyncEvent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @return the range of dl sync events
	 */
	@Override
	public List<DLSyncEvent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl sync events
	 */
	@Override
	public List<DLSyncEvent> findAll(
		int start, int end, OrderByComparator<DLSyncEvent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dl sync events
	 */
	@Override
	public List<DLSyncEvent> findAll(
		int start, int end, OrderByComparator<DLSyncEvent> orderByComparator,
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

		List<DLSyncEvent> list = null;

		if (useFinderCache) {
			list = (List<DLSyncEvent>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DLSYNCEVENT);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLSYNCEVENT;

				sql = sql.concat(DLSyncEventModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<DLSyncEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Removes all the dl sync events from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLSyncEvent dlSyncEvent : findAll()) {
			remove(dlSyncEvent);
		}
	}

	/**
	 * Returns the number of dl sync events.
	 *
	 * @return the number of dl sync events
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLSYNCEVENT);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "syncEventId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLSYNCEVENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DLSyncEventModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dl sync event persistence.
	 */
	@Activate
	public void activate() {
		DLSyncEventModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		DLSyncEventModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DLSyncEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DLSyncEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByModifiedTime = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DLSyncEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByModifiedTime",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByModifiedTime = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByModifiedTime",
			new String[] {Long.class.getName()});

		_finderPathFetchByTypePK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DLSyncEventImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByTypePK",
			new String[] {Long.class.getName()},
			DLSyncEventModelImpl.TYPEPK_COLUMN_BITMASK);

		_finderPathCountByTypePK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTypePK",
			new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DLSyncEventImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DLSyncPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.document.library.sync.model.DLSyncEvent"),
			true);
	}

	@Override
	@Reference(
		target = DLSyncPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DLSyncPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DLSYNCEVENT =
		"SELECT dlSyncEvent FROM DLSyncEvent dlSyncEvent";

	private static final String _SQL_SELECT_DLSYNCEVENT_WHERE =
		"SELECT dlSyncEvent FROM DLSyncEvent dlSyncEvent WHERE ";

	private static final String _SQL_COUNT_DLSYNCEVENT =
		"SELECT COUNT(dlSyncEvent) FROM DLSyncEvent dlSyncEvent";

	private static final String _SQL_COUNT_DLSYNCEVENT_WHERE =
		"SELECT COUNT(dlSyncEvent) FROM DLSyncEvent dlSyncEvent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dlSyncEvent.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DLSyncEvent exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLSyncEvent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLSyncEventPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	static {
		try {
			Class.forName(DLSyncPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}