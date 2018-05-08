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

package com.liferay.portal.security.audit.storage.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.security.audit.storage.exception.NoSuchEventException;
import com.liferay.portal.security.audit.storage.model.AuditEvent;
import com.liferay.portal.security.audit.storage.model.impl.AuditEventImpl;
import com.liferay.portal.security.audit.storage.model.impl.AuditEventModelImpl;
import com.liferay.portal.security.audit.storage.service.persistence.AuditEventPersistence;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the audit event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventPersistence
 * @see com.liferay.portal.security.audit.storage.service.persistence.AuditEventUtil
 * @generated
 */
@ProviderType
public class AuditEventPersistenceImpl extends BasePersistenceImpl<AuditEvent>
	implements AuditEventPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AuditEventUtil} to access the audit event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AuditEventImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
			AuditEventModelImpl.FINDER_CACHE_ENABLED, AuditEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
			AuditEventModelImpl.FINDER_CACHE_ENABLED, AuditEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
			AuditEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
			AuditEventModelImpl.FINDER_CACHE_ENABLED, AuditEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
			AuditEventModelImpl.FINDER_CACHE_ENABLED, AuditEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			AuditEventModelImpl.COMPANYID_COLUMN_BITMASK |
			AuditEventModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
			AuditEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the audit events where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching audit events
	 */
	@Override
	public List<AuditEvent> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the audit events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AuditEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @return the range of matching audit events
	 */
	@Override
	public List<AuditEvent> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the audit events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AuditEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audit events
	 */
	@Override
	public List<AuditEvent> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<AuditEvent> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the audit events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AuditEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching audit events
	 */
	@Override
	public List<AuditEvent> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<AuditEvent> orderByComparator,
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

		List<AuditEvent> list = null;

		if (retrieveFromCache) {
			list = (List<AuditEvent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AuditEvent auditEvent : list) {
					if ((companyId != auditEvent.getCompanyId())) {
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

			query.append(_SQL_SELECT_AUDITEVENT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AuditEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<AuditEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AuditEvent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first audit event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit event
	 * @throws NoSuchEventException if a matching audit event could not be found
	 */
	@Override
	public AuditEvent findByCompanyId_First(long companyId,
		OrderByComparator<AuditEvent> orderByComparator)
		throws NoSuchEventException {
		AuditEvent auditEvent = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (auditEvent != null) {
			return auditEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first audit event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit event, or <code>null</code> if a matching audit event could not be found
	 */
	@Override
	public AuditEvent fetchByCompanyId_First(long companyId,
		OrderByComparator<AuditEvent> orderByComparator) {
		List<AuditEvent> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last audit event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit event
	 * @throws NoSuchEventException if a matching audit event could not be found
	 */
	@Override
	public AuditEvent findByCompanyId_Last(long companyId,
		OrderByComparator<AuditEvent> orderByComparator)
		throws NoSuchEventException {
		AuditEvent auditEvent = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (auditEvent != null) {
			return auditEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last audit event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit event, or <code>null</code> if a matching audit event could not be found
	 */
	@Override
	public AuditEvent fetchByCompanyId_Last(long companyId,
		OrderByComparator<AuditEvent> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AuditEvent> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the audit events before and after the current audit event in the ordered set where companyId = &#63;.
	 *
	 * @param auditEventId the primary key of the current audit event
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next audit event
	 * @throws NoSuchEventException if a audit event with the primary key could not be found
	 */
	@Override
	public AuditEvent[] findByCompanyId_PrevAndNext(long auditEventId,
		long companyId, OrderByComparator<AuditEvent> orderByComparator)
		throws NoSuchEventException {
		AuditEvent auditEvent = findByPrimaryKey(auditEventId);

		Session session = null;

		try {
			session = openSession();

			AuditEvent[] array = new AuditEventImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, auditEvent,
					companyId, orderByComparator, true);

			array[1] = auditEvent;

			array[2] = getByCompanyId_PrevAndNext(session, auditEvent,
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

	protected AuditEvent getByCompanyId_PrevAndNext(Session session,
		AuditEvent auditEvent, long companyId,
		OrderByComparator<AuditEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AUDITEVENT_WHERE);

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
			query.append(AuditEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(auditEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AuditEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the audit events where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AuditEvent auditEvent : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(auditEvent);
		}
	}

	/**
	 * Returns the number of audit events where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audit events
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AUDITEVENT_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "auditEvent.companyId = ?";

	public AuditEventPersistenceImpl() {
		setModelClass(AuditEvent.class);
	}

	/**
	 * Caches the audit event in the entity cache if it is enabled.
	 *
	 * @param auditEvent the audit event
	 */
	@Override
	public void cacheResult(AuditEvent auditEvent) {
		entityCache.putResult(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
			AuditEventImpl.class, auditEvent.getPrimaryKey(), auditEvent);

		auditEvent.resetOriginalValues();
	}

	/**
	 * Caches the audit events in the entity cache if it is enabled.
	 *
	 * @param auditEvents the audit events
	 */
	@Override
	public void cacheResult(List<AuditEvent> auditEvents) {
		for (AuditEvent auditEvent : auditEvents) {
			if (entityCache.getResult(
						AuditEventModelImpl.ENTITY_CACHE_ENABLED,
						AuditEventImpl.class, auditEvent.getPrimaryKey()) == null) {
				cacheResult(auditEvent);
			}
			else {
				auditEvent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all audit events.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AuditEventImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the audit event.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AuditEvent auditEvent) {
		entityCache.removeResult(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
			AuditEventImpl.class, auditEvent.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AuditEvent> auditEvents) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AuditEvent auditEvent : auditEvents) {
			entityCache.removeResult(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
				AuditEventImpl.class, auditEvent.getPrimaryKey());
		}
	}

	/**
	 * Creates a new audit event with the primary key. Does not add the audit event to the database.
	 *
	 * @param auditEventId the primary key for the new audit event
	 * @return the new audit event
	 */
	@Override
	public AuditEvent create(long auditEventId) {
		AuditEvent auditEvent = new AuditEventImpl();

		auditEvent.setNew(true);
		auditEvent.setPrimaryKey(auditEventId);

		auditEvent.setCompanyId(companyProvider.getCompanyId());

		return auditEvent;
	}

	/**
	 * Removes the audit event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEventId the primary key of the audit event
	 * @return the audit event that was removed
	 * @throws NoSuchEventException if a audit event with the primary key could not be found
	 */
	@Override
	public AuditEvent remove(long auditEventId) throws NoSuchEventException {
		return remove((Serializable)auditEventId);
	}

	/**
	 * Removes the audit event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the audit event
	 * @return the audit event that was removed
	 * @throws NoSuchEventException if a audit event with the primary key could not be found
	 */
	@Override
	public AuditEvent remove(Serializable primaryKey)
		throws NoSuchEventException {
		Session session = null;

		try {
			session = openSession();

			AuditEvent auditEvent = (AuditEvent)session.get(AuditEventImpl.class,
					primaryKey);

			if (auditEvent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(auditEvent);
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
	protected AuditEvent removeImpl(AuditEvent auditEvent) {
		auditEvent = toUnwrappedModel(auditEvent);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(auditEvent)) {
				auditEvent = (AuditEvent)session.get(AuditEventImpl.class,
						auditEvent.getPrimaryKeyObj());
			}

			if (auditEvent != null) {
				session.delete(auditEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (auditEvent != null) {
			clearCache(auditEvent);
		}

		return auditEvent;
	}

	@Override
	public AuditEvent updateImpl(AuditEvent auditEvent) {
		auditEvent = toUnwrappedModel(auditEvent);

		boolean isNew = auditEvent.isNew();

		AuditEventModelImpl auditEventModelImpl = (AuditEventModelImpl)auditEvent;

		Session session = null;

		try {
			session = openSession();

			if (auditEvent.isNew()) {
				session.save(auditEvent);

				auditEvent.setNew(false);
			}
			else {
				auditEvent = (AuditEvent)session.merge(auditEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AuditEventModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { auditEventModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((auditEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						auditEventModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { auditEventModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
			AuditEventImpl.class, auditEvent.getPrimaryKey(), auditEvent, false);

		auditEvent.resetOriginalValues();

		return auditEvent;
	}

	protected AuditEvent toUnwrappedModel(AuditEvent auditEvent) {
		if (auditEvent instanceof AuditEventImpl) {
			return auditEvent;
		}

		AuditEventImpl auditEventImpl = new AuditEventImpl();

		auditEventImpl.setNew(auditEvent.isNew());
		auditEventImpl.setPrimaryKey(auditEvent.getPrimaryKey());

		auditEventImpl.setAuditEventId(auditEvent.getAuditEventId());
		auditEventImpl.setCompanyId(auditEvent.getCompanyId());
		auditEventImpl.setUserId(auditEvent.getUserId());
		auditEventImpl.setUserName(auditEvent.getUserName());
		auditEventImpl.setCreateDate(auditEvent.getCreateDate());
		auditEventImpl.setEventType(auditEvent.getEventType());
		auditEventImpl.setClassName(auditEvent.getClassName());
		auditEventImpl.setClassPK(auditEvent.getClassPK());
		auditEventImpl.setMessage(auditEvent.getMessage());
		auditEventImpl.setClientHost(auditEvent.getClientHost());
		auditEventImpl.setClientIP(auditEvent.getClientIP());
		auditEventImpl.setServerName(auditEvent.getServerName());
		auditEventImpl.setServerPort(auditEvent.getServerPort());
		auditEventImpl.setSessionID(auditEvent.getSessionID());
		auditEventImpl.setAdditionalInfo(auditEvent.getAdditionalInfo());

		return auditEventImpl;
	}

	/**
	 * Returns the audit event with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the audit event
	 * @return the audit event
	 * @throws NoSuchEventException if a audit event with the primary key could not be found
	 */
	@Override
	public AuditEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEventException {
		AuditEvent auditEvent = fetchByPrimaryKey(primaryKey);

		if (auditEvent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return auditEvent;
	}

	/**
	 * Returns the audit event with the primary key or throws a {@link NoSuchEventException} if it could not be found.
	 *
	 * @param auditEventId the primary key of the audit event
	 * @return the audit event
	 * @throws NoSuchEventException if a audit event with the primary key could not be found
	 */
	@Override
	public AuditEvent findByPrimaryKey(long auditEventId)
		throws NoSuchEventException {
		return findByPrimaryKey((Serializable)auditEventId);
	}

	/**
	 * Returns the audit event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the audit event
	 * @return the audit event, or <code>null</code> if a audit event with the primary key could not be found
	 */
	@Override
	public AuditEvent fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
				AuditEventImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AuditEvent auditEvent = (AuditEvent)serializable;

		if (auditEvent == null) {
			Session session = null;

			try {
				session = openSession();

				auditEvent = (AuditEvent)session.get(AuditEventImpl.class,
						primaryKey);

				if (auditEvent != null) {
					cacheResult(auditEvent);
				}
				else {
					entityCache.putResult(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
						AuditEventImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
					AuditEventImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return auditEvent;
	}

	/**
	 * Returns the audit event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param auditEventId the primary key of the audit event
	 * @return the audit event, or <code>null</code> if a audit event with the primary key could not be found
	 */
	@Override
	public AuditEvent fetchByPrimaryKey(long auditEventId) {
		return fetchByPrimaryKey((Serializable)auditEventId);
	}

	@Override
	public Map<Serializable, AuditEvent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AuditEvent> map = new HashMap<Serializable, AuditEvent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AuditEvent auditEvent = fetchByPrimaryKey(primaryKey);

			if (auditEvent != null) {
				map.put(primaryKey, auditEvent);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
					AuditEventImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AuditEvent)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_AUDITEVENT_WHERE_PKS_IN);

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

			for (AuditEvent auditEvent : (List<AuditEvent>)q.list()) {
				map.put(auditEvent.getPrimaryKeyObj(), auditEvent);

				cacheResult(auditEvent);

				uncachedPrimaryKeys.remove(auditEvent.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AuditEventModelImpl.ENTITY_CACHE_ENABLED,
					AuditEventImpl.class, primaryKey, nullModel);
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
	 * Returns all the audit events.
	 *
	 * @return the audit events
	 */
	@Override
	public List<AuditEvent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the audit events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AuditEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @return the range of audit events
	 */
	@Override
	public List<AuditEvent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the audit events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AuditEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of audit events
	 */
	@Override
	public List<AuditEvent> findAll(int start, int end,
		OrderByComparator<AuditEvent> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the audit events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AuditEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of audit events
	 */
	@Override
	public List<AuditEvent> findAll(int start, int end,
		OrderByComparator<AuditEvent> orderByComparator,
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

		List<AuditEvent> list = null;

		if (retrieveFromCache) {
			list = (List<AuditEvent>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_AUDITEVENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_AUDITEVENT;

				if (pagination) {
					sql = sql.concat(AuditEventModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AuditEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AuditEvent>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the audit events from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AuditEvent auditEvent : findAll()) {
			remove(auditEvent);
		}
	}

	/**
	 * Returns the number of audit events.
	 *
	 * @return the number of audit events
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_AUDITEVENT);

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
		return AuditEventModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the audit event persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AuditEventImpl.class.getName());
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
	private static final String _SQL_SELECT_AUDITEVENT = "SELECT auditEvent FROM AuditEvent auditEvent";
	private static final String _SQL_SELECT_AUDITEVENT_WHERE_PKS_IN = "SELECT auditEvent FROM AuditEvent auditEvent WHERE auditEventId IN (";
	private static final String _SQL_SELECT_AUDITEVENT_WHERE = "SELECT auditEvent FROM AuditEvent auditEvent WHERE ";
	private static final String _SQL_COUNT_AUDITEVENT = "SELECT COUNT(auditEvent) FROM AuditEvent auditEvent";
	private static final String _SQL_COUNT_AUDITEVENT_WHERE = "SELECT COUNT(auditEvent) FROM AuditEvent auditEvent WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "auditEvent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AuditEvent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AuditEvent exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AuditEventPersistenceImpl.class);
}