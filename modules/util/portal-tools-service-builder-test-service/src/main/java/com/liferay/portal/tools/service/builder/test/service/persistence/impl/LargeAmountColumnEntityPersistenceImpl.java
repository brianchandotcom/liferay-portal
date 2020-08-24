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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLargeAmountColumnEntityException;
import com.liferay.portal.tools.service.builder.test.model.LargeAmountColumnEntity;
import com.liferay.portal.tools.service.builder.test.model.LargeAmountColumnEntityTable;
import com.liferay.portal.tools.service.builder.test.model.impl.LargeAmountColumnEntityImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LargeAmountColumnEntityModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.LargeAmountColumnEntityPersistence;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the large amount column entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LargeAmountColumnEntityPersistenceImpl
	extends BasePersistenceImpl<LargeAmountColumnEntity>
	implements LargeAmountColumnEntityPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LargeAmountColumnEntityUtil</code> to access the large amount column entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LargeAmountColumnEntityImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public LargeAmountColumnEntityPersistenceImpl() {
		setModelClass(LargeAmountColumnEntity.class);

		setModelImplClass(LargeAmountColumnEntityImpl.class);
		setModelPKClass(long.class);

		setTable(LargeAmountColumnEntityTable.INSTANCE);
	}

	/**
	 * Caches the large amount column entity in the entity cache if it is enabled.
	 *
	 * @param largeAmountColumnEntity the large amount column entity
	 */
	@Override
	public void cacheResult(LargeAmountColumnEntity largeAmountColumnEntity) {
		entityCache.putResult(
			LargeAmountColumnEntityImpl.class,
			largeAmountColumnEntity.getPrimaryKey(), largeAmountColumnEntity);
	}

	/**
	 * Caches the large amount column entities in the entity cache if it is enabled.
	 *
	 * @param largeAmountColumnEntities the large amount column entities
	 */
	@Override
	public void cacheResult(
		List<LargeAmountColumnEntity> largeAmountColumnEntities) {

		for (LargeAmountColumnEntity largeAmountColumnEntity :
				largeAmountColumnEntities) {

			if (entityCache.getResult(
					LargeAmountColumnEntityImpl.class,
					largeAmountColumnEntity.getPrimaryKey()) == null) {

				cacheResult(largeAmountColumnEntity);
			}
		}
	}

	/**
	 * Clears the cache for all large amount column entities.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LargeAmountColumnEntityImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the large amount column entity.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LargeAmountColumnEntity largeAmountColumnEntity) {
		entityCache.removeResult(
			LargeAmountColumnEntityImpl.class, largeAmountColumnEntity);
	}

	@Override
	public void clearCache(
		List<LargeAmountColumnEntity> largeAmountColumnEntities) {

		for (LargeAmountColumnEntity largeAmountColumnEntity :
				largeAmountColumnEntities) {

			entityCache.removeResult(
				LargeAmountColumnEntityImpl.class, largeAmountColumnEntity);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LargeAmountColumnEntityImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new large amount column entity with the primary key. Does not add the large amount column entity to the database.
	 *
	 * @param largeAmountColumnEntityId the primary key for the new large amount column entity
	 * @return the new large amount column entity
	 */
	@Override
	public LargeAmountColumnEntity create(long largeAmountColumnEntityId) {
		LargeAmountColumnEntity largeAmountColumnEntity =
			new LargeAmountColumnEntityImpl();

		largeAmountColumnEntity.setNew(true);
		largeAmountColumnEntity.setPrimaryKey(largeAmountColumnEntityId);

		return largeAmountColumnEntity;
	}

	/**
	 * Removes the large amount column entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity that was removed
	 * @throws NoSuchLargeAmountColumnEntityException if a large amount column entity with the primary key could not be found
	 */
	@Override
	public LargeAmountColumnEntity remove(long largeAmountColumnEntityId)
		throws NoSuchLargeAmountColumnEntityException {

		return remove((Serializable)largeAmountColumnEntityId);
	}

	/**
	 * Removes the large amount column entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the large amount column entity
	 * @return the large amount column entity that was removed
	 * @throws NoSuchLargeAmountColumnEntityException if a large amount column entity with the primary key could not be found
	 */
	@Override
	public LargeAmountColumnEntity remove(Serializable primaryKey)
		throws NoSuchLargeAmountColumnEntityException {

		Session session = null;

		try {
			session = openSession();

			LargeAmountColumnEntity largeAmountColumnEntity =
				(LargeAmountColumnEntity)session.get(
					LargeAmountColumnEntityImpl.class, primaryKey);

			if (largeAmountColumnEntity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLargeAmountColumnEntityException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(largeAmountColumnEntity);
		}
		catch (NoSuchLargeAmountColumnEntityException noSuchEntityException) {
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
	protected LargeAmountColumnEntity removeImpl(
		LargeAmountColumnEntity largeAmountColumnEntity) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(largeAmountColumnEntity)) {
				largeAmountColumnEntity = (LargeAmountColumnEntity)session.get(
					LargeAmountColumnEntityImpl.class,
					largeAmountColumnEntity.getPrimaryKeyObj());
			}

			if (largeAmountColumnEntity != null) {
				session.delete(largeAmountColumnEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (largeAmountColumnEntity != null) {
			clearCache(largeAmountColumnEntity);
		}

		return largeAmountColumnEntity;
	}

	@Override
	public LargeAmountColumnEntity updateImpl(
		LargeAmountColumnEntity largeAmountColumnEntity) {

		boolean isNew = largeAmountColumnEntity.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(largeAmountColumnEntity);
			}
			else {
				largeAmountColumnEntity =
					(LargeAmountColumnEntity)session.merge(
						largeAmountColumnEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			LargeAmountColumnEntityImpl.class, largeAmountColumnEntity, false,
			true);

		if (isNew) {
			largeAmountColumnEntity.setNew(false);
		}

		largeAmountColumnEntity.resetOriginalValues();

		return largeAmountColumnEntity;
	}

	/**
	 * Returns the large amount column entity with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the large amount column entity
	 * @return the large amount column entity
	 * @throws NoSuchLargeAmountColumnEntityException if a large amount column entity with the primary key could not be found
	 */
	@Override
	public LargeAmountColumnEntity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLargeAmountColumnEntityException {

		LargeAmountColumnEntity largeAmountColumnEntity = fetchByPrimaryKey(
			primaryKey);

		if (largeAmountColumnEntity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLargeAmountColumnEntityException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return largeAmountColumnEntity;
	}

	/**
	 * Returns the large amount column entity with the primary key or throws a <code>NoSuchLargeAmountColumnEntityException</code> if it could not be found.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity
	 * @throws NoSuchLargeAmountColumnEntityException if a large amount column entity with the primary key could not be found
	 */
	@Override
	public LargeAmountColumnEntity findByPrimaryKey(
			long largeAmountColumnEntityId)
		throws NoSuchLargeAmountColumnEntityException {

		return findByPrimaryKey((Serializable)largeAmountColumnEntityId);
	}

	/**
	 * Returns the large amount column entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity, or <code>null</code> if a large amount column entity with the primary key could not be found
	 */
	@Override
	public LargeAmountColumnEntity fetchByPrimaryKey(
		long largeAmountColumnEntityId) {

		return fetchByPrimaryKey((Serializable)largeAmountColumnEntityId);
	}

	/**
	 * Returns all the large amount column entities.
	 *
	 * @return the large amount column entities
	 */
	@Override
	public List<LargeAmountColumnEntity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @return the range of large amount column entities
	 */
	@Override
	public List<LargeAmountColumnEntity> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of large amount column entities
	 */
	@Override
	public List<LargeAmountColumnEntity> findAll(
		int start, int end,
		OrderByComparator<LargeAmountColumnEntity> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of large amount column entities
	 */
	@Override
	public List<LargeAmountColumnEntity> findAll(
		int start, int end,
		OrderByComparator<LargeAmountColumnEntity> orderByComparator,
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

		List<LargeAmountColumnEntity> list = null;

		if (useFinderCache) {
			list = (List<LargeAmountColumnEntity>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LARGEAMOUNTCOLUMNENTITY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LARGEAMOUNTCOLUMNENTITY;

				sql = sql.concat(
					LargeAmountColumnEntityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LargeAmountColumnEntity>)QueryUtil.list(
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
	 * Removes all the large amount column entities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LargeAmountColumnEntity largeAmountColumnEntity : findAll()) {
			remove(largeAmountColumnEntity);
		}
	}

	/**
	 * Returns the number of large amount column entities.
	 *
	 * @return the number of large amount column entities
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
					_SQL_COUNT_LARGEAMOUNTCOLUMNENTITY);

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
		return "largeAmountColumnEntityId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LARGEAMOUNTCOLUMNENTITY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LargeAmountColumnEntityModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the large amount column entity persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			LargeAmountColumnEntityPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new LargeAmountColumnEntityModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", LargeAmountColumnEntity.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);
	}

	public void destroy() {
		entityCache.removeCache(LargeAmountColumnEntityImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_LARGEAMOUNTCOLUMNENTITY =
		"SELECT largeAmountColumnEntity FROM LargeAmountColumnEntity largeAmountColumnEntity";

	private static final String _SQL_COUNT_LARGEAMOUNTCOLUMNENTITY =
		"SELECT COUNT(largeAmountColumnEntity) FROM LargeAmountColumnEntity largeAmountColumnEntity";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"largeAmountColumnEntity.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LargeAmountColumnEntity exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		LargeAmountColumnEntityPersistenceImpl.class);

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;
	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();

	private static class LargeAmountColumnEntityModelArgumentsResolver
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

			LargeAmountColumnEntityModelImpl largeAmountColumnEntityModelImpl =
				(LargeAmountColumnEntityModelImpl)baseModel;

			Object[] values = _getValue(
				largeAmountColumnEntityModelImpl, columnNames, original);

			if (!checkColumn ||
				!Arrays.equals(
					values,
					_getValue(
						largeAmountColumnEntityModelImpl, columnNames,
						!original))) {

				return values;
			}

			return null;
		}

		private Object[] _getValue(
			LargeAmountColumnEntityModelImpl largeAmountColumnEntityModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						largeAmountColumnEntityModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] =
						largeAmountColumnEntityModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}