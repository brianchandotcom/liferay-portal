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

package com.liferay.powwow.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.powwow.exception.NoSuchServerException;
import com.liferay.powwow.model.PowwowServer;
import com.liferay.powwow.model.impl.PowwowServerImpl;
import com.liferay.powwow.model.impl.PowwowServerModelImpl;
import com.liferay.powwow.service.persistence.PowwowServerPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the powwow server service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @generated
 */
public class PowwowServerPersistenceImpl
	extends BasePersistenceImpl<PowwowServer>
	implements PowwowServerPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PowwowServerUtil</code> to access the powwow server persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PowwowServerImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByPT_A;
	private FinderPath _finderPathWithoutPaginationFindByPT_A;
	private FinderPath _finderPathCountByPT_A;

	/**
	 * Returns all the powwow servers where providerType = &#63; and active = &#63;.
	 *
	 * @param providerType the provider type
	 * @param active the active
	 * @return the matching powwow servers
	 */
	@Override
	public List<PowwowServer> findByPT_A(String providerType, boolean active) {
		return findByPT_A(
			providerType, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow servers where providerType = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowServerModelImpl</code>.
	 * </p>
	 *
	 * @param providerType the provider type
	 * @param active the active
	 * @param start the lower bound of the range of powwow servers
	 * @param end the upper bound of the range of powwow servers (not inclusive)
	 * @return the range of matching powwow servers
	 */
	@Override
	public List<PowwowServer> findByPT_A(
		String providerType, boolean active, int start, int end) {

		return findByPT_A(providerType, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow servers where providerType = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowServerModelImpl</code>.
	 * </p>
	 *
	 * @param providerType the provider type
	 * @param active the active
	 * @param start the lower bound of the range of powwow servers
	 * @param end the upper bound of the range of powwow servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow servers
	 */
	@Override
	public List<PowwowServer> findByPT_A(
		String providerType, boolean active, int start, int end,
		OrderByComparator<PowwowServer> orderByComparator) {

		return findByPT_A(
			providerType, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow servers where providerType = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowServerModelImpl</code>.
	 * </p>
	 *
	 * @param providerType the provider type
	 * @param active the active
	 * @param start the lower bound of the range of powwow servers
	 * @param end the upper bound of the range of powwow servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching powwow servers
	 */
	@Override
	public List<PowwowServer> findByPT_A(
		String providerType, boolean active, int start, int end,
		OrderByComparator<PowwowServer> orderByComparator,
		boolean useFinderCache) {

		providerType = Objects.toString(providerType, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByPT_A;
				finderArgs = new Object[] {providerType, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPT_A;
			finderArgs = new Object[] {
				providerType, active, start, end, orderByComparator
			};
		}

		List<PowwowServer> list = null;

		if (useFinderCache) {
			list = (List<PowwowServer>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PowwowServer powwowServer : list) {
					if (!providerType.equals(powwowServer.getProviderType()) ||
						(active != powwowServer.isActive())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_POWWOWSERVER_WHERE);

			boolean bindProviderType = false;

			if (providerType.isEmpty()) {
				query.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_3);
			}
			else {
				bindProviderType = true;

				query.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_2);
			}

			query.append(_FINDER_COLUMN_PT_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(PowwowServerModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindProviderType) {
					qPos.add(providerType);
				}

				qPos.add(active);

				list = (List<PowwowServer>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first powwow server in the ordered set where providerType = &#63; and active = &#63;.
	 *
	 * @param providerType the provider type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow server
	 * @throws NoSuchServerException if a matching powwow server could not be found
	 */
	@Override
	public PowwowServer findByPT_A_First(
			String providerType, boolean active,
			OrderByComparator<PowwowServer> orderByComparator)
		throws NoSuchServerException {

		PowwowServer powwowServer = fetchByPT_A_First(
			providerType, active, orderByComparator);

		if (powwowServer != null) {
			return powwowServer;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("providerType=");
		msg.append(providerType);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchServerException(msg.toString());
	}

	/**
	 * Returns the first powwow server in the ordered set where providerType = &#63; and active = &#63;.
	 *
	 * @param providerType the provider type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow server, or <code>null</code> if a matching powwow server could not be found
	 */
	@Override
	public PowwowServer fetchByPT_A_First(
		String providerType, boolean active,
		OrderByComparator<PowwowServer> orderByComparator) {

		List<PowwowServer> list = findByPT_A(
			providerType, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last powwow server in the ordered set where providerType = &#63; and active = &#63;.
	 *
	 * @param providerType the provider type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow server
	 * @throws NoSuchServerException if a matching powwow server could not be found
	 */
	@Override
	public PowwowServer findByPT_A_Last(
			String providerType, boolean active,
			OrderByComparator<PowwowServer> orderByComparator)
		throws NoSuchServerException {

		PowwowServer powwowServer = fetchByPT_A_Last(
			providerType, active, orderByComparator);

		if (powwowServer != null) {
			return powwowServer;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("providerType=");
		msg.append(providerType);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchServerException(msg.toString());
	}

	/**
	 * Returns the last powwow server in the ordered set where providerType = &#63; and active = &#63;.
	 *
	 * @param providerType the provider type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow server, or <code>null</code> if a matching powwow server could not be found
	 */
	@Override
	public PowwowServer fetchByPT_A_Last(
		String providerType, boolean active,
		OrderByComparator<PowwowServer> orderByComparator) {

		int count = countByPT_A(providerType, active);

		if (count == 0) {
			return null;
		}

		List<PowwowServer> list = findByPT_A(
			providerType, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the powwow servers before and after the current powwow server in the ordered set where providerType = &#63; and active = &#63;.
	 *
	 * @param powwowServerId the primary key of the current powwow server
	 * @param providerType the provider type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next powwow server
	 * @throws NoSuchServerException if a powwow server with the primary key could not be found
	 */
	@Override
	public PowwowServer[] findByPT_A_PrevAndNext(
			long powwowServerId, String providerType, boolean active,
			OrderByComparator<PowwowServer> orderByComparator)
		throws NoSuchServerException {

		providerType = Objects.toString(providerType, "");

		PowwowServer powwowServer = findByPrimaryKey(powwowServerId);

		Session session = null;

		try {
			session = openSession();

			PowwowServer[] array = new PowwowServerImpl[3];

			array[0] = getByPT_A_PrevAndNext(
				session, powwowServer, providerType, active, orderByComparator,
				true);

			array[1] = powwowServer;

			array[2] = getByPT_A_PrevAndNext(
				session, powwowServer, providerType, active, orderByComparator,
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

	protected PowwowServer getByPT_A_PrevAndNext(
		Session session, PowwowServer powwowServer, String providerType,
		boolean active, OrderByComparator<PowwowServer> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_POWWOWSERVER_WHERE);

		boolean bindProviderType = false;

		if (providerType.isEmpty()) {
			query.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_3);
		}
		else {
			bindProviderType = true;

			query.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_2);
		}

		query.append(_FINDER_COLUMN_PT_A_ACTIVE_2);

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
			query.append(PowwowServerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindProviderType) {
			qPos.add(providerType);
		}

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(powwowServer)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<PowwowServer> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the powwow servers where providerType = &#63; and active = &#63; from the database.
	 *
	 * @param providerType the provider type
	 * @param active the active
	 */
	@Override
	public void removeByPT_A(String providerType, boolean active) {
		for (PowwowServer powwowServer :
				findByPT_A(
					providerType, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(powwowServer);
		}
	}

	/**
	 * Returns the number of powwow servers where providerType = &#63; and active = &#63;.
	 *
	 * @param providerType the provider type
	 * @param active the active
	 * @return the number of matching powwow servers
	 */
	@Override
	public int countByPT_A(String providerType, boolean active) {
		providerType = Objects.toString(providerType, "");

		FinderPath finderPath = _finderPathCountByPT_A;

		Object[] finderArgs = new Object[] {providerType, active};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POWWOWSERVER_WHERE);

			boolean bindProviderType = false;

			if (providerType.isEmpty()) {
				query.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_3);
			}
			else {
				bindProviderType = true;

				query.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_2);
			}

			query.append(_FINDER_COLUMN_PT_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindProviderType) {
					qPos.add(providerType);
				}

				qPos.add(active);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PT_A_PROVIDERTYPE_2 =
		"powwowServer.providerType = ? AND ";

	private static final String _FINDER_COLUMN_PT_A_PROVIDERTYPE_3 =
		"(powwowServer.providerType IS NULL OR powwowServer.providerType = '') AND ";

	private static final String _FINDER_COLUMN_PT_A_ACTIVE_2 =
		"powwowServer.active = ?";

	public PowwowServerPersistenceImpl() {
		setModelClass(PowwowServer.class);

		setModelImplClass(PowwowServerImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(PowwowServerModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the powwow server in the entity cache if it is enabled.
	 *
	 * @param powwowServer the powwow server
	 */
	@Override
	public void cacheResult(PowwowServer powwowServer) {
		EntityCacheUtil.putResult(
			PowwowServerModelImpl.ENTITY_CACHE_ENABLED, PowwowServerImpl.class,
			powwowServer.getPrimaryKey(), powwowServer);

		powwowServer.resetOriginalValues();
	}

	/**
	 * Caches the powwow servers in the entity cache if it is enabled.
	 *
	 * @param powwowServers the powwow servers
	 */
	@Override
	public void cacheResult(List<PowwowServer> powwowServers) {
		for (PowwowServer powwowServer : powwowServers) {
			if (EntityCacheUtil.getResult(
					PowwowServerModelImpl.ENTITY_CACHE_ENABLED,
					PowwowServerImpl.class, powwowServer.getPrimaryKey()) ==
						null) {

				cacheResult(powwowServer);
			}
			else {
				powwowServer.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all powwow servers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PowwowServerImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the powwow server.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PowwowServer powwowServer) {
		EntityCacheUtil.removeResult(
			PowwowServerModelImpl.ENTITY_CACHE_ENABLED, PowwowServerImpl.class,
			powwowServer.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<PowwowServer> powwowServers) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PowwowServer powwowServer : powwowServers) {
			EntityCacheUtil.removeResult(
				PowwowServerModelImpl.ENTITY_CACHE_ENABLED,
				PowwowServerImpl.class, powwowServer.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				PowwowServerModelImpl.ENTITY_CACHE_ENABLED,
				PowwowServerImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new powwow server with the primary key. Does not add the powwow server to the database.
	 *
	 * @param powwowServerId the primary key for the new powwow server
	 * @return the new powwow server
	 */
	@Override
	public PowwowServer create(long powwowServerId) {
		PowwowServer powwowServer = new PowwowServerImpl();

		powwowServer.setNew(true);
		powwowServer.setPrimaryKey(powwowServerId);

		powwowServer.setCompanyId(CompanyThreadLocal.getCompanyId());

		return powwowServer;
	}

	/**
	 * Removes the powwow server with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param powwowServerId the primary key of the powwow server
	 * @return the powwow server that was removed
	 * @throws NoSuchServerException if a powwow server with the primary key could not be found
	 */
	@Override
	public PowwowServer remove(long powwowServerId)
		throws NoSuchServerException {

		return remove((Serializable)powwowServerId);
	}

	/**
	 * Removes the powwow server with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the powwow server
	 * @return the powwow server that was removed
	 * @throws NoSuchServerException if a powwow server with the primary key could not be found
	 */
	@Override
	public PowwowServer remove(Serializable primaryKey)
		throws NoSuchServerException {

		Session session = null;

		try {
			session = openSession();

			PowwowServer powwowServer = (PowwowServer)session.get(
				PowwowServerImpl.class, primaryKey);

			if (powwowServer == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchServerException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(powwowServer);
		}
		catch (NoSuchServerException nsee) {
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
	protected PowwowServer removeImpl(PowwowServer powwowServer) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(powwowServer)) {
				powwowServer = (PowwowServer)session.get(
					PowwowServerImpl.class, powwowServer.getPrimaryKeyObj());
			}

			if (powwowServer != null) {
				session.delete(powwowServer);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (powwowServer != null) {
			clearCache(powwowServer);
		}

		return powwowServer;
	}

	@Override
	public PowwowServer updateImpl(PowwowServer powwowServer) {
		boolean isNew = powwowServer.isNew();

		if (!(powwowServer instanceof PowwowServerModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(powwowServer.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					powwowServer);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in powwowServer proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PowwowServer implementation " +
					powwowServer.getClass());
		}

		PowwowServerModelImpl powwowServerModelImpl =
			(PowwowServerModelImpl)powwowServer;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (powwowServer.getCreateDate() == null)) {
			if (serviceContext == null) {
				powwowServer.setCreateDate(now);
			}
			else {
				powwowServer.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!powwowServerModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				powwowServer.setModifiedDate(now);
			}
			else {
				powwowServer.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (powwowServer.isNew()) {
				session.save(powwowServer);

				powwowServer.setNew(false);
			}
			else {
				powwowServer = (PowwowServer)session.merge(powwowServer);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!PowwowServerModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				powwowServerModelImpl.getProviderType(),
				powwowServerModelImpl.isActive()
			};

			FinderCacheUtil.removeResult(_finderPathCountByPT_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByPT_A, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((powwowServerModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPT_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					powwowServerModelImpl.getOriginalProviderType(),
					powwowServerModelImpl.getOriginalActive()
				};

				FinderCacheUtil.removeResult(_finderPathCountByPT_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByPT_A, args);

				args = new Object[] {
					powwowServerModelImpl.getProviderType(),
					powwowServerModelImpl.isActive()
				};

				FinderCacheUtil.removeResult(_finderPathCountByPT_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByPT_A, args);
			}
		}

		EntityCacheUtil.putResult(
			PowwowServerModelImpl.ENTITY_CACHE_ENABLED, PowwowServerImpl.class,
			powwowServer.getPrimaryKey(), powwowServer, false);

		powwowServer.resetOriginalValues();

		return powwowServer;
	}

	/**
	 * Returns the powwow server with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the powwow server
	 * @return the powwow server
	 * @throws NoSuchServerException if a powwow server with the primary key could not be found
	 */
	@Override
	public PowwowServer findByPrimaryKey(Serializable primaryKey)
		throws NoSuchServerException {

		PowwowServer powwowServer = fetchByPrimaryKey(primaryKey);

		if (powwowServer == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchServerException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return powwowServer;
	}

	/**
	 * Returns the powwow server with the primary key or throws a <code>NoSuchServerException</code> if it could not be found.
	 *
	 * @param powwowServerId the primary key of the powwow server
	 * @return the powwow server
	 * @throws NoSuchServerException if a powwow server with the primary key could not be found
	 */
	@Override
	public PowwowServer findByPrimaryKey(long powwowServerId)
		throws NoSuchServerException {

		return findByPrimaryKey((Serializable)powwowServerId);
	}

	/**
	 * Returns the powwow server with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param powwowServerId the primary key of the powwow server
	 * @return the powwow server, or <code>null</code> if a powwow server with the primary key could not be found
	 */
	@Override
	public PowwowServer fetchByPrimaryKey(long powwowServerId) {
		return fetchByPrimaryKey((Serializable)powwowServerId);
	}

	/**
	 * Returns all the powwow servers.
	 *
	 * @return the powwow servers
	 */
	@Override
	public List<PowwowServer> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow servers
	 * @param end the upper bound of the range of powwow servers (not inclusive)
	 * @return the range of powwow servers
	 */
	@Override
	public List<PowwowServer> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow servers
	 * @param end the upper bound of the range of powwow servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of powwow servers
	 */
	@Override
	public List<PowwowServer> findAll(
		int start, int end, OrderByComparator<PowwowServer> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow servers
	 * @param end the upper bound of the range of powwow servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of powwow servers
	 */
	@Override
	public List<PowwowServer> findAll(
		int start, int end, OrderByComparator<PowwowServer> orderByComparator,
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

		List<PowwowServer> list = null;

		if (useFinderCache) {
			list = (List<PowwowServer>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_POWWOWSERVER);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_POWWOWSERVER;

				sql = sql.concat(PowwowServerModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<PowwowServer>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Removes all the powwow servers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PowwowServer powwowServer : findAll()) {
			remove(powwowServer);
		}
	}

	/**
	 * Returns the number of powwow servers.
	 *
	 * @return the number of powwow servers
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_POWWOWSERVER);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "powwowServerId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_POWWOWSERVER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PowwowServerModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the powwow server persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			PowwowServerModelImpl.ENTITY_CACHE_ENABLED,
			PowwowServerModelImpl.FINDER_CACHE_ENABLED, PowwowServerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			PowwowServerModelImpl.ENTITY_CACHE_ENABLED,
			PowwowServerModelImpl.FINDER_CACHE_ENABLED, PowwowServerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			PowwowServerModelImpl.ENTITY_CACHE_ENABLED,
			PowwowServerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByPT_A = new FinderPath(
			PowwowServerModelImpl.ENTITY_CACHE_ENABLED,
			PowwowServerModelImpl.FINDER_CACHE_ENABLED, PowwowServerImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPT_A",
			new String[] {
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPT_A = new FinderPath(
			PowwowServerModelImpl.ENTITY_CACHE_ENABLED,
			PowwowServerModelImpl.FINDER_CACHE_ENABLED, PowwowServerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPT_A",
			new String[] {String.class.getName(), Boolean.class.getName()},
			PowwowServerModelImpl.PROVIDERTYPE_COLUMN_BITMASK |
			PowwowServerModelImpl.ACTIVE_COLUMN_BITMASK |
			PowwowServerModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByPT_A = new FinderPath(
			PowwowServerModelImpl.ENTITY_CACHE_ENABLED,
			PowwowServerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPT_A",
			new String[] {String.class.getName(), Boolean.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PowwowServerImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_POWWOWSERVER =
		"SELECT powwowServer FROM PowwowServer powwowServer";

	private static final String _SQL_SELECT_POWWOWSERVER_WHERE =
		"SELECT powwowServer FROM PowwowServer powwowServer WHERE ";

	private static final String _SQL_COUNT_POWWOWSERVER =
		"SELECT COUNT(powwowServer) FROM PowwowServer powwowServer";

	private static final String _SQL_COUNT_POWWOWSERVER_WHERE =
		"SELECT COUNT(powwowServer) FROM PowwowServer powwowServer WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "powwowServer.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PowwowServer exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PowwowServer exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PowwowServerPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"active"});

}