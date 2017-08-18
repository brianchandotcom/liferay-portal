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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.impl.CommerceOrderImpl;
import com.liferay.commerce.model.impl.CommerceOrderModelImpl;
import com.liferay.commerce.service.persistence.CommerceOrderPersistence;

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
 * The persistence implementation for the commerce order service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPersistence
 * @see com.liferay.commerce.service.persistence.CommerceOrderUtil
 * @generated
 */
@ProviderType
public class CommerceOrderPersistenceImpl extends BasePersistenceImpl<CommerceOrder>
	implements CommerceOrderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceOrderUtil} to access the commerce order persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceOrderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public CommerceOrderPersistenceImpl() {
		setModelClass(CommerceOrder.class);
	}

	/**
	 * Caches the commerce order in the entity cache if it is enabled.
	 *
	 * @param commerceOrder the commerce order
	 */
	@Override
	public void cacheResult(CommerceOrder commerceOrder) {
		entityCache.putResult(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderImpl.class, commerceOrder.getPrimaryKey(),
			commerceOrder);

		commerceOrder.resetOriginalValues();
	}

	/**
	 * Caches the commerce orders in the entity cache if it is enabled.
	 *
	 * @param commerceOrders the commerce orders
	 */
	@Override
	public void cacheResult(List<CommerceOrder> commerceOrders) {
		for (CommerceOrder commerceOrder : commerceOrders) {
			if (entityCache.getResult(
						CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
						CommerceOrderImpl.class, commerceOrder.getPrimaryKey()) == null) {
				cacheResult(commerceOrder);
			}
			else {
				commerceOrder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce orders.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceOrderImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce order.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceOrder commerceOrder) {
		entityCache.removeResult(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderImpl.class, commerceOrder.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceOrder> commerceOrders) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceOrder commerceOrder : commerceOrders) {
			entityCache.removeResult(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
				CommerceOrderImpl.class, commerceOrder.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce order with the primary key. Does not add the commerce order to the database.
	 *
	 * @param commerceOrderId the primary key for the new commerce order
	 * @return the new commerce order
	 */
	@Override
	public CommerceOrder create(long commerceOrderId) {
		CommerceOrder commerceOrder = new CommerceOrderImpl();

		commerceOrder.setNew(true);
		commerceOrder.setPrimaryKey(commerceOrderId);

		commerceOrder.setCompanyId(companyProvider.getCompanyId());

		return commerceOrder;
	}

	/**
	 * Removes the commerce order with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderId the primary key of the commerce order
	 * @return the commerce order that was removed
	 * @throws NoSuchOrderException if a commerce order with the primary key could not be found
	 */
	@Override
	public CommerceOrder remove(long commerceOrderId)
		throws NoSuchOrderException {
		return remove((Serializable)commerceOrderId);
	}

	/**
	 * Removes the commerce order with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce order
	 * @return the commerce order that was removed
	 * @throws NoSuchOrderException if a commerce order with the primary key could not be found
	 */
	@Override
	public CommerceOrder remove(Serializable primaryKey)
		throws NoSuchOrderException {
		Session session = null;

		try {
			session = openSession();

			CommerceOrder commerceOrder = (CommerceOrder)session.get(CommerceOrderImpl.class,
					primaryKey);

			if (commerceOrder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceOrder);
		}
		catch (NoSuchOrderException nsee) {
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
	protected CommerceOrder removeImpl(CommerceOrder commerceOrder) {
		commerceOrder = toUnwrappedModel(commerceOrder);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceOrder)) {
				commerceOrder = (CommerceOrder)session.get(CommerceOrderImpl.class,
						commerceOrder.getPrimaryKeyObj());
			}

			if (commerceOrder != null) {
				session.delete(commerceOrder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceOrder != null) {
			clearCache(commerceOrder);
		}

		return commerceOrder;
	}

	@Override
	public CommerceOrder updateImpl(CommerceOrder commerceOrder) {
		commerceOrder = toUnwrappedModel(commerceOrder);

		boolean isNew = commerceOrder.isNew();

		CommerceOrderModelImpl commerceOrderModelImpl = (CommerceOrderModelImpl)commerceOrder;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceOrder.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceOrder.setCreateDate(now);
			}
			else {
				commerceOrder.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceOrderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceOrder.setModifiedDate(now);
			}
			else {
				commerceOrder.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceOrder.isNew()) {
				session.save(commerceOrder);

				commerceOrder.setNew(false);
			}
			else {
				commerceOrder = (CommerceOrder)session.merge(commerceOrder);
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

		entityCache.putResult(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderImpl.class, commerceOrder.getPrimaryKey(),
			commerceOrder, false);

		commerceOrder.resetOriginalValues();

		return commerceOrder;
	}

	protected CommerceOrder toUnwrappedModel(CommerceOrder commerceOrder) {
		if (commerceOrder instanceof CommerceOrderImpl) {
			return commerceOrder;
		}

		CommerceOrderImpl commerceOrderImpl = new CommerceOrderImpl();

		commerceOrderImpl.setNew(commerceOrder.isNew());
		commerceOrderImpl.setPrimaryKey(commerceOrder.getPrimaryKey());

		commerceOrderImpl.setCommerceOrderId(commerceOrder.getCommerceOrderId());
		commerceOrderImpl.setGroupId(commerceOrder.getGroupId());
		commerceOrderImpl.setCompanyId(commerceOrder.getCompanyId());
		commerceOrderImpl.setUserId(commerceOrder.getUserId());
		commerceOrderImpl.setUserName(commerceOrder.getUserName());
		commerceOrderImpl.setCreateDate(commerceOrder.getCreateDate());
		commerceOrderImpl.setModifiedDate(commerceOrder.getModifiedDate());
		commerceOrderImpl.setOrderUserId(commerceOrder.getOrderUserId());
		commerceOrderImpl.setStatus(commerceOrder.getStatus());

		return commerceOrderImpl;
	}

	/**
	 * Returns the commerce order with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order
	 * @return the commerce order
	 * @throws NoSuchOrderException if a commerce order with the primary key could not be found
	 */
	@Override
	public CommerceOrder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrderException {
		CommerceOrder commerceOrder = fetchByPrimaryKey(primaryKey);

		if (commerceOrder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceOrder;
	}

	/**
	 * Returns the commerce order with the primary key or throws a {@link NoSuchOrderException} if it could not be found.
	 *
	 * @param commerceOrderId the primary key of the commerce order
	 * @return the commerce order
	 * @throws NoSuchOrderException if a commerce order with the primary key could not be found
	 */
	@Override
	public CommerceOrder findByPrimaryKey(long commerceOrderId)
		throws NoSuchOrderException {
		return findByPrimaryKey((Serializable)commerceOrderId);
	}

	/**
	 * Returns the commerce order with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order
	 * @return the commerce order, or <code>null</code> if a commerce order with the primary key could not be found
	 */
	@Override
	public CommerceOrder fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
				CommerceOrderImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceOrder commerceOrder = (CommerceOrder)serializable;

		if (commerceOrder == null) {
			Session session = null;

			try {
				session = openSession();

				commerceOrder = (CommerceOrder)session.get(CommerceOrderImpl.class,
						primaryKey);

				if (commerceOrder != null) {
					cacheResult(commerceOrder);
				}
				else {
					entityCache.putResult(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
						CommerceOrderImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
					CommerceOrderImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceOrder;
	}

	/**
	 * Returns the commerce order with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderId the primary key of the commerce order
	 * @return the commerce order, or <code>null</code> if a commerce order with the primary key could not be found
	 */
	@Override
	public CommerceOrder fetchByPrimaryKey(long commerceOrderId) {
		return fetchByPrimaryKey((Serializable)commerceOrderId);
	}

	@Override
	public Map<Serializable, CommerceOrder> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceOrder> map = new HashMap<Serializable, CommerceOrder>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceOrder commerceOrder = fetchByPrimaryKey(primaryKey);

			if (commerceOrder != null) {
				map.put(primaryKey, commerceOrder);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
					CommerceOrderImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceOrder)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEORDER_WHERE_PKS_IN);

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

			for (CommerceOrder commerceOrder : (List<CommerceOrder>)q.list()) {
				map.put(commerceOrder.getPrimaryKeyObj(), commerceOrder);

				cacheResult(commerceOrder);

				uncachedPrimaryKeys.remove(commerceOrder.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceOrderModelImpl.ENTITY_CACHE_ENABLED,
					CommerceOrderImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce orders.
	 *
	 * @return the commerce orders
	 */
	@Override
	public List<CommerceOrder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce orders
	 * @param end the upper bound of the range of commerce orders (not inclusive)
	 * @return the range of commerce orders
	 */
	@Override
	public List<CommerceOrder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce orders
	 * @param end the upper bound of the range of commerce orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce orders
	 */
	@Override
	public List<CommerceOrder> findAll(int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce orders
	 * @param end the upper bound of the range of commerce orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce orders
	 */
	@Override
	public List<CommerceOrder> findAll(int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator,
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

		List<CommerceOrder> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceOrder>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEORDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEORDER;

				if (pagination) {
					sql = sql.concat(CommerceOrderModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceOrder>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceOrder>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the commerce orders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceOrder commerceOrder : findAll()) {
			remove(commerceOrder);
		}
	}

	/**
	 * Returns the number of commerce orders.
	 *
	 * @return the number of commerce orders
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEORDER);

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
		return CommerceOrderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce order persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceOrderImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEORDER = "SELECT commerceOrder FROM CommerceOrder commerceOrder";
	private static final String _SQL_SELECT_COMMERCEORDER_WHERE_PKS_IN = "SELECT commerceOrder FROM CommerceOrder commerceOrder WHERE commerceOrderId IN (";
	private static final String _SQL_COUNT_COMMERCEORDER = "SELECT COUNT(commerceOrder) FROM CommerceOrder commerceOrder";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceOrder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceOrder exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(CommerceOrderPersistenceImpl.class);
}