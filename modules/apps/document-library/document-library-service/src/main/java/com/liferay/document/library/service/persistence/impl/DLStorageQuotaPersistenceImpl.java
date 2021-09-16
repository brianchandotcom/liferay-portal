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

package com.liferay.document.library.service.persistence.impl;

import com.liferay.document.library.exception.NoSuchStorageQuotaException;
import com.liferay.document.library.model.DLStorageQuota;
import com.liferay.document.library.model.DLStorageQuotaTable;
import com.liferay.document.library.model.impl.DLStorageQuotaImpl;
import com.liferay.document.library.model.impl.DLStorageQuotaModelImpl;
import com.liferay.document.library.service.persistence.DLStorageQuotaPersistence;
import com.liferay.document.library.service.persistence.impl.constants.DLPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the dl storage quota service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {DLStorageQuotaPersistence.class, BasePersistence.class})
public class DLStorageQuotaPersistenceImpl
	extends BasePersistenceImpl<DLStorageQuota>
	implements DLStorageQuotaPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLStorageQuotaUtil</code> to access the dl storage quota persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLStorageQuotaImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns the dl storage quota where companyId = &#63; or throws a <code>NoSuchStorageQuotaException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @return the matching dl storage quota
	 * @throws NoSuchStorageQuotaException if a matching dl storage quota could not be found
	 */
	@Override
	public DLStorageQuota findByCompanyId(long companyId)
		throws NoSuchStorageQuotaException {

		DLStorageQuota dlStorageQuota = fetchByCompanyId(companyId);

		if (dlStorageQuota == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchStorageQuotaException(sb.toString());
		}

		return dlStorageQuota;
	}

	/**
	 * Returns the dl storage quota where companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @return the matching dl storage quota, or <code>null</code> if a matching dl storage quota could not be found
	 */
	@Override
	public DLStorageQuota fetchByCompanyId(long companyId) {
		return fetchByCompanyId(companyId, true);
	}

	/**
	 * Returns the dl storage quota where companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl storage quota, or <code>null</code> if a matching dl storage quota could not be found
	 */
	@Override
	public DLStorageQuota fetchByCompanyId(
		long companyId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCompanyId, finderArgs);
		}

		if (result instanceof DLStorageQuota) {
			DLStorageQuota dlStorageQuota = (DLStorageQuota)result;

			if (companyId != dlStorageQuota.getCompanyId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_DLSTORAGEQUOTA_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				List<DLStorageQuota> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCompanyId, finderArgs, list);
					}
				}
				else {
					DLStorageQuota dlStorageQuota = list.get(0);

					result = dlStorageQuota;

					cacheResult(dlStorageQuota);
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
			return (DLStorageQuota)result;
		}
	}

	/**
	 * Removes the dl storage quota where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @return the dl storage quota that was removed
	 */
	@Override
	public DLStorageQuota removeByCompanyId(long companyId)
		throws NoSuchStorageQuotaException {

		DLStorageQuota dlStorageQuota = findByCompanyId(companyId);

		return remove(dlStorageQuota);
	}

	/**
	 * Returns the number of dl storage quotas where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching dl storage quotas
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DLSTORAGEQUOTA_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"dlStorageQuota.companyId = ?";

	public DLStorageQuotaPersistenceImpl() {
		setModelClass(DLStorageQuota.class);

		setModelImplClass(DLStorageQuotaImpl.class);
		setModelPKClass(long.class);

		setTable(DLStorageQuotaTable.INSTANCE);
	}

	/**
	 * Caches the dl storage quota in the entity cache if it is enabled.
	 *
	 * @param dlStorageQuota the dl storage quota
	 */
	@Override
	public void cacheResult(DLStorageQuota dlStorageQuota) {
		entityCache.putResult(
			DLStorageQuotaImpl.class, dlStorageQuota.getPrimaryKey(),
			dlStorageQuota);

		finderCache.putResult(
			_finderPathFetchByCompanyId,
			new Object[] {dlStorageQuota.getCompanyId()}, dlStorageQuota);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the dl storage quotas in the entity cache if it is enabled.
	 *
	 * @param dlStorageQuotas the dl storage quotas
	 */
	@Override
	public void cacheResult(List<DLStorageQuota> dlStorageQuotas) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (dlStorageQuotas.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DLStorageQuota dlStorageQuota : dlStorageQuotas) {
			if (entityCache.getResult(
					DLStorageQuotaImpl.class, dlStorageQuota.getPrimaryKey()) ==
						null) {

				cacheResult(dlStorageQuota);
			}
		}
	}

	/**
	 * Clears the cache for all dl storage quotas.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLStorageQuotaImpl.class);

		finderCache.clearCache(DLStorageQuotaImpl.class);
	}

	/**
	 * Clears the cache for the dl storage quota.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLStorageQuota dlStorageQuota) {
		entityCache.removeResult(DLStorageQuotaImpl.class, dlStorageQuota);
	}

	@Override
	public void clearCache(List<DLStorageQuota> dlStorageQuotas) {
		for (DLStorageQuota dlStorageQuota : dlStorageQuotas) {
			entityCache.removeResult(DLStorageQuotaImpl.class, dlStorageQuota);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DLStorageQuotaImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DLStorageQuotaImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DLStorageQuotaModelImpl dlStorageQuotaModelImpl) {

		Object[] args = new Object[] {dlStorageQuotaModelImpl.getCompanyId()};

		finderCache.putResult(
			_finderPathCountByCompanyId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCompanyId, args, dlStorageQuotaModelImpl);
	}

	/**
	 * Creates a new dl storage quota with the primary key. Does not add the dl storage quota to the database.
	 *
	 * @param dlStorageQuotaId the primary key for the new dl storage quota
	 * @return the new dl storage quota
	 */
	@Override
	public DLStorageQuota create(long dlStorageQuotaId) {
		DLStorageQuota dlStorageQuota = new DLStorageQuotaImpl();

		dlStorageQuota.setNew(true);
		dlStorageQuota.setPrimaryKey(dlStorageQuotaId);

		dlStorageQuota.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dlStorageQuota;
	}

	/**
	 * Removes the dl storage quota with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlStorageQuotaId the primary key of the dl storage quota
	 * @return the dl storage quota that was removed
	 * @throws NoSuchStorageQuotaException if a dl storage quota with the primary key could not be found
	 */
	@Override
	public DLStorageQuota remove(long dlStorageQuotaId)
		throws NoSuchStorageQuotaException {

		return remove((Serializable)dlStorageQuotaId);
	}

	/**
	 * Removes the dl storage quota with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dl storage quota
	 * @return the dl storage quota that was removed
	 * @throws NoSuchStorageQuotaException if a dl storage quota with the primary key could not be found
	 */
	@Override
	public DLStorageQuota remove(Serializable primaryKey)
		throws NoSuchStorageQuotaException {

		Session session = null;

		try {
			session = openSession();

			DLStorageQuota dlStorageQuota = (DLStorageQuota)session.get(
				DLStorageQuotaImpl.class, primaryKey);

			if (dlStorageQuota == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStorageQuotaException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dlStorageQuota);
		}
		catch (NoSuchStorageQuotaException noSuchEntityException) {
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
	protected DLStorageQuota removeImpl(DLStorageQuota dlStorageQuota) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlStorageQuota)) {
				dlStorageQuota = (DLStorageQuota)session.get(
					DLStorageQuotaImpl.class,
					dlStorageQuota.getPrimaryKeyObj());
			}

			if (dlStorageQuota != null) {
				session.delete(dlStorageQuota);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dlStorageQuota != null) {
			clearCache(dlStorageQuota);
		}

		return dlStorageQuota;
	}

	@Override
	public DLStorageQuota updateImpl(DLStorageQuota dlStorageQuota) {
		boolean isNew = dlStorageQuota.isNew();

		if (!(dlStorageQuota instanceof DLStorageQuotaModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlStorageQuota.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					dlStorageQuota);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlStorageQuota proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLStorageQuota implementation " +
					dlStorageQuota.getClass());
		}

		DLStorageQuotaModelImpl dlStorageQuotaModelImpl =
			(DLStorageQuotaModelImpl)dlStorageQuota;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(dlStorageQuota);
			}
			else {
				dlStorageQuota = (DLStorageQuota)session.merge(dlStorageQuota);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DLStorageQuotaImpl.class, dlStorageQuotaModelImpl, false, true);

		cacheUniqueFindersCache(dlStorageQuotaModelImpl);

		if (isNew) {
			dlStorageQuota.setNew(false);
		}

		dlStorageQuota.resetOriginalValues();

		return dlStorageQuota;
	}

	/**
	 * Returns the dl storage quota with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dl storage quota
	 * @return the dl storage quota
	 * @throws NoSuchStorageQuotaException if a dl storage quota with the primary key could not be found
	 */
	@Override
	public DLStorageQuota findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStorageQuotaException {

		DLStorageQuota dlStorageQuota = fetchByPrimaryKey(primaryKey);

		if (dlStorageQuota == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStorageQuotaException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dlStorageQuota;
	}

	/**
	 * Returns the dl storage quota with the primary key or throws a <code>NoSuchStorageQuotaException</code> if it could not be found.
	 *
	 * @param dlStorageQuotaId the primary key of the dl storage quota
	 * @return the dl storage quota
	 * @throws NoSuchStorageQuotaException if a dl storage quota with the primary key could not be found
	 */
	@Override
	public DLStorageQuota findByPrimaryKey(long dlStorageQuotaId)
		throws NoSuchStorageQuotaException {

		return findByPrimaryKey((Serializable)dlStorageQuotaId);
	}

	/**
	 * Returns the dl storage quota with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dlStorageQuotaId the primary key of the dl storage quota
	 * @return the dl storage quota, or <code>null</code> if a dl storage quota with the primary key could not be found
	 */
	@Override
	public DLStorageQuota fetchByPrimaryKey(long dlStorageQuotaId) {
		return fetchByPrimaryKey((Serializable)dlStorageQuotaId);
	}

	/**
	 * Returns all the dl storage quotas.
	 *
	 * @return the dl storage quotas
	 */
	@Override
	public List<DLStorageQuota> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl storage quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLStorageQuotaModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl storage quotas
	 * @param end the upper bound of the range of dl storage quotas (not inclusive)
	 * @return the range of dl storage quotas
	 */
	@Override
	public List<DLStorageQuota> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl storage quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLStorageQuotaModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl storage quotas
	 * @param end the upper bound of the range of dl storage quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl storage quotas
	 */
	@Override
	public List<DLStorageQuota> findAll(
		int start, int end,
		OrderByComparator<DLStorageQuota> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dl storage quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLStorageQuotaModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl storage quotas
	 * @param end the upper bound of the range of dl storage quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dl storage quotas
	 */
	@Override
	public List<DLStorageQuota> findAll(
		int start, int end, OrderByComparator<DLStorageQuota> orderByComparator,
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

		List<DLStorageQuota> list = null;

		if (useFinderCache) {
			list = (List<DLStorageQuota>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DLSTORAGEQUOTA);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DLSTORAGEQUOTA;

				sql = sql.concat(DLStorageQuotaModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DLStorageQuota>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
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
	 * Removes all the dl storage quotas from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLStorageQuota dlStorageQuota : findAll()) {
			remove(dlStorageQuota);
		}
	}

	/**
	 * Returns the number of dl storage quotas.
	 *
	 * @return the number of dl storage quotas
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_DLSTORAGEQUOTA);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
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
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "dlStorageQuotaId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLSTORAGEQUOTA;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DLStorageQuotaModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dl storage quota persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DLStorageQuotaImpl.class.getName());
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DLSTORAGEQUOTA =
		"SELECT dlStorageQuota FROM DLStorageQuota dlStorageQuota";

	private static final String _SQL_SELECT_DLSTORAGEQUOTA_WHERE =
		"SELECT dlStorageQuota FROM DLStorageQuota dlStorageQuota WHERE ";

	private static final String _SQL_COUNT_DLSTORAGEQUOTA =
		"SELECT COUNT(dlStorageQuota) FROM DLStorageQuota dlStorageQuota";

	private static final String _SQL_COUNT_DLSTORAGEQUOTA_WHERE =
		"SELECT COUNT(dlStorageQuota) FROM DLStorageQuota dlStorageQuota WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dlStorageQuota.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DLStorageQuota exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLStorageQuota exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLStorageQuotaPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private DLStorageQuotaModelArgumentsResolver
		_dlStorageQuotaModelArgumentsResolver;

}