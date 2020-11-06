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
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.powwow.exception.NoSuchServerException;
import com.liferay.powwow.model.PowwowServer;
import com.liferay.powwow.model.PowwowServerTable;
import com.liferay.powwow.model.impl.PowwowServerImpl;
import com.liferay.powwow.model.impl.PowwowServerModelImpl;
import com.liferay.powwow.service.persistence.PowwowServerPersistence;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

	/*
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
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_POWWOWSERVER_WHERE);

			boolean bindProviderType = false;

			if (providerType.isEmpty()) {
				sb.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_3);
			}
			else {
				bindProviderType = true;

				sb.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_2);
			}

			sb.append(_FINDER_COLUMN_PT_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PowwowServerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindProviderType) {
					queryPos.add(providerType);
				}

				queryPos.add(active);

				list = (List<PowwowServer>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("providerType=");
		sb.append(providerType);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchServerException(sb.toString());
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

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("providerType=");
		sb.append(providerType);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchServerException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PowwowServer getByPT_A_PrevAndNext(
		Session session, PowwowServer powwowServer, String providerType,
		boolean active, OrderByComparator<PowwowServer> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_POWWOWSERVER_WHERE);

		boolean bindProviderType = false;

		if (providerType.isEmpty()) {
			sb.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_3);
		}
		else {
			bindProviderType = true;

			sb.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_2);
		}

		sb.append(_FINDER_COLUMN_PT_A_ACTIVE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(PowwowServerModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindProviderType) {
			queryPos.add(providerType);
		}

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(powwowServer)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PowwowServer> list = query.list();

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

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POWWOWSERVER_WHERE);

			boolean bindProviderType = false;

			if (providerType.isEmpty()) {
				sb.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_3);
			}
			else {
				bindProviderType = true;

				sb.append(_FINDER_COLUMN_PT_A_PROVIDERTYPE_2);
			}

			sb.append(_FINDER_COLUMN_PT_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindProviderType) {
					queryPos.add(providerType);
				}

				queryPos.add(active);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
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
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(PowwowServer.class);

		setModelImplClass(PowwowServerImpl.class);
		setModelPKClass(long.class);

		setTable(PowwowServerTable.INSTANCE);
	}

	/**
	 * Caches the powwow server in the entity cache if it is enabled.
	 *
	 * @param powwowServer the powwow server
	 */
	@Override
	public void cacheResult(PowwowServer powwowServer) {
		EntityCacheUtil.putResult(
			PowwowServerImpl.class, powwowServer.getPrimaryKey(), powwowServer);
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
					PowwowServerImpl.class, powwowServer.getPrimaryKey()) ==
						null) {

				cacheResult(powwowServer);
			}
		}
	}

	/**
	 * Clears the cache for all powwow servers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PowwowServerImpl.class);

		FinderCacheUtil.clearCache(PowwowServerImpl.class);
	}

	/**
	 * Clears the cache for the powwow server.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PowwowServer powwowServer) {
		EntityCacheUtil.removeResult(PowwowServerImpl.class, powwowServer);
	}

	@Override
	public void clearCache(List<PowwowServer> powwowServers) {
		for (PowwowServer powwowServer : powwowServers) {
			EntityCacheUtil.removeResult(PowwowServerImpl.class, powwowServer);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(PowwowServerImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(PowwowServerImpl.class, primaryKey);
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
		catch (NoSuchServerException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
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
		catch (Exception exception) {
			throw processException(exception);
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

			if (isNew) {
				session.save(powwowServer);
			}
			else {
				powwowServer = (PowwowServer)session.merge(powwowServer);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			PowwowServerImpl.class, powwowServerModelImpl, false, true);

		if (isNew) {
			powwowServer.setNew(false);
		}

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
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_POWWOWSERVER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_POWWOWSERVER;

				sql = sql.concat(PowwowServerModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PowwowServer>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_POWWOWSERVER);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
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
		Registry registry = RegistryUtil.getRegistry();

		_argumentsResolverServiceRegistration = registry.registerService(
			ArgumentsResolver.class, new PowwowServerModelArgumentsResolver());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByPT_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPT_A",
			new String[] {
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"providerType", "active_"}, true);

		_finderPathWithoutPaginationFindByPT_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPT_A",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"providerType", "active_"}, true);

		_finderPathCountByPT_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPT_A",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"providerType", "active_"}, false);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PowwowServerImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
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

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class PowwowServerModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			PowwowServerModelImpl powwowServerModelImpl =
				(PowwowServerModelImpl)baseModel;

			long columnBitmask = powwowServerModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(powwowServerModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						powwowServerModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(powwowServerModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return PowwowServerImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return PowwowServerTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			PowwowServerModelImpl powwowServerModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] = powwowServerModelImpl.getColumnOriginalValue(
						columnName);
				}
				else {
					arguments[i] = powwowServerModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}