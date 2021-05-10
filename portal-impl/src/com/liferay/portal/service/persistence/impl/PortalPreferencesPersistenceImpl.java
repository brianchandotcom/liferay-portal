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
import com.liferay.portal.kernel.exception.NoSuchPreferencesException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.PortalPreferences;
import com.liferay.portal.kernel.model.PortalPreferencesTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.PortalPreferencesPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.PortalPreferencesImpl;
import com.liferay.portal.model.impl.PortalPreferencesModelImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The persistence implementation for the portal preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortalPreferencesPersistenceImpl
	extends BasePersistenceImpl<PortalPreferences>
	implements PortalPreferencesPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PortalPreferencesUtil</code> to access the portal preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PortalPreferencesImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByO_O;
	private FinderPath _finderPathCountByO_O;

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the matching portal preferences
	 * @throws NoSuchPreferencesException if a matching portal preferences could not be found
	 */
	@Override
	public PortalPreferences findByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException {

		PortalPreferences portalPreferences = fetchByO_O(ownerId, ownerType);

		if (portalPreferences == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("ownerId=");
			sb.append(ownerId);

			sb.append(", ownerType=");
			sb.append(ownerType);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPreferencesException(sb.toString());
		}

		return portalPreferences;
	}

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	 */
	@Override
	public PortalPreferences fetchByO_O(long ownerId, int ownerType) {
		return fetchByO_O(ownerId, ownerType, true);
	}

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	 */
	@Override
	public PortalPreferences fetchByO_O(
		long ownerId, int ownerType, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {ownerId, ownerType};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByO_O, finderArgs);
		}

		if (result instanceof PortalPreferences) {
			PortalPreferences portalPreferences = (PortalPreferences)result;

			if ((ownerId != portalPreferences.getOwnerId()) ||
				(ownerType != portalPreferences.getOwnerType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_PORTALPREFERENCES_WHERE);

			sb.append(_FINDER_COLUMN_O_O_OWNERID_2);

			sb.append(_FINDER_COLUMN_O_O_OWNERTYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ownerId);

				queryPos.add(ownerType);

				List<PortalPreferences> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByO_O, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {ownerId, ownerType};
							}

							_log.warn(
								"PortalPreferencesPersistenceImpl.fetchByO_O(long, int, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					PortalPreferences portalPreferences = list.get(0);

					result = portalPreferences;

					cacheResult(portalPreferences);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (PortalPreferences)result;
		}
	}

	/**
	 * Removes the portal preferences where ownerId = &#63; and ownerType = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the portal preferences that was removed
	 */
	@Override
	public PortalPreferences removeByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException {

		PortalPreferences portalPreferences = findByO_O(ownerId, ownerType);

		return remove(portalPreferences);
	}

	/**
	 * Returns the number of portal preferenceses where ownerId = &#63; and ownerType = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the number of matching portal preferenceses
	 */
	@Override
	public int countByO_O(long ownerId, int ownerType) {
		FinderPath finderPath = _finderPathCountByO_O;

		Object[] finderArgs = new Object[] {ownerId, ownerType};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_PORTALPREFERENCES_WHERE);

			sb.append(_FINDER_COLUMN_O_O_OWNERID_2);

			sb.append(_FINDER_COLUMN_O_O_OWNERTYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ownerId);

				queryPos.add(ownerType);

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

	private static final String _FINDER_COLUMN_O_O_OWNERID_2 =
		"portalPreferences.ownerId = ? AND ";

	private static final String _FINDER_COLUMN_O_O_OWNERTYPE_2 =
		"portalPreferences.ownerType = ?";

	public PortalPreferencesPersistenceImpl() {
		setModelClass(PortalPreferences.class);

		setModelImplClass(PortalPreferencesImpl.class);
		setModelPKClass(long.class);

		setTable(PortalPreferencesTable.INSTANCE);
	}

	/**
	 * Caches the portal preferences in the entity cache if it is enabled.
	 *
	 * @param portalPreferences the portal preferences
	 */
	@Override
	public void cacheResult(PortalPreferences portalPreferences) {
		EntityCacheUtil.putResult(
			PortalPreferencesImpl.class, portalPreferences.getPrimaryKey(),
			portalPreferences);

		FinderCacheUtil.putResult(
			_finderPathFetchByO_O,
			new Object[] {
				portalPreferences.getOwnerId(), portalPreferences.getOwnerType()
			},
			portalPreferences);
	}

	/**
	 * Caches the portal preferenceses in the entity cache if it is enabled.
	 *
	 * @param portalPreferenceses the portal preferenceses
	 */
	@Override
	public void cacheResult(List<PortalPreferences> portalPreferenceses) {
		for (PortalPreferences portalPreferences : portalPreferenceses) {
			if (EntityCacheUtil.getResult(
					PortalPreferencesImpl.class,
					portalPreferences.getPrimaryKey()) == null) {

				cacheResult(portalPreferences);
			}
		}
	}

	/**
	 * Clears the cache for all portal preferenceses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PortalPreferencesImpl.class);

		FinderCacheUtil.clearCache(PortalPreferencesImpl.class);
	}

	/**
	 * Clears the cache for the portal preferences.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PortalPreferences portalPreferences) {
		EntityCacheUtil.removeResult(
			PortalPreferencesImpl.class, portalPreferences);
	}

	@Override
	public void clearCache(List<PortalPreferences> portalPreferenceses) {
		for (PortalPreferences portalPreferences : portalPreferenceses) {
			EntityCacheUtil.removeResult(
				PortalPreferencesImpl.class, portalPreferences);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(PortalPreferencesImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				PortalPreferencesImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PortalPreferencesModelImpl portalPreferencesModelImpl) {

		Object[] args = new Object[] {
			portalPreferencesModelImpl.getOwnerId(),
			portalPreferencesModelImpl.getOwnerType()
		};

		FinderCacheUtil.putResult(_finderPathCountByO_O, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByO_O, args, portalPreferencesModelImpl);
	}

	/**
	 * Creates a new portal preferences with the primary key. Does not add the portal preferences to the database.
	 *
	 * @param portalPreferencesId the primary key for the new portal preferences
	 * @return the new portal preferences
	 */
	@Override
	public PortalPreferences create(long portalPreferencesId) {
		PortalPreferences portalPreferences = new PortalPreferencesImpl();

		portalPreferences.setNew(true);
		portalPreferences.setPrimaryKey(portalPreferencesId);

		portalPreferences.setCompanyId(CompanyThreadLocal.getCompanyId());

		return portalPreferences;
	}

	/**
	 * Removes the portal preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences that was removed
	 * @throws NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences remove(long portalPreferencesId)
		throws NoSuchPreferencesException {

		return remove((Serializable)portalPreferencesId);
	}

	/**
	 * Removes the portal preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portal preferences
	 * @return the portal preferences that was removed
	 * @throws NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences remove(Serializable primaryKey)
		throws NoSuchPreferencesException {

		Session session = null;

		try {
			session = openSession();

			PortalPreferences portalPreferences =
				(PortalPreferences)session.get(
					PortalPreferencesImpl.class, primaryKey);

			if (portalPreferences == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPreferencesException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(portalPreferences);
		}
		catch (NoSuchPreferencesException noSuchEntityException) {
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
	protected PortalPreferences removeImpl(
		PortalPreferences portalPreferences) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portalPreferences)) {
				portalPreferences = (PortalPreferences)session.get(
					PortalPreferencesImpl.class,
					portalPreferences.getPrimaryKeyObj());
			}

			if (portalPreferences != null) {
				session.delete(portalPreferences);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (portalPreferences != null) {
			clearCache(portalPreferences);
		}

		return portalPreferences;
	}

	@Override
	public PortalPreferences updateImpl(PortalPreferences portalPreferences) {
		boolean isNew = portalPreferences.isNew();

		if (!(portalPreferences instanceof PortalPreferencesModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(portalPreferences.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					portalPreferences);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in portalPreferences proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PortalPreferences implementation " +
					portalPreferences.getClass());
		}

		PortalPreferencesModelImpl portalPreferencesModelImpl =
			(PortalPreferencesModelImpl)portalPreferences;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(portalPreferences);
			}
			else {
				portalPreferences = (PortalPreferences)session.merge(
					portalPreferences);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			PortalPreferencesImpl.class, portalPreferencesModelImpl, false,
			true);

		cacheUniqueFindersCache(portalPreferencesModelImpl);

		if (isNew) {
			portalPreferences.setNew(false);
		}

		portalPreferences.resetOriginalValues();

		return portalPreferences;
	}

	/**
	 * Returns the portal preferences with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portal preferences
	 * @return the portal preferences
	 * @throws NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPreferencesException {

		PortalPreferences portalPreferences = fetchByPrimaryKey(primaryKey);

		if (portalPreferences == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPreferencesException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return portalPreferences;
	}

	/**
	 * Returns the portal preferences with the primary key or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences
	 * @throws NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences findByPrimaryKey(long portalPreferencesId)
		throws NoSuchPreferencesException {

		return findByPrimaryKey((Serializable)portalPreferencesId);
	}

	/**
	 * Returns the portal preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences, or <code>null</code> if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences fetchByPrimaryKey(long portalPreferencesId) {
		return fetchByPrimaryKey((Serializable)portalPreferencesId);
	}

	/**
	 * Returns all the portal preferenceses.
	 *
	 * @return the portal preferenceses
	 */
	@Override
	public List<PortalPreferences> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portal preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @return the range of portal preferenceses
	 */
	@Override
	public List<PortalPreferences> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portal preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portal preferenceses
	 */
	@Override
	public List<PortalPreferences> findAll(
		int start, int end,
		OrderByComparator<PortalPreferences> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portal preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of portal preferenceses
	 */
	@Override
	public List<PortalPreferences> findAll(
		int start, int end,
		OrderByComparator<PortalPreferences> orderByComparator,
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

		List<PortalPreferences> list = null;

		if (useFinderCache) {
			list = (List<PortalPreferences>)FinderCacheUtil.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PORTALPREFERENCES);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PORTALPREFERENCES;

				sql = sql.concat(PortalPreferencesModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PortalPreferences>)QueryUtil.list(
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
	 * Removes all the portal preferenceses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PortalPreferences portalPreferences : findAll()) {
			remove(portalPreferences);
		}
	}

	/**
	 * Returns the number of portal preferenceses.
	 *
	 * @return the number of portal preferenceses
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_PORTALPREFERENCES);

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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "portalPreferencesId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PORTALPREFERENCES;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PortalPreferencesModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the portal preferences persistence.
	 */
	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_argumentsResolverServiceRegistration = registry.registerService(
			ArgumentsResolver.class,
			new PortalPreferencesModelArgumentsResolver());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByO_O = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByO_O",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"ownerId", "ownerType"}, true);

		_finderPathCountByO_O = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_O",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"ownerId", "ownerType"}, false);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PortalPreferencesImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private static final String _SQL_SELECT_PORTALPREFERENCES =
		"SELECT portalPreferences FROM PortalPreferences portalPreferences";

	private static final String _SQL_SELECT_PORTALPREFERENCES_WHERE =
		"SELECT portalPreferences FROM PortalPreferences portalPreferences WHERE ";

	private static final String _SQL_COUNT_PORTALPREFERENCES =
		"SELECT COUNT(portalPreferences) FROM PortalPreferences portalPreferences";

	private static final String _SQL_COUNT_PORTALPREFERENCES_WHERE =
		"SELECT COUNT(portalPreferences) FROM PortalPreferences portalPreferences WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "portalPreferences.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PortalPreferences exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PortalPreferences exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalPreferencesPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class PortalPreferencesModelArgumentsResolver
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

			PortalPreferencesModelImpl portalPreferencesModelImpl =
				(PortalPreferencesModelImpl)baseModel;

			long columnBitmask = portalPreferencesModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					portalPreferencesModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						portalPreferencesModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					portalPreferencesModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return PortalPreferencesImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return PortalPreferencesTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			PortalPreferencesModelImpl portalPreferencesModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						portalPreferencesModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = portalPreferencesModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}