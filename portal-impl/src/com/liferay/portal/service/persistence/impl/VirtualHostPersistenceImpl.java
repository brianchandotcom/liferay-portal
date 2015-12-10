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

import com.liferay.portal.NoSuchVirtualHostException;
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
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.model.impl.VirtualHostImpl;
import com.liferay.portal.model.impl.VirtualHostModelImpl;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.VirtualHostPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the virtual host service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostPersistence
 * @see com.liferay.portal.service.persistence.VirtualHostUtil
 * @generated
 */
@ProviderType
public class VirtualHostPersistenceImpl extends BasePersistenceImpl<VirtualHost>
	implements VirtualHostPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link VirtualHostUtil} to access the virtual host persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = VirtualHostImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, VirtualHostImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, VirtualHostImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_HOSTNAME = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, VirtualHostImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByHostname",
			new String[] { String.class.getName() },
			VirtualHostModelImpl.HOSTNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_HOSTNAME = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByHostname",
			new String[] { String.class.getName() });

	/**
	 * Returns the virtual host where hostname = &#63; or throws a {@link NoSuchVirtualHostException} if it could not be found.
	 *
	 * @param hostname the hostname
	 * @return the matching virtual host
	 * @throws NoSuchVirtualHostException if a matching virtual host could not be found
	 */
	@Override
	public VirtualHost findByHostname(String hostname)
		throws NoSuchVirtualHostException {
		VirtualHost virtualHost = fetchByHostname(hostname);

		if (virtualHost == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("hostname=");
			msg.append(hostname);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchVirtualHostException(msg.toString());
		}

		return virtualHost;
	}

	/**
	 * Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param hostname the hostname
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	@Override
	public VirtualHost fetchByHostname(String hostname) {
		return fetchByHostname(hostname, true);
	}

	/**
	 * Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param hostname the hostname
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	@Override
	public VirtualHost fetchByHostname(String hostname,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { hostname };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_HOSTNAME,
					finderArgs, this);
		}

		if (result instanceof VirtualHost) {
			VirtualHost virtualHost = (VirtualHost)result;

			if (!Validator.equals(hostname, virtualHost.getHostname())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_VIRTUALHOST_WHERE);

			boolean bindHostname = false;

			if (hostname == null) {
				query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_1);
			}
			else if (hostname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_3);
			}
			else {
				bindHostname = true;

				query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindHostname) {
					qPos.add(hostname);
				}

				List<VirtualHost> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_HOSTNAME,
						finderArgs, list);
				}
				else {
					VirtualHost virtualHost = list.get(0);

					result = virtualHost;

					cacheResult(virtualHost);

					if ((virtualHost.getHostname() == null) ||
							!virtualHost.getHostname().equals(hostname)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_HOSTNAME,
							finderArgs, virtualHost);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_HOSTNAME,
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
			return (VirtualHost)result;
		}
	}

	/**
	 * Removes the virtual host where hostname = &#63; from the database.
	 *
	 * @param hostname the hostname
	 * @return the virtual host that was removed
	 */
	@Override
	public VirtualHost removeByHostname(String hostname)
		throws NoSuchVirtualHostException {
		VirtualHost virtualHost = findByHostname(hostname);

		return remove(virtualHost);
	}

	/**
	 * Returns the number of virtual hosts where hostname = &#63;.
	 *
	 * @param hostname the hostname
	 * @return the number of matching virtual hosts
	 */
	@Override
	public int countByHostname(String hostname) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_HOSTNAME;

		Object[] finderArgs = new Object[] { hostname };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_VIRTUALHOST_WHERE);

			boolean bindHostname = false;

			if (hostname == null) {
				query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_1);
			}
			else if (hostname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_3);
			}
			else {
				bindHostname = true;

				query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindHostname) {
					qPos.add(hostname);
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

	private static final String _FINDER_COLUMN_HOSTNAME_HOSTNAME_1 = "virtualHost.hostname IS NULL";
	private static final String _FINDER_COLUMN_HOSTNAME_HOSTNAME_2 = "virtualHost.hostname = ?";
	private static final String _FINDER_COLUMN_HOSTNAME_HOSTNAME_3 = "(virtualHost.hostname IS NULL OR virtualHost.hostname = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_L = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, VirtualHostImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_L",
			new String[] { Long.class.getName(), Long.class.getName() },
			VirtualHostModelImpl.COMPANYID_COLUMN_BITMASK |
			VirtualHostModelImpl.LAYOUTSETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_L = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_L",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or throws a {@link NoSuchVirtualHostException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @return the matching virtual host
	 * @throws NoSuchVirtualHostException if a matching virtual host could not be found
	 */
	@Override
	public VirtualHost findByC_L(long companyId, long layoutSetId)
		throws NoSuchVirtualHostException {
		VirtualHost virtualHost = fetchByC_L(companyId, layoutSetId);

		if (virtualHost == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", layoutSetId=");
			msg.append(layoutSetId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchVirtualHostException(msg.toString());
		}

		return virtualHost;
	}

	/**
	 * Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	@Override
	public VirtualHost fetchByC_L(long companyId, long layoutSetId) {
		return fetchByC_L(companyId, layoutSetId, true);
	}

	/**
	 * Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	@Override
	public VirtualHost fetchByC_L(long companyId, long layoutSetId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, layoutSetId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_L,
					finderArgs, this);
		}

		if (result instanceof VirtualHost) {
			VirtualHost virtualHost = (VirtualHost)result;

			if ((companyId != virtualHost.getCompanyId()) ||
					(layoutSetId != virtualHost.getLayoutSetId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_VIRTUALHOST_WHERE);

			query.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_L_LAYOUTSETID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(layoutSetId);

				List<VirtualHost> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_L, finderArgs,
						list);
				}
				else {
					VirtualHost virtualHost = list.get(0);

					result = virtualHost;

					cacheResult(virtualHost);

					if ((virtualHost.getCompanyId() != companyId) ||
							(virtualHost.getLayoutSetId() != layoutSetId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_L,
							finderArgs, virtualHost);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_L, finderArgs);

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
			return (VirtualHost)result;
		}
	}

	/**
	 * Removes the virtual host where companyId = &#63; and layoutSetId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @return the virtual host that was removed
	 */
	@Override
	public VirtualHost removeByC_L(long companyId, long layoutSetId)
		throws NoSuchVirtualHostException {
		VirtualHost virtualHost = findByC_L(companyId, layoutSetId);

		return remove(virtualHost);
	}

	/**
	 * Returns the number of virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @return the number of matching virtual hosts
	 */
	@Override
	public int countByC_L(long companyId, long layoutSetId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_L;

		Object[] finderArgs = new Object[] { companyId, layoutSetId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_VIRTUALHOST_WHERE);

			query.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_L_LAYOUTSETID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(layoutSetId);

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

	private static final String _FINDER_COLUMN_C_L_COMPANYID_2 = "virtualHost.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_L_LAYOUTSETID_2 = "virtualHost.layoutSetId = ?";

	public VirtualHostPersistenceImpl() {
		setModelClass(VirtualHost.class);
	}

	/**
	 * Caches the virtual host in the entity cache if it is enabled.
	 *
	 * @param virtualHost the virtual host
	 */
	@Override
	public void cacheResult(VirtualHost virtualHost) {
		entityCache.putResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostImpl.class, virtualHost.getPrimaryKey(), virtualHost);

		finderCache.putResult(FINDER_PATH_FETCH_BY_HOSTNAME,
			new Object[] { virtualHost.getHostname() }, virtualHost);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_L,
			new Object[] {
				virtualHost.getCompanyId(), virtualHost.getLayoutSetId()
			}, virtualHost);

		virtualHost.resetOriginalValues();
	}

	/**
	 * Caches the virtual hosts in the entity cache if it is enabled.
	 *
	 * @param virtualHosts the virtual hosts
	 */
	@Override
	public void cacheResult(List<VirtualHost> virtualHosts) {
		for (VirtualHost virtualHost : virtualHosts) {
			if (entityCache.getResult(
						VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
						VirtualHostImpl.class, virtualHost.getPrimaryKey()) == null) {
				cacheResult(virtualHost);
			}
			else {
				virtualHost.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all virtual hosts.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(VirtualHostImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the virtual host.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(VirtualHost virtualHost) {
		entityCache.removeResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostImpl.class, virtualHost.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((VirtualHostModelImpl)virtualHost);
	}

	@Override
	public void clearCache(List<VirtualHost> virtualHosts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (VirtualHost virtualHost : virtualHosts) {
			entityCache.removeResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
				VirtualHostImpl.class, virtualHost.getPrimaryKey());

			clearUniqueFindersCache((VirtualHostModelImpl)virtualHost);
		}
	}

	protected void cacheUniqueFindersCache(
		VirtualHostModelImpl virtualHostModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] { virtualHostModelImpl.getHostname() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_HOSTNAME, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_HOSTNAME, args,
				virtualHostModelImpl);

			args = new Object[] {
					virtualHostModelImpl.getCompanyId(),
					virtualHostModelImpl.getLayoutSetId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_L, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_L, args,
				virtualHostModelImpl);
		}
		else {
			if ((virtualHostModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_HOSTNAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { virtualHostModelImpl.getHostname() };

				finderCache.putResult(FINDER_PATH_COUNT_BY_HOSTNAME, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_HOSTNAME, args,
					virtualHostModelImpl);
			}

			if ((virtualHostModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						virtualHostModelImpl.getCompanyId(),
						virtualHostModelImpl.getLayoutSetId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_L, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_L, args,
					virtualHostModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		VirtualHostModelImpl virtualHostModelImpl) {
		Object[] args = new Object[] { virtualHostModelImpl.getHostname() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_HOSTNAME, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_HOSTNAME, args);

		if ((virtualHostModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_HOSTNAME.getColumnBitmask()) != 0) {
			args = new Object[] { virtualHostModelImpl.getOriginalHostname() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_HOSTNAME, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_HOSTNAME, args);
		}

		args = new Object[] {
				virtualHostModelImpl.getCompanyId(),
				virtualHostModelImpl.getLayoutSetId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_L, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_L, args);

		if ((virtualHostModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_L.getColumnBitmask()) != 0) {
			args = new Object[] {
					virtualHostModelImpl.getOriginalCompanyId(),
					virtualHostModelImpl.getOriginalLayoutSetId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_L, args);
		}
	}

	/**
	 * Creates a new virtual host with the primary key. Does not add the virtual host to the database.
	 *
	 * @param virtualHostId the primary key for the new virtual host
	 * @return the new virtual host
	 */
	@Override
	public VirtualHost create(long virtualHostId) {
		VirtualHost virtualHost = new VirtualHostImpl();

		virtualHost.setNew(true);
		virtualHost.setPrimaryKey(virtualHostId);

		virtualHost.setCompanyId(companyProvider.getCompanyId());

		return virtualHost;
	}

	/**
	 * Removes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host that was removed
	 * @throws NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 */
	@Override
	public VirtualHost remove(long virtualHostId)
		throws NoSuchVirtualHostException {
		return remove((Serializable)virtualHostId);
	}

	/**
	 * Removes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the virtual host
	 * @return the virtual host that was removed
	 * @throws NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 */
	@Override
	public VirtualHost remove(Serializable primaryKey)
		throws NoSuchVirtualHostException {
		Session session = null;

		try {
			session = openSession();

			VirtualHost virtualHost = (VirtualHost)session.get(VirtualHostImpl.class,
					primaryKey);

			if (virtualHost == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVirtualHostException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(virtualHost);
		}
		catch (NoSuchVirtualHostException nsee) {
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
	protected VirtualHost removeImpl(VirtualHost virtualHost) {
		virtualHost = toUnwrappedModel(virtualHost);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(virtualHost)) {
				virtualHost = (VirtualHost)session.get(VirtualHostImpl.class,
						virtualHost.getPrimaryKeyObj());
			}

			if (virtualHost != null) {
				session.delete(virtualHost);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (virtualHost != null) {
			clearCache(virtualHost);
		}

		return virtualHost;
	}

	@Override
	public VirtualHost updateImpl(VirtualHost virtualHost) {
		virtualHost = toUnwrappedModel(virtualHost);

		boolean isNew = virtualHost.isNew();

		VirtualHostModelImpl virtualHostModelImpl = (VirtualHostModelImpl)virtualHost;

		Session session = null;

		try {
			session = openSession();

			if (virtualHost.isNew()) {
				session.save(virtualHost);

				virtualHost.setNew(false);
			}
			else {
				virtualHost = (VirtualHost)session.merge(virtualHost);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !VirtualHostModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		entityCache.putResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostImpl.class, virtualHost.getPrimaryKey(), virtualHost,
			false);

		clearUniqueFindersCache(virtualHostModelImpl);
		cacheUniqueFindersCache(virtualHostModelImpl, isNew);

		virtualHost.resetOriginalValues();

		return virtualHost;
	}

	protected VirtualHost toUnwrappedModel(VirtualHost virtualHost) {
		if (virtualHost instanceof VirtualHostImpl) {
			return virtualHost;
		}

		VirtualHostImpl virtualHostImpl = new VirtualHostImpl();

		virtualHostImpl.setNew(virtualHost.isNew());
		virtualHostImpl.setPrimaryKey(virtualHost.getPrimaryKey());

		virtualHostImpl.setMvccVersion(virtualHost.getMvccVersion());
		virtualHostImpl.setVirtualHostId(virtualHost.getVirtualHostId());
		virtualHostImpl.setCompanyId(virtualHost.getCompanyId());
		virtualHostImpl.setLayoutSetId(virtualHost.getLayoutSetId());
		virtualHostImpl.setHostname(virtualHost.getHostname());

		return virtualHostImpl;
	}

	/**
	 * Returns the virtual host with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the virtual host
	 * @return the virtual host
	 * @throws NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 */
	@Override
	public VirtualHost findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVirtualHostException {
		VirtualHost virtualHost = fetchByPrimaryKey(primaryKey);

		if (virtualHost == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVirtualHostException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return virtualHost;
	}

	/**
	 * Returns the virtual host with the primary key or throws a {@link NoSuchVirtualHostException} if it could not be found.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host
	 * @throws NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 */
	@Override
	public VirtualHost findByPrimaryKey(long virtualHostId)
		throws NoSuchVirtualHostException {
		return findByPrimaryKey((Serializable)virtualHostId);
	}

	/**
	 * Returns the virtual host with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the virtual host
	 * @return the virtual host, or <code>null</code> if a virtual host with the primary key could not be found
	 */
	@Override
	public VirtualHost fetchByPrimaryKey(Serializable primaryKey) {
		VirtualHost virtualHost = (VirtualHost)entityCache.getResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
				VirtualHostImpl.class, primaryKey);

		if (virtualHost == _nullVirtualHost) {
			return null;
		}

		if (virtualHost == null) {
			Session session = null;

			try {
				session = openSession();

				virtualHost = (VirtualHost)session.get(VirtualHostImpl.class,
						primaryKey);

				if (virtualHost != null) {
					cacheResult(virtualHost);
				}
				else {
					entityCache.putResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
						VirtualHostImpl.class, primaryKey, _nullVirtualHost);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
					VirtualHostImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return virtualHost;
	}

	/**
	 * Returns the virtual host with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host, or <code>null</code> if a virtual host with the primary key could not be found
	 */
	@Override
	public VirtualHost fetchByPrimaryKey(long virtualHostId) {
		return fetchByPrimaryKey((Serializable)virtualHostId);
	}

	@Override
	public Map<Serializable, VirtualHost> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, VirtualHost> map = new HashMap<Serializable, VirtualHost>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			VirtualHost virtualHost = fetchByPrimaryKey(primaryKey);

			if (virtualHost != null) {
				map.put(primaryKey, virtualHost);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			VirtualHost virtualHost = (VirtualHost)entityCache.getResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
					VirtualHostImpl.class, primaryKey);

			if (virtualHost == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, virtualHost);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_VIRTUALHOST_WHERE_PKS_IN);

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

			for (VirtualHost virtualHost : (List<VirtualHost>)q.list()) {
				map.put(virtualHost.getPrimaryKeyObj(), virtualHost);

				cacheResult(virtualHost);

				uncachedPrimaryKeys.remove(virtualHost.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
					VirtualHostImpl.class, primaryKey, _nullVirtualHost);
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
	 * Returns all the virtual hosts.
	 *
	 * @return the virtual hosts
	 */
	@Override
	public List<VirtualHost> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @return the range of virtual hosts
	 */
	@Override
	public List<VirtualHost> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of virtual hosts
	 */
	@Override
	public List<VirtualHost> findAll(int start, int end,
		OrderByComparator<VirtualHost> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of virtual hosts
	 */
	@Override
	public List<VirtualHost> findAll(int start, int end,
		OrderByComparator<VirtualHost> orderByComparator,
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

		List<VirtualHost> list = null;

		if (retrieveFromCache) {
			list = (List<VirtualHost>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_VIRTUALHOST);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_VIRTUALHOST;

				if (pagination) {
					sql = sql.concat(VirtualHostModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<VirtualHost>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<VirtualHost>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the virtual hosts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (VirtualHost virtualHost : findAll()) {
			remove(virtualHost);
		}
	}

	/**
	 * Returns the number of virtual hosts.
	 *
	 * @return the number of virtual hosts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_VIRTUALHOST);

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
		return VirtualHostModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the virtual host persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(VirtualHostImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_VIRTUALHOST = "SELECT virtualHost FROM VirtualHost virtualHost";
	private static final String _SQL_SELECT_VIRTUALHOST_WHERE_PKS_IN = "SELECT virtualHost FROM VirtualHost virtualHost WHERE virtualHostId IN (";
	private static final String _SQL_SELECT_VIRTUALHOST_WHERE = "SELECT virtualHost FROM VirtualHost virtualHost WHERE ";
	private static final String _SQL_COUNT_VIRTUALHOST = "SELECT COUNT(virtualHost) FROM VirtualHost virtualHost";
	private static final String _SQL_COUNT_VIRTUALHOST_WHERE = "SELECT COUNT(virtualHost) FROM VirtualHost virtualHost WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "virtualHost.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No VirtualHost exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No VirtualHost exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(VirtualHostPersistenceImpl.class);
	private static final VirtualHost _nullVirtualHost = new VirtualHostImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<VirtualHost> toCacheModel() {
				return _nullVirtualHostCacheModel;
			}
		};

	private static final CacheModel<VirtualHost> _nullVirtualHostCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<VirtualHost>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public VirtualHost toEntityModel() {
			return _nullVirtualHost;
		}
	}
}