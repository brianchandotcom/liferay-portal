/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchVirtualHostException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.model.impl.VirtualHostImpl;
import com.liferay.portal.model.impl.VirtualHostModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the virtual host service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostPersistence
 * @see VirtualHostUtil
 * @generated
 */
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
	public static final FinderPath FINDER_PATH_FETCH_BY_HOSTNAME = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, VirtualHostImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByHostname",
			new String[] { String.class.getName() },
			VirtualHostModelImpl.HOSTNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_HOSTNAME = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByHostname",
			new String[] { String.class.getName() });
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, VirtualHostImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, VirtualHostImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the virtual host in the entity cache if it is enabled.
	 *
	 * @param virtualHost the virtual host
	 */
	public void cacheResult(VirtualHost virtualHost) {
		EntityCacheUtil.putResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostImpl.class, virtualHost.getPrimaryKey(), virtualHost);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTNAME,
			new Object[] { virtualHost.getHostname() }, virtualHost);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L,
			new Object[] {
				Long.valueOf(virtualHost.getCompanyId()),
				Long.valueOf(virtualHost.getLayoutSetId())
			}, virtualHost);

		virtualHost.resetOriginalValues();
	}

	/**
	 * Caches the virtual hosts in the entity cache if it is enabled.
	 *
	 * @param virtualHosts the virtual hosts
	 */
	public void cacheResult(List<VirtualHost> virtualHosts) {
		for (VirtualHost virtualHost : virtualHosts) {
			if (EntityCacheUtil.getResult(
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
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(VirtualHostImpl.class.getName());
		}

		EntityCacheUtil.clearCache(VirtualHostImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the virtual host.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(VirtualHost virtualHost) {
		EntityCacheUtil.removeResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostImpl.class, virtualHost.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(virtualHost);
	}

	@Override
	public void clearCache(List<VirtualHost> virtualHosts) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (VirtualHost virtualHost : virtualHosts) {
			EntityCacheUtil.removeResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
				VirtualHostImpl.class, virtualHost.getPrimaryKey());

			clearUniqueFindersCache(virtualHost);
		}
	}

	protected void clearUniqueFindersCache(VirtualHost virtualHost) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HOSTNAME,
			new Object[] { virtualHost.getHostname() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L,
			new Object[] {
				Long.valueOf(virtualHost.getCompanyId()),
				Long.valueOf(virtualHost.getLayoutSetId())
			});
	}

	/**
	 * Creates a new virtual host with the primary key. Does not add the virtual host to the database.
	 *
	 * @param virtualHostId the primary key for the new virtual host
	 * @return the new virtual host
	 */
	public VirtualHost create(long virtualHostId) {
		VirtualHost virtualHost = new VirtualHostImpl();

		virtualHost.setNew(true);
		virtualHost.setPrimaryKey(virtualHostId);

		return virtualHost;
	}

	/**
	 * Removes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host that was removed
	 * @throws com.liferay.portal.NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost remove(long virtualHostId)
		throws NoSuchVirtualHostException, SystemException {
		return remove(Long.valueOf(virtualHostId));
	}

	/**
	 * Removes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the virtual host
	 * @return the virtual host that was removed
	 * @throws com.liferay.portal.NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VirtualHost remove(Serializable primaryKey)
		throws NoSuchVirtualHostException, SystemException {
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
	protected VirtualHost removeImpl(VirtualHost virtualHost)
		throws SystemException {
		virtualHost = toUnwrappedModel(virtualHost);

		Session session = null;

		try {
			session = openSession();

			if (virtualHost.isCachedModel()) {
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
	public VirtualHost updateImpl(
		com.liferay.portal.model.VirtualHost virtualHost)
		throws SystemException {
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
				session.merge(virtualHost);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !VirtualHostModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
			VirtualHostImpl.class, virtualHost.getPrimaryKey(), virtualHost);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTNAME,
				new Object[] { virtualHost.getHostname() }, virtualHost);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L,
				new Object[] {
					Long.valueOf(virtualHost.getCompanyId()),
					Long.valueOf(virtualHost.getLayoutSetId())
				}, virtualHost);
		}
		else {
			if ((virtualHostModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_HOSTNAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						virtualHostModelImpl.getOriginalHostname()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_HOSTNAME, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HOSTNAME, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTNAME,
					new Object[] { virtualHost.getHostname() }, virtualHost);
			}

			if ((virtualHostModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(virtualHostModelImpl.getOriginalCompanyId()),
						Long.valueOf(virtualHostModelImpl.getOriginalLayoutSetId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_L, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L,
					new Object[] {
						Long.valueOf(virtualHost.getCompanyId()),
						Long.valueOf(virtualHost.getLayoutSetId())
					}, virtualHost);
			}
		}

		return virtualHost;
	}

	protected VirtualHost toUnwrappedModel(VirtualHost virtualHost) {
		if (virtualHost instanceof VirtualHostImpl) {
			return virtualHost;
		}

		VirtualHostImpl virtualHostImpl = new VirtualHostImpl();

		virtualHostImpl.setNew(virtualHost.isNew());
		virtualHostImpl.setPrimaryKey(virtualHost.getPrimaryKey());

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
	 * @throws com.liferay.portal.NoSuchModelException if a virtual host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VirtualHost findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the virtual host with the primary key or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host
	 * @throws com.liferay.portal.NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost findByPrimaryKey(long virtualHostId)
		throws NoSuchVirtualHostException, SystemException {
		VirtualHost virtualHost = fetchByPrimaryKey(virtualHostId);

		if (virtualHost == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + virtualHostId);
			}

			throw new NoSuchVirtualHostException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				virtualHostId);
		}

		return virtualHost;
	}

	/**
	 * Returns the virtual host with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the virtual host
	 * @return the virtual host, or <code>null</code> if a virtual host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VirtualHost fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the virtual host with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host, or <code>null</code> if a virtual host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost fetchByPrimaryKey(long virtualHostId)
		throws SystemException {
		VirtualHost virtualHost = (VirtualHost)EntityCacheUtil.getResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
				VirtualHostImpl.class, virtualHostId);

		if (virtualHost == _nullVirtualHost) {
			return null;
		}

		if (virtualHost == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				virtualHost = (VirtualHost)session.get(VirtualHostImpl.class,
						Long.valueOf(virtualHostId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (virtualHost != null) {
					cacheResult(virtualHost);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(VirtualHostModelImpl.ENTITY_CACHE_ENABLED,
						VirtualHostImpl.class, virtualHostId, _nullVirtualHost);
				}

				closeSession(session);
			}
		}

		return virtualHost;
	}

	/**
	 * Returns the virtual host where hostname = &#63; or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	 *
	 * @param hostname the hostname
	 * @return the matching virtual host
	 * @throws com.liferay.portal.NoSuchVirtualHostException if a matching virtual host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost findByHostname(String hostname)
		throws NoSuchVirtualHostException, SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost fetchByHostname(String hostname)
		throws SystemException {
		return fetchByHostname(hostname, true);
	}

	/**
	 * Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param hostname the hostname
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost fetchByHostname(String hostname,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { hostname };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_HOSTNAME,
					finderArgs, this);
		}

		if (result instanceof VirtualHost) {
			VirtualHost virtualHost = (VirtualHost)result;

			if (!Validator.equals(hostname, virtualHost.getHostname())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_VIRTUALHOST_WHERE);

			if (hostname == null) {
				query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_1);
			}
			else {
				if (hostname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (hostname != null) {
					qPos.add(hostname);
				}

				List<VirtualHost> list = q.list();

				result = list;

				VirtualHost virtualHost = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTNAME,
						finderArgs, list);
				}
				else {
					virtualHost = list.get(0);

					cacheResult(virtualHost);

					if ((virtualHost.getHostname() == null) ||
							!virtualHost.getHostname().equals(hostname)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTNAME,
							finderArgs, virtualHost);
					}
				}

				return virtualHost;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HOSTNAME,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (VirtualHost)result;
			}
		}
	}

	/**
	 * Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @return the matching virtual host
	 * @throws com.liferay.portal.NoSuchVirtualHostException if a matching virtual host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost findByC_L(long companyId, long layoutSetId)
		throws NoSuchVirtualHostException, SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost fetchByC_L(long companyId, long layoutSetId)
		throws SystemException {
		return fetchByC_L(companyId, layoutSetId, true);
	}

	/**
	 * Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost fetchByC_L(long companyId, long layoutSetId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, layoutSetId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_L,
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
			StringBundler query = new StringBundler(3);

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

				result = list;

				VirtualHost virtualHost = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L,
						finderArgs, list);
				}
				else {
					virtualHost = list.get(0);

					cacheResult(virtualHost);

					if ((virtualHost.getCompanyId() != companyId) ||
							(virtualHost.getLayoutSetId() != layoutSetId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L,
							finderArgs, virtualHost);
					}
				}

				return virtualHost;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (VirtualHost)result;
			}
		}
	}

	/**
	 * Returns all the virtual hosts.
	 *
	 * @return the virtual hosts
	 * @throws SystemException if a system exception occurred
	 */
	public List<VirtualHost> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @return the range of virtual hosts
	 * @throws SystemException if a system exception occurred
	 */
	public List<VirtualHost> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of virtual hosts
	 * @throws SystemException if a system exception occurred
	 */
	public List<VirtualHost> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<VirtualHost> list = (List<VirtualHost>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

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
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<VirtualHost>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<VirtualHost>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes the virtual host where hostname = &#63; from the database.
	 *
	 * @param hostname the hostname
	 * @return the virtual host that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost removeByHostname(String hostname)
		throws NoSuchVirtualHostException, SystemException {
		VirtualHost virtualHost = findByHostname(hostname);

		return remove(virtualHost);
	}

	/**
	 * Removes the virtual host where companyId = &#63; and layoutSetId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @return the virtual host that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public VirtualHost removeByC_L(long companyId, long layoutSetId)
		throws NoSuchVirtualHostException, SystemException {
		VirtualHost virtualHost = findByC_L(companyId, layoutSetId);

		return remove(virtualHost);
	}

	/**
	 * Removes all the virtual hosts from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (VirtualHost virtualHost : findAll()) {
			remove(virtualHost);
		}
	}

	/**
	 * Returns the number of virtual hosts where hostname = &#63;.
	 *
	 * @param hostname the hostname
	 * @return the number of matching virtual hosts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByHostname(String hostname) throws SystemException {
		Object[] finderArgs = new Object[] { hostname };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_HOSTNAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_VIRTUALHOST_WHERE);

			if (hostname == null) {
				query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_1);
			}
			else {
				if (hostname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_HOSTNAME_HOSTNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (hostname != null) {
					qPos.add(hostname);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_HOSTNAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @return the number of matching virtual hosts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_L(long companyId, long layoutSetId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, layoutSetId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_L,
				finderArgs, this);

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
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_L, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of virtual hosts.
	 *
	 * @return the number of virtual hosts
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_VIRTUALHOST);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the virtual host persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.VirtualHost")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<VirtualHost>> listenersList = new ArrayList<ModelListener<VirtualHost>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<VirtualHost>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(VirtualHostImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_VIRTUALHOST = "SELECT virtualHost FROM VirtualHost virtualHost";
	private static final String _SQL_SELECT_VIRTUALHOST_WHERE = "SELECT virtualHost FROM VirtualHost virtualHost WHERE ";
	private static final String _SQL_COUNT_VIRTUALHOST = "SELECT COUNT(virtualHost) FROM VirtualHost virtualHost";
	private static final String _SQL_COUNT_VIRTUALHOST_WHERE = "SELECT COUNT(virtualHost) FROM VirtualHost virtualHost WHERE ";
	private static final String _FINDER_COLUMN_HOSTNAME_HOSTNAME_1 = "virtualHost.hostname IS NULL";
	private static final String _FINDER_COLUMN_HOSTNAME_HOSTNAME_2 = "virtualHost.hostname = ?";
	private static final String _FINDER_COLUMN_HOSTNAME_HOSTNAME_3 = "(virtualHost.hostname IS NULL OR virtualHost.hostname = ?)";
	private static final String _FINDER_COLUMN_C_L_COMPANYID_2 = "virtualHost.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_L_LAYOUTSETID_2 = "virtualHost.layoutSetId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "virtualHost.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No VirtualHost exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No VirtualHost exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(VirtualHostPersistenceImpl.class);
	private static VirtualHost _nullVirtualHost = new VirtualHostImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<VirtualHost> toCacheModel() {
				return _nullVirtualHostCacheModel;
			}
		};

	private static CacheModel<VirtualHost> _nullVirtualHostCacheModel = new CacheModel<VirtualHost>() {
			public VirtualHost toEntityModel() {
				return _nullVirtualHost;
			}
		};
}