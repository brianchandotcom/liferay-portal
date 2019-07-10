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

package com.liferay.data.engine.service.persistence.impl;

import com.liferay.data.engine.exception.NoSuchRecordQueryException;
import com.liferay.data.engine.model.DERecordQuery;
import com.liferay.data.engine.model.impl.DERecordQueryImpl;
import com.liferay.data.engine.model.impl.DERecordQueryModelImpl;
import com.liferay.data.engine.service.persistence.DERecordQueryPersistence;
import com.liferay.data.engine.service.persistence.impl.constants.DEPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the de record query service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DERecordQueryPersistence.class)
@ProviderType
public class DERecordQueryPersistenceImpl
	extends BasePersistenceImpl<DERecordQuery>
	implements DERecordQueryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DERecordQueryUtil</code> to access the de record query persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DERecordQueryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the de record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de record queries
	 */
	@Override
	public List<DERecordQuery> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @return the range of matching de record queries
	 */
	@Override
	public List<DERecordQuery> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de record queries
	 */
	@Override
	public List<DERecordQuery> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DERecordQuery> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de record queries
	 */
	@Override
	public List<DERecordQuery> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DERecordQuery> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] {uuid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<DERecordQuery> list = null;

		if (retrieveFromCache) {
			list = (List<DERecordQuery>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DERecordQuery deRecordQuery : list) {
					if (!uuid.equals(deRecordQuery.getUuid())) {
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

			query.append(_SQL_SELECT_DERECORDQUERY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(DERecordQueryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<DERecordQuery>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DERecordQuery>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de record query
	 * @throws NoSuchRecordQueryException if a matching de record query could not be found
	 */
	@Override
	public DERecordQuery findByUuid_First(
			String uuid, OrderByComparator<DERecordQuery> orderByComparator)
		throws NoSuchRecordQueryException {

		DERecordQuery deRecordQuery = fetchByUuid_First(
			uuid, orderByComparator);

		if (deRecordQuery != null) {
			return deRecordQuery;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchRecordQueryException(msg.toString());
	}

	/**
	 * Returns the first de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de record query, or <code>null</code> if a matching de record query could not be found
	 */
	@Override
	public DERecordQuery fetchByUuid_First(
		String uuid, OrderByComparator<DERecordQuery> orderByComparator) {

		List<DERecordQuery> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de record query
	 * @throws NoSuchRecordQueryException if a matching de record query could not be found
	 */
	@Override
	public DERecordQuery findByUuid_Last(
			String uuid, OrderByComparator<DERecordQuery> orderByComparator)
		throws NoSuchRecordQueryException {

		DERecordQuery deRecordQuery = fetchByUuid_Last(uuid, orderByComparator);

		if (deRecordQuery != null) {
			return deRecordQuery;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchRecordQueryException(msg.toString());
	}

	/**
	 * Returns the last de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de record query, or <code>null</code> if a matching de record query could not be found
	 */
	@Override
	public DERecordQuery fetchByUuid_Last(
		String uuid, OrderByComparator<DERecordQuery> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DERecordQuery> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de record queries before and after the current de record query in the ordered set where uuid = &#63;.
	 *
	 * @param recordQueryId the primary key of the current de record query
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de record query
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	@Override
	public DERecordQuery[] findByUuid_PrevAndNext(
			long recordQueryId, String uuid,
			OrderByComparator<DERecordQuery> orderByComparator)
		throws NoSuchRecordQueryException {

		uuid = Objects.toString(uuid, "");

		DERecordQuery deRecordQuery = findByPrimaryKey(recordQueryId);

		Session session = null;

		try {
			session = openSession();

			DERecordQuery[] array = new DERecordQueryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, deRecordQuery, uuid, orderByComparator, true);

			array[1] = deRecordQuery;

			array[2] = getByUuid_PrevAndNext(
				session, deRecordQuery, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DERecordQuery getByUuid_PrevAndNext(
		Session session, DERecordQuery deRecordQuery, String uuid,
		OrderByComparator<DERecordQuery> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DERECORDQUERY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

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
			query.append(DERecordQueryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						deRecordQuery)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DERecordQuery> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de record queries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DERecordQuery deRecordQuery :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(deRecordQuery);
		}
	}

	/**
	 * Returns the number of de record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de record queries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DERECORDQUERY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"deRecordQuery.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(deRecordQuery.uuid IS NULL OR deRecordQuery.uuid = '')";

	public DERecordQueryPersistenceImpl() {
		setModelClass(DERecordQuery.class);

		setModelImplClass(DERecordQueryImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the de record query in the entity cache if it is enabled.
	 *
	 * @param deRecordQuery the de record query
	 */
	@Override
	public void cacheResult(DERecordQuery deRecordQuery) {
		entityCache.putResult(
			entityCacheEnabled, DERecordQueryImpl.class,
			deRecordQuery.getPrimaryKey(), deRecordQuery);

		deRecordQuery.resetOriginalValues();
	}

	/**
	 * Caches the de record queries in the entity cache if it is enabled.
	 *
	 * @param deRecordQueries the de record queries
	 */
	@Override
	public void cacheResult(List<DERecordQuery> deRecordQueries) {
		for (DERecordQuery deRecordQuery : deRecordQueries) {
			if (entityCache.getResult(
					entityCacheEnabled, DERecordQueryImpl.class,
					deRecordQuery.getPrimaryKey()) == null) {

				cacheResult(deRecordQuery);
			}
			else {
				deRecordQuery.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all de record queries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DERecordQueryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the de record query.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DERecordQuery deRecordQuery) {
		entityCache.removeResult(
			entityCacheEnabled, DERecordQueryImpl.class,
			deRecordQuery.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<DERecordQuery> deRecordQueries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DERecordQuery deRecordQuery : deRecordQueries) {
			entityCache.removeResult(
				entityCacheEnabled, DERecordQueryImpl.class,
				deRecordQuery.getPrimaryKey());
		}
	}

	/**
	 * Creates a new de record query with the primary key. Does not add the de record query to the database.
	 *
	 * @param recordQueryId the primary key for the new de record query
	 * @return the new de record query
	 */
	@Override
	public DERecordQuery create(long recordQueryId) {
		DERecordQuery deRecordQuery = new DERecordQueryImpl();

		deRecordQuery.setNew(true);
		deRecordQuery.setPrimaryKey(recordQueryId);

		String uuid = PortalUUIDUtil.generate();

		deRecordQuery.setUuid(uuid);

		return deRecordQuery;
	}

	/**
	 * Removes the de record query with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordQueryId the primary key of the de record query
	 * @return the de record query that was removed
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	@Override
	public DERecordQuery remove(long recordQueryId)
		throws NoSuchRecordQueryException {

		return remove((Serializable)recordQueryId);
	}

	/**
	 * Removes the de record query with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the de record query
	 * @return the de record query that was removed
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	@Override
	public DERecordQuery remove(Serializable primaryKey)
		throws NoSuchRecordQueryException {

		Session session = null;

		try {
			session = openSession();

			DERecordQuery deRecordQuery = (DERecordQuery)session.get(
				DERecordQueryImpl.class, primaryKey);

			if (deRecordQuery == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecordQueryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(deRecordQuery);
		}
		catch (NoSuchRecordQueryException nsee) {
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
	protected DERecordQuery removeImpl(DERecordQuery deRecordQuery) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(deRecordQuery)) {
				deRecordQuery = (DERecordQuery)session.get(
					DERecordQueryImpl.class, deRecordQuery.getPrimaryKeyObj());
			}

			if (deRecordQuery != null) {
				session.delete(deRecordQuery);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (deRecordQuery != null) {
			clearCache(deRecordQuery);
		}

		return deRecordQuery;
	}

	@Override
	public DERecordQuery updateImpl(DERecordQuery deRecordQuery) {
		boolean isNew = deRecordQuery.isNew();

		if (!(deRecordQuery instanceof DERecordQueryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(deRecordQuery.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					deRecordQuery);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in deRecordQuery proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DERecordQuery implementation " +
					deRecordQuery.getClass());
		}

		DERecordQueryModelImpl deRecordQueryModelImpl =
			(DERecordQueryModelImpl)deRecordQuery;

		if (Validator.isNull(deRecordQuery.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			deRecordQuery.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (deRecordQuery.isNew()) {
				session.save(deRecordQuery);

				deRecordQuery.setNew(false);
			}
			else {
				deRecordQuery = (DERecordQuery)session.merge(deRecordQuery);
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
			Object[] args = new Object[] {deRecordQueryModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((deRecordQueryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					deRecordQueryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {deRecordQueryModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, DERecordQueryImpl.class,
			deRecordQuery.getPrimaryKey(), deRecordQuery, false);

		deRecordQuery.resetOriginalValues();

		return deRecordQuery;
	}

	/**
	 * Returns the de record query with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the de record query
	 * @return the de record query
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	@Override
	public DERecordQuery findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecordQueryException {

		DERecordQuery deRecordQuery = fetchByPrimaryKey(primaryKey);

		if (deRecordQuery == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecordQueryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return deRecordQuery;
	}

	/**
	 * Returns the de record query with the primary key or throws a <code>NoSuchRecordQueryException</code> if it could not be found.
	 *
	 * @param recordQueryId the primary key of the de record query
	 * @return the de record query
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	@Override
	public DERecordQuery findByPrimaryKey(long recordQueryId)
		throws NoSuchRecordQueryException {

		return findByPrimaryKey((Serializable)recordQueryId);
	}

	/**
	 * Returns the de record query with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordQueryId the primary key of the de record query
	 * @return the de record query, or <code>null</code> if a de record query with the primary key could not be found
	 */
	@Override
	public DERecordQuery fetchByPrimaryKey(long recordQueryId) {
		return fetchByPrimaryKey((Serializable)recordQueryId);
	}

	/**
	 * Returns all the de record queries.
	 *
	 * @return the de record queries
	 */
	@Override
	public List<DERecordQuery> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @return the range of de record queries
	 */
	@Override
	public List<DERecordQuery> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the de record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de record queries
	 */
	@Override
	public List<DERecordQuery> findAll(
		int start, int end,
		OrderByComparator<DERecordQuery> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of de record queries
	 */
	@Override
	public List<DERecordQuery> findAll(
		int start, int end, OrderByComparator<DERecordQuery> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<DERecordQuery> list = null;

		if (retrieveFromCache) {
			list = (List<DERecordQuery>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DERECORDQUERY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DERECORDQUERY;

				if (pagination) {
					sql = sql.concat(DERecordQueryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DERecordQuery>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DERecordQuery>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Removes all the de record queries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DERecordQuery deRecordQuery : findAll()) {
			remove(deRecordQuery);
		}
	}

	/**
	 * Returns the number of de record queries.
	 *
	 * @return the number of de record queries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DERECORDQUERY);

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
		return "recordQueryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DERECORDQUERY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DERecordQueryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the de record query persistence.
	 */
	@Activate
	public void activate() {
		DERecordQueryModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		DERecordQueryModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DERecordQueryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DERecordQueryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DERecordQueryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, DERecordQueryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			DERecordQueryModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DERecordQueryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.data.engine.model.DERecordQuery"),
			true);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DERECORDQUERY =
		"SELECT deRecordQuery FROM DERecordQuery deRecordQuery";

	private static final String _SQL_SELECT_DERECORDQUERY_WHERE =
		"SELECT deRecordQuery FROM DERecordQuery deRecordQuery WHERE ";

	private static final String _SQL_COUNT_DERECORDQUERY =
		"SELECT COUNT(deRecordQuery) FROM DERecordQuery deRecordQuery";

	private static final String _SQL_COUNT_DERECORDQUERY_WHERE =
		"SELECT COUNT(deRecordQuery) FROM DERecordQuery deRecordQuery WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "deRecordQuery.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DERecordQuery exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DERecordQuery exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DERecordQueryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}