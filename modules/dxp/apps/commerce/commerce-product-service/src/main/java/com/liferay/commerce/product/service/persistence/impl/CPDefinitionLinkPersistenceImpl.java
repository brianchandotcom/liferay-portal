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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.impl.CPDefinitionLinkImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionLinkPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the cp definition link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionLinkPersistence
 * @see com.liferay.commerce.product.service.persistence.CPDefinitionLinkUtil
 * @generated
 */
@ProviderType
public class CPDefinitionLinkPersistenceImpl extends BasePersistenceImpl<CPDefinitionLink>
	implements CPDefinitionLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDefinitionLinkUtil} to access the cp definition link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDefinitionLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public CPDefinitionLinkPersistenceImpl() {
		setModelClass(CPDefinitionLink.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the cp definition link in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionLink the cp definition link
	 */
	@Override
	public void cacheResult(CPDefinitionLink cpDefinitionLink) {
		entityCache.putResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, cpDefinitionLink.getPrimaryKey(),
			cpDefinitionLink);

		cpDefinitionLink.resetOriginalValues();
	}

	/**
	 * Caches the cp definition links in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionLinks the cp definition links
	 */
	@Override
	public void cacheResult(List<CPDefinitionLink> cpDefinitionLinks) {
		for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
			if (entityCache.getResult(
						CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionLinkImpl.class,
						cpDefinitionLink.getPrimaryKey()) == null) {
				cacheResult(cpDefinitionLink);
			}
			else {
				cpDefinitionLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp definition links.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp definition link.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionLink cpDefinitionLink) {
		entityCache.removeResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, cpDefinitionLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CPDefinitionLink> cpDefinitionLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
			entityCache.removeResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionLinkImpl.class, cpDefinitionLink.getPrimaryKey());
		}
	}

	/**
	 * Creates a new cp definition link with the primary key. Does not add the cp definition link to the database.
	 *
	 * @param CPDefinitionLinkId the primary key for the new cp definition link
	 * @return the new cp definition link
	 */
	@Override
	public CPDefinitionLink create(long CPDefinitionLinkId) {
		CPDefinitionLink cpDefinitionLink = new CPDefinitionLinkImpl();

		cpDefinitionLink.setNew(true);
		cpDefinitionLink.setPrimaryKey(CPDefinitionLinkId);

		cpDefinitionLink.setCompanyId(companyProvider.getCompanyId());

		return cpDefinitionLink;
	}

	/**
	 * Removes the cp definition link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionLinkId the primary key of the cp definition link
	 * @return the cp definition link that was removed
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink remove(long CPDefinitionLinkId)
		throws NoSuchCPDefinitionLinkException {
		return remove((Serializable)CPDefinitionLinkId);
	}

	/**
	 * Removes the cp definition link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition link
	 * @return the cp definition link that was removed
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink remove(Serializable primaryKey)
		throws NoSuchCPDefinitionLinkException {
		Session session = null;

		try {
			session = openSession();

			CPDefinitionLink cpDefinitionLink = (CPDefinitionLink)session.get(CPDefinitionLinkImpl.class,
					primaryKey);

			if (cpDefinitionLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDefinitionLink);
		}
		catch (NoSuchCPDefinitionLinkException nsee) {
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
	protected CPDefinitionLink removeImpl(CPDefinitionLink cpDefinitionLink) {
		cpDefinitionLink = toUnwrappedModel(cpDefinitionLink);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionLink)) {
				cpDefinitionLink = (CPDefinitionLink)session.get(CPDefinitionLinkImpl.class,
						cpDefinitionLink.getPrimaryKeyObj());
			}

			if (cpDefinitionLink != null) {
				session.delete(cpDefinitionLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionLink != null) {
			clearCache(cpDefinitionLink);
		}

		return cpDefinitionLink;
	}

	@Override
	public CPDefinitionLink updateImpl(CPDefinitionLink cpDefinitionLink) {
		cpDefinitionLink = toUnwrappedModel(cpDefinitionLink);

		boolean isNew = cpDefinitionLink.isNew();

		Session session = null;

		try {
			session = openSession();

			if (cpDefinitionLink.isNew()) {
				session.save(cpDefinitionLink);

				cpDefinitionLink.setNew(false);
			}
			else {
				cpDefinitionLink = (CPDefinitionLink)session.merge(cpDefinitionLink);
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

		entityCache.putResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, cpDefinitionLink.getPrimaryKey(),
			cpDefinitionLink, false);

		cpDefinitionLink.resetOriginalValues();

		return cpDefinitionLink;
	}

	protected CPDefinitionLink toUnwrappedModel(
		CPDefinitionLink cpDefinitionLink) {
		if (cpDefinitionLink instanceof CPDefinitionLinkImpl) {
			return cpDefinitionLink;
		}

		CPDefinitionLinkImpl cpDefinitionLinkImpl = new CPDefinitionLinkImpl();

		cpDefinitionLinkImpl.setNew(cpDefinitionLink.isNew());
		cpDefinitionLinkImpl.setPrimaryKey(cpDefinitionLink.getPrimaryKey());

		cpDefinitionLinkImpl.setCPDefinitionLinkId(cpDefinitionLink.getCPDefinitionLinkId());
		cpDefinitionLinkImpl.setCompanyId(cpDefinitionLink.getCompanyId());
		cpDefinitionLinkImpl.setUserId(cpDefinitionLink.getUserId());
		cpDefinitionLinkImpl.setUserName(cpDefinitionLink.getUserName());
		cpDefinitionLinkImpl.setCreateDate(cpDefinitionLink.getCreateDate());
		cpDefinitionLinkImpl.setCPDefinitionId1(cpDefinitionLink.getCPDefinitionId1());
		cpDefinitionLinkImpl.setCPDefinitionId2(cpDefinitionLink.getCPDefinitionId2());
		cpDefinitionLinkImpl.setDisplayOrder(cpDefinitionLink.getDisplayOrder());
		cpDefinitionLinkImpl.setType(cpDefinitionLink.getType());

		return cpDefinitionLinkImpl;
	}

	/**
	 * Returns the cp definition link with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition link
	 * @return the cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = fetchByPrimaryKey(primaryKey);

		if (cpDefinitionLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDefinitionLink;
	}

	/**
	 * Returns the cp definition link with the primary key or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	 *
	 * @param CPDefinitionLinkId the primary key of the cp definition link
	 * @return the cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink findByPrimaryKey(long CPDefinitionLinkId)
		throws NoSuchCPDefinitionLinkException {
		return findByPrimaryKey((Serializable)CPDefinitionLinkId);
	}

	/**
	 * Returns the cp definition link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition link
	 * @return the cp definition link, or <code>null</code> if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionLinkImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDefinitionLink cpDefinitionLink = (CPDefinitionLink)serializable;

		if (cpDefinitionLink == null) {
			Session session = null;

			try {
				session = openSession();

				cpDefinitionLink = (CPDefinitionLink)session.get(CPDefinitionLinkImpl.class,
						primaryKey);

				if (cpDefinitionLink != null) {
					cacheResult(cpDefinitionLink);
				}
				else {
					entityCache.putResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionLinkImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionLinkImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDefinitionLink;
	}

	/**
	 * Returns the cp definition link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionLinkId the primary key of the cp definition link
	 * @return the cp definition link, or <code>null</code> if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink fetchByPrimaryKey(long CPDefinitionLinkId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionLinkId);
	}

	@Override
	public Map<Serializable, CPDefinitionLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionLink> map = new HashMap<Serializable, CPDefinitionLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionLink cpDefinitionLink = fetchByPrimaryKey(primaryKey);

			if (cpDefinitionLink != null) {
				map.put(primaryKey, cpDefinitionLink);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionLinkImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPDefinitionLink)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDEFINITIONLINK_WHERE_PKS_IN);

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

			for (CPDefinitionLink cpDefinitionLink : (List<CPDefinitionLink>)q.list()) {
				map.put(cpDefinitionLink.getPrimaryKeyObj(), cpDefinitionLink);

				cacheResult(cpDefinitionLink);

				uncachedPrimaryKeys.remove(cpDefinitionLink.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionLinkImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp definition links.
	 *
	 * @return the cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @return the range of cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findAll(int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findAll(int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
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

		List<CPDefinitionLink> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionLink>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDEFINITIONLINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONLINK;

				if (pagination) {
					sql = sql.concat(CPDefinitionLinkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDefinitionLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionLink>)QueryUtil.list(q,
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
	 * Removes all the cp definition links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionLink cpDefinitionLink : findAll()) {
			remove(cpDefinitionLink);
		}
	}

	/**
	 * Returns the number of cp definition links.
	 *
	 * @return the number of cp definition links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDEFINITIONLINK);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition link persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionLinkImpl.class.getName());
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
	private static final String _SQL_SELECT_CPDEFINITIONLINK = "SELECT cpDefinitionLink FROM CPDefinitionLink cpDefinitionLink";
	private static final String _SQL_SELECT_CPDEFINITIONLINK_WHERE_PKS_IN = "SELECT cpDefinitionLink FROM CPDefinitionLink cpDefinitionLink WHERE CPDefinitionLinkId IN (";
	private static final String _SQL_COUNT_CPDEFINITIONLINK = "SELECT COUNT(cpDefinitionLink) FROM CPDefinitionLink cpDefinitionLink";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinitionLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDefinitionLink exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(CPDefinitionLinkPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}