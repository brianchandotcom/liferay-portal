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

package com.liferay.commerce.address.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.address.exception.NoSuchCountryException;
import com.liferay.commerce.address.model.CommerceCountry;
import com.liferay.commerce.address.model.impl.CommerceCountryImpl;
import com.liferay.commerce.address.model.impl.CommerceCountryModelImpl;
import com.liferay.commerce.address.service.persistence.CommerceCountryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryPersistence
 * @see com.liferay.commerce.address.service.persistence.CommerceCountryUtil
 * @generated
 */
@ProviderType
public class CommerceCountryPersistenceImpl extends BasePersistenceImpl<CommerceCountry>
	implements CommerceCountryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceCountryUtil} to access the commerce country persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceCountryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCountryModelImpl.FINDER_CACHE_ENABLED,
			CommerceCountryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCountryModelImpl.FINDER_CACHE_ENABLED,
			CommerceCountryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCountryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public CommerceCountryPersistenceImpl() {
		setModelClass(CommerceCountry.class);
	}

	/**
	 * Caches the commerce country in the entity cache if it is enabled.
	 *
	 * @param commerceCountry the commerce country
	 */
	@Override
	public void cacheResult(CommerceCountry commerceCountry) {
		entityCache.putResult(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCountryImpl.class, commerceCountry.getPrimaryKey(),
			commerceCountry);

		commerceCountry.resetOriginalValues();
	}

	/**
	 * Caches the commerce countries in the entity cache if it is enabled.
	 *
	 * @param commerceCountries the commerce countries
	 */
	@Override
	public void cacheResult(List<CommerceCountry> commerceCountries) {
		for (CommerceCountry commerceCountry : commerceCountries) {
			if (entityCache.getResult(
						CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCountryImpl.class,
						commerceCountry.getPrimaryKey()) == null) {
				cacheResult(commerceCountry);
			}
			else {
				commerceCountry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce countries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceCountryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce country.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceCountry commerceCountry) {
		entityCache.removeResult(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCountryImpl.class, commerceCountry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceCountry> commerceCountries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceCountry commerceCountry : commerceCountries) {
			entityCache.removeResult(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCountryImpl.class, commerceCountry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce country with the primary key. Does not add the commerce country to the database.
	 *
	 * @param commerceCountryId the primary key for the new commerce country
	 * @return the new commerce country
	 */
	@Override
	public CommerceCountry create(long commerceCountryId) {
		CommerceCountry commerceCountry = new CommerceCountryImpl();

		commerceCountry.setNew(true);
		commerceCountry.setPrimaryKey(commerceCountryId);

		commerceCountry.setCompanyId(companyProvider.getCompanyId());

		return commerceCountry;
	}

	/**
	 * Removes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country that was removed
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry remove(long commerceCountryId)
		throws NoSuchCountryException {
		return remove((Serializable)commerceCountryId);
	}

	/**
	 * Removes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce country
	 * @return the commerce country that was removed
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry remove(Serializable primaryKey)
		throws NoSuchCountryException {
		Session session = null;

		try {
			session = openSession();

			CommerceCountry commerceCountry = (CommerceCountry)session.get(CommerceCountryImpl.class,
					primaryKey);

			if (commerceCountry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCountryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceCountry);
		}
		catch (NoSuchCountryException nsee) {
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
	protected CommerceCountry removeImpl(CommerceCountry commerceCountry) {
		commerceCountry = toUnwrappedModel(commerceCountry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceCountry)) {
				commerceCountry = (CommerceCountry)session.get(CommerceCountryImpl.class,
						commerceCountry.getPrimaryKeyObj());
			}

			if (commerceCountry != null) {
				session.delete(commerceCountry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceCountry != null) {
			clearCache(commerceCountry);
		}

		return commerceCountry;
	}

	@Override
	public CommerceCountry updateImpl(CommerceCountry commerceCountry) {
		commerceCountry = toUnwrappedModel(commerceCountry);

		boolean isNew = commerceCountry.isNew();

		CommerceCountryModelImpl commerceCountryModelImpl = (CommerceCountryModelImpl)commerceCountry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceCountry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceCountry.setCreateDate(now);
			}
			else {
				commerceCountry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceCountryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceCountry.setModifiedDate(now);
			}
			else {
				commerceCountry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceCountry.isNew()) {
				session.save(commerceCountry);

				commerceCountry.setNew(false);
			}
			else {
				commerceCountry = (CommerceCountry)session.merge(commerceCountry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCountryImpl.class, commerceCountry.getPrimaryKey(),
			commerceCountry, false);

		commerceCountry.resetOriginalValues();

		return commerceCountry;
	}

	protected CommerceCountry toUnwrappedModel(CommerceCountry commerceCountry) {
		if (commerceCountry instanceof CommerceCountryImpl) {
			return commerceCountry;
		}

		CommerceCountryImpl commerceCountryImpl = new CommerceCountryImpl();

		commerceCountryImpl.setNew(commerceCountry.isNew());
		commerceCountryImpl.setPrimaryKey(commerceCountry.getPrimaryKey());

		commerceCountryImpl.setCommerceCountryId(commerceCountry.getCommerceCountryId());
		commerceCountryImpl.setGroupId(commerceCountry.getGroupId());
		commerceCountryImpl.setCompanyId(commerceCountry.getCompanyId());
		commerceCountryImpl.setUserId(commerceCountry.getUserId());
		commerceCountryImpl.setUserName(commerceCountry.getUserName());
		commerceCountryImpl.setCreateDate(commerceCountry.getCreateDate());
		commerceCountryImpl.setModifiedDate(commerceCountry.getModifiedDate());
		commerceCountryImpl.setName(commerceCountry.getName());
		commerceCountryImpl.setAllowsBilling(commerceCountry.isAllowsBilling());
		commerceCountryImpl.setAllowsShipping(commerceCountry.isAllowsShipping());
		commerceCountryImpl.setTwoLettersISOCode(commerceCountry.getTwoLettersISOCode());
		commerceCountryImpl.setThreeLettersISOCode(commerceCountry.getThreeLettersISOCode());
		commerceCountryImpl.setNumericISOCode(commerceCountry.getNumericISOCode());
		commerceCountryImpl.setPriority(commerceCountry.getPriority());
		commerceCountryImpl.setPublished(commerceCountry.isPublished());

		return commerceCountryImpl;
	}

	/**
	 * Returns the commerce country with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce country
	 * @return the commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCountryException {
		CommerceCountry commerceCountry = fetchByPrimaryKey(primaryKey);

		if (commerceCountry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCountryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceCountry;
	}

	/**
	 * Returns the commerce country with the primary key or throws a {@link NoSuchCountryException} if it could not be found.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry findByPrimaryKey(long commerceCountryId)
		throws NoSuchCountryException {
		return findByPrimaryKey((Serializable)commerceCountryId);
	}

	/**
	 * Returns the commerce country with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce country
	 * @return the commerce country, or <code>null</code> if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCountryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceCountry commerceCountry = (CommerceCountry)serializable;

		if (commerceCountry == null) {
			Session session = null;

			try {
				session = openSession();

				commerceCountry = (CommerceCountry)session.get(CommerceCountryImpl.class,
						primaryKey);

				if (commerceCountry != null) {
					cacheResult(commerceCountry);
				}
				else {
					entityCache.putResult(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCountryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCountryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceCountry;
	}

	/**
	 * Returns the commerce country with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country, or <code>null</code> if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry fetchByPrimaryKey(long commerceCountryId) {
		return fetchByPrimaryKey((Serializable)commerceCountryId);
	}

	@Override
	public Map<Serializable, CommerceCountry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceCountry> map = new HashMap<Serializable, CommerceCountry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceCountry commerceCountry = fetchByPrimaryKey(primaryKey);

			if (commerceCountry != null) {
				map.put(primaryKey, commerceCountry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCountryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceCountry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommerceCountry commerceCountry : (List<CommerceCountry>)q.list()) {
				map.put(commerceCountry.getPrimaryKeyObj(), commerceCountry);

				cacheResult(commerceCountry);

				uncachedPrimaryKeys.remove(commerceCountry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceCountryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCountryImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce countries.
	 *
	 * @return the commerce countries
	 */
	@Override
	public List<CommerceCountry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of commerce countries
	 */
	@Override
	public List<CommerceCountry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce countries
	 */
	@Override
	public List<CommerceCountry> findAll(int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce countries
	 */
	@Override
	public List<CommerceCountry> findAll(int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		List<CommerceCountry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCountry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCECOUNTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCECOUNTRY;

				if (pagination) {
					sql = sql.concat(CommerceCountryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceCountry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCountry>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the commerce countries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceCountry commerceCountry : findAll()) {
			remove(commerceCountry);
		}
	}

	/**
	 * Returns the number of commerce countries.
	 *
	 * @return the number of commerce countries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCECOUNTRY);

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
		return CommerceCountryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce country persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceCountryImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCECOUNTRY = "SELECT commerceCountry FROM CommerceCountry commerceCountry";
	private static final String _SQL_SELECT_COMMERCECOUNTRY_WHERE_PKS_IN = "SELECT commerceCountry FROM CommerceCountry commerceCountry WHERE commerceCountryId IN (";
	private static final String _SQL_COUNT_COMMERCECOUNTRY = "SELECT COUNT(commerceCountry) FROM CommerceCountry commerceCountry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceCountry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceCountry exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(CommerceCountryPersistenceImpl.class);
}