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

package com.liferay.commerce.discount.service.persistence.impl;

import com.liferay.commerce.discount.exception.NoSuchDiscountUsageEntryException;
import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUsageEntryPersistence;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce discount usage entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountUsageEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceDiscountUsageEntry>
	implements CommerceDiscountUsageEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceDiscountUsageEntryUtil</code> to access the commerce discount usage entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceDiscountUsageEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public CommerceDiscountUsageEntryPersistenceImpl() {
		setModelClass(CommerceDiscountUsageEntry.class);
	}

	/**
	 * Caches the commerce discount usage entry in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUsageEntry the commerce discount usage entry
	 */
	@Override
	public void cacheResult(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		entityCache.putResult(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey(),
			commerceDiscountUsageEntry);

		commerceDiscountUsageEntry.resetOriginalValues();
	}

	/**
	 * Caches the commerce discount usage entries in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUsageEntries the commerce discount usage entries
	 */
	@Override
	public void cacheResult(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				commerceDiscountUsageEntries) {

			if (entityCache.getResult(
					CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUsageEntryImpl.class,
					commerceDiscountUsageEntry.getPrimaryKey()) == null) {

				cacheResult(commerceDiscountUsageEntry);
			}
			else {
				commerceDiscountUsageEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce discount usage entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDiscountUsageEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce discount usage entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		entityCache.removeResult(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				commerceDiscountUsageEntries) {

			entityCache.removeResult(
				CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUsageEntryImpl.class,
				commerceDiscountUsageEntry.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUsageEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce discount usage entry with the primary key. Does not add the commerce discount usage entry to the database.
	 *
	 * @param commerceDiscountUsageEntryId the primary key for the new commerce discount usage entry
	 * @return the new commerce discount usage entry
	 */
	@Override
	public CommerceDiscountUsageEntry create(
		long commerceDiscountUsageEntryId) {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			new CommerceDiscountUsageEntryImpl();

		commerceDiscountUsageEntry.setNew(true);
		commerceDiscountUsageEntry.setPrimaryKey(commerceDiscountUsageEntryId);

		commerceDiscountUsageEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceDiscountUsageEntry;
	}

	/**
	 * Removes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry that was removed
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry remove(long commerceDiscountUsageEntryId)
		throws NoSuchDiscountUsageEntryException {

		return remove((Serializable)commerceDiscountUsageEntryId);
	}

	/**
	 * Removes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry that was removed
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry remove(Serializable primaryKey)
		throws NoSuchDiscountUsageEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUsageEntry commerceDiscountUsageEntry =
				(CommerceDiscountUsageEntry)session.get(
					CommerceDiscountUsageEntryImpl.class, primaryKey);

			if (commerceDiscountUsageEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscountUsageEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceDiscountUsageEntry);
		}
		catch (NoSuchDiscountUsageEntryException noSuchEntityException) {
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
	protected CommerceDiscountUsageEntry removeImpl(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscountUsageEntry)) {
				commerceDiscountUsageEntry =
					(CommerceDiscountUsageEntry)session.get(
						CommerceDiscountUsageEntryImpl.class,
						commerceDiscountUsageEntry.getPrimaryKeyObj());
			}

			if (commerceDiscountUsageEntry != null) {
				session.delete(commerceDiscountUsageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceDiscountUsageEntry != null) {
			clearCache(commerceDiscountUsageEntry);
		}

		return commerceDiscountUsageEntry;
	}

	@Override
	public CommerceDiscountUsageEntry updateImpl(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		boolean isNew = commerceDiscountUsageEntry.isNew();

		if (!(commerceDiscountUsageEntry instanceof
				CommerceDiscountUsageEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceDiscountUsageEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceDiscountUsageEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscountUsageEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscountUsageEntry implementation " +
					commerceDiscountUsageEntry.getClass());
		}

		CommerceDiscountUsageEntryModelImpl
			commerceDiscountUsageEntryModelImpl =
				(CommerceDiscountUsageEntryModelImpl)commerceDiscountUsageEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceDiscountUsageEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscountUsageEntry.setCreateDate(now);
			}
			else {
				commerceDiscountUsageEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceDiscountUsageEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscountUsageEntry.setModifiedDate(now);
			}
			else {
				commerceDiscountUsageEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceDiscountUsageEntry.isNew()) {
				session.save(commerceDiscountUsageEntry);

				commerceDiscountUsageEntry.setNew(false);
			}
			else {
				commerceDiscountUsageEntry =
					(CommerceDiscountUsageEntry)session.merge(
						commerceDiscountUsageEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey(),
			commerceDiscountUsageEntry, false);

		commerceDiscountUsageEntry.resetOriginalValues();

		return commerceDiscountUsageEntry;
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDiscountUsageEntryException {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			fetchByPrimaryKey(primaryKey);

		if (commerceDiscountUsageEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscountUsageEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceDiscountUsageEntry;
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or throws a <code>NoSuchDiscountUsageEntryException</code> if it could not be found.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByPrimaryKey(
			long commerceDiscountUsageEntryId)
		throws NoSuchDiscountUsageEntryException {

		return findByPrimaryKey((Serializable)commerceDiscountUsageEntryId);
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			(CommerceDiscountUsageEntry)serializable;

		if (commerceDiscountUsageEntry == null) {
			Session session = null;

			try {
				session = openSession();

				commerceDiscountUsageEntry =
					(CommerceDiscountUsageEntry)session.get(
						CommerceDiscountUsageEntryImpl.class, primaryKey);

				if (commerceDiscountUsageEntry != null) {
					cacheResult(commerceDiscountUsageEntry);
				}
				else {
					entityCache.putResult(
						CommerceDiscountUsageEntryModelImpl.
							ENTITY_CACHE_ENABLED,
						CommerceDiscountUsageEntryImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUsageEntryImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceDiscountUsageEntry;
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByPrimaryKey(
		long commerceDiscountUsageEntryId) {

		return fetchByPrimaryKey((Serializable)commerceDiscountUsageEntryId);
	}

	@Override
	public Map<Serializable, CommerceDiscountUsageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceDiscountUsageEntry> map =
			new HashMap<Serializable, CommerceDiscountUsageEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceDiscountUsageEntry commerceDiscountUsageEntry =
				fetchByPrimaryKey(primaryKey);

			if (commerceDiscountUsageEntry != null) {
				map.put(primaryKey, commerceDiscountUsageEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUsageEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey, (CommerceDiscountUsageEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
					(List<CommerceDiscountUsageEntry>)query.list()) {

				map.put(
					commerceDiscountUsageEntry.getPrimaryKeyObj(),
					commerceDiscountUsageEntry);

				cacheResult(commerceDiscountUsageEntry);

				uncachedPrimaryKeys.remove(
					commerceDiscountUsageEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUsageEntryImpl.class, primaryKey,
					nullModel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the commerce discount usage entries.
	 *
	 * @return the commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
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

		List<CommerceDiscountUsageEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY;

				sql = sql.concat(
					CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the commerce discount usage entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry :
				findAll()) {

			remove(commerceDiscountUsageEntry);
		}
	}

	/**
	 * Returns the number of commerce discount usage entries.
	 *
	 * @return the number of commerce discount usage entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceDiscountUsageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount usage entry persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	}

	public void destroy() {
		entityCache.removeCache(CommerceDiscountUsageEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY =
		"SELECT commerceDiscountUsageEntry FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry";

	private static final String
		_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE_PKS_IN =
			"SELECT commerceDiscountUsageEntry FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry WHERE commerceDiscountUsageEntryId IN (";

	private static final String _SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY =
		"SELECT COUNT(commerceDiscountUsageEntry) FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceDiscountUsageEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceDiscountUsageEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountUsageEntryPersistenceImpl.class);

}