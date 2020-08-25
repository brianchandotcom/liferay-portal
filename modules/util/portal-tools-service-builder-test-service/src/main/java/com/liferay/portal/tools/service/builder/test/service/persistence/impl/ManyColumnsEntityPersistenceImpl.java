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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchManyColumnsEntityException;
import com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity;
import com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntityTable;
import com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntityImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntityModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.ManyColumnsEntityPersistence;

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
 * The persistence implementation for the many columns entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ManyColumnsEntityPersistenceImpl
	extends BasePersistenceImpl<ManyColumnsEntity>
	implements ManyColumnsEntityPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ManyColumnsEntityUtil</code> to access the many columns entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ManyColumnsEntityImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public ManyColumnsEntityPersistenceImpl() {
		setModelClass(ManyColumnsEntity.class);

		setModelImplClass(ManyColumnsEntityImpl.class);
		setModelPKClass(long.class);

		setTable(ManyColumnsEntityTable.INSTANCE);
	}

	/**
	 * Caches the many columns entity in the entity cache if it is enabled.
	 *
	 * @param manyColumnsEntity the many columns entity
	 */
	@Override
	public void cacheResult(ManyColumnsEntity manyColumnsEntity) {
		entityCache.putResult(
			ManyColumnsEntityImpl.class, manyColumnsEntity.getPrimaryKey(),
			manyColumnsEntity);
	}

	/**
	 * Caches the many columns entities in the entity cache if it is enabled.
	 *
	 * @param manyColumnsEntities the many columns entities
	 */
	@Override
	public void cacheResult(List<ManyColumnsEntity> manyColumnsEntities) {
		for (ManyColumnsEntity manyColumnsEntity : manyColumnsEntities) {
			if (entityCache.getResult(
					ManyColumnsEntityImpl.class,
					manyColumnsEntity.getPrimaryKey()) == null) {

				cacheResult(manyColumnsEntity);
			}
		}
	}

	/**
	 * Clears the cache for all many columns entities.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ManyColumnsEntityImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the many columns entity.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ManyColumnsEntity manyColumnsEntity) {
		entityCache.removeResult(
			ManyColumnsEntityImpl.class, manyColumnsEntity);
	}

	@Override
	public void clearCache(List<ManyColumnsEntity> manyColumnsEntities) {
		for (ManyColumnsEntity manyColumnsEntity : manyColumnsEntities) {
			entityCache.removeResult(
				ManyColumnsEntityImpl.class, manyColumnsEntity);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ManyColumnsEntityImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new many columns entity with the primary key. Does not add the many columns entity to the database.
	 *
	 * @param manyColumnsEntityId the primary key for the new many columns entity
	 * @return the new many columns entity
	 */
	@Override
	public ManyColumnsEntity create(long manyColumnsEntityId) {
		ManyColumnsEntity manyColumnsEntity = new ManyColumnsEntityImpl();

		manyColumnsEntity.setNew(true);
		manyColumnsEntity.setPrimaryKey(manyColumnsEntityId);

		return manyColumnsEntity;
	}

	/**
	 * Removes the many columns entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param manyColumnsEntityId the primary key of the many columns entity
	 * @return the many columns entity that was removed
	 * @throws NoSuchManyColumnsEntityException if a many columns entity with the primary key could not be found
	 */
	@Override
	public ManyColumnsEntity remove(long manyColumnsEntityId)
		throws NoSuchManyColumnsEntityException {

		return remove((Serializable)manyColumnsEntityId);
	}

	/**
	 * Removes the many columns entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the many columns entity
	 * @return the many columns entity that was removed
	 * @throws NoSuchManyColumnsEntityException if a many columns entity with the primary key could not be found
	 */
	@Override
	public ManyColumnsEntity remove(Serializable primaryKey)
		throws NoSuchManyColumnsEntityException {

		Session session = null;

		try {
			session = openSession();

			ManyColumnsEntity manyColumnsEntity =
				(ManyColumnsEntity)session.get(
					ManyColumnsEntityImpl.class, primaryKey);

			if (manyColumnsEntity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchManyColumnsEntityException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(manyColumnsEntity);
		}
		catch (NoSuchManyColumnsEntityException noSuchEntityException) {
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
	protected ManyColumnsEntity removeImpl(
		ManyColumnsEntity manyColumnsEntity) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(manyColumnsEntity)) {
				manyColumnsEntity = (ManyColumnsEntity)session.get(
					ManyColumnsEntityImpl.class,
					manyColumnsEntity.getPrimaryKeyObj());
			}

			if (manyColumnsEntity != null) {
				session.delete(manyColumnsEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (manyColumnsEntity != null) {
			clearCache(manyColumnsEntity);
		}

		return manyColumnsEntity;
	}

	@Override
	public ManyColumnsEntity updateImpl(ManyColumnsEntity manyColumnsEntity) {
		boolean isNew = manyColumnsEntity.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(manyColumnsEntity);
			}
			else {
				manyColumnsEntity = (ManyColumnsEntity)session.merge(
					manyColumnsEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ManyColumnsEntityImpl.class, manyColumnsEntity, false, true);

		if (isNew) {
			manyColumnsEntity.setNew(false);
		}

		manyColumnsEntity.resetOriginalValues();

		return manyColumnsEntity;
	}

	/**
	 * Returns the many columns entity with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the many columns entity
	 * @return the many columns entity
	 * @throws NoSuchManyColumnsEntityException if a many columns entity with the primary key could not be found
	 */
	@Override
	public ManyColumnsEntity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchManyColumnsEntityException {

		ManyColumnsEntity manyColumnsEntity = fetchByPrimaryKey(primaryKey);

		if (manyColumnsEntity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchManyColumnsEntityException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return manyColumnsEntity;
	}

	/**
	 * Returns the many columns entity with the primary key or throws a <code>NoSuchManyColumnsEntityException</code> if it could not be found.
	 *
	 * @param manyColumnsEntityId the primary key of the many columns entity
	 * @return the many columns entity
	 * @throws NoSuchManyColumnsEntityException if a many columns entity with the primary key could not be found
	 */
	@Override
	public ManyColumnsEntity findByPrimaryKey(long manyColumnsEntityId)
		throws NoSuchManyColumnsEntityException {

		return findByPrimaryKey((Serializable)manyColumnsEntityId);
	}

	/**
	 * Returns the many columns entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param manyColumnsEntityId the primary key of the many columns entity
	 * @return the many columns entity, or <code>null</code> if a many columns entity with the primary key could not be found
	 */
	@Override
	public ManyColumnsEntity fetchByPrimaryKey(long manyColumnsEntityId) {
		return fetchByPrimaryKey((Serializable)manyColumnsEntityId);
	}

	/**
	 * Returns all the many columns entities.
	 *
	 * @return the many columns entities
	 */
	@Override
	public List<ManyColumnsEntity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the many columns entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ManyColumnsEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of many columns entities
	 * @param end the upper bound of the range of many columns entities (not inclusive)
	 * @return the range of many columns entities
	 */
	@Override
	public List<ManyColumnsEntity> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the many columns entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ManyColumnsEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of many columns entities
	 * @param end the upper bound of the range of many columns entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of many columns entities
	 */
	@Override
	public List<ManyColumnsEntity> findAll(
		int start, int end,
		OrderByComparator<ManyColumnsEntity> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the many columns entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ManyColumnsEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of many columns entities
	 * @param end the upper bound of the range of many columns entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of many columns entities
	 */
	@Override
	public List<ManyColumnsEntity> findAll(
		int start, int end,
		OrderByComparator<ManyColumnsEntity> orderByComparator,
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

		List<ManyColumnsEntity> list = null;

		if (useFinderCache) {
			list = (List<ManyColumnsEntity>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MANYCOLUMNSENTITY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MANYCOLUMNSENTITY;

				sql = sql.concat(ManyColumnsEntityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ManyColumnsEntity>)QueryUtil.list(
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
	 * Removes all the many columns entities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ManyColumnsEntity manyColumnsEntity : findAll()) {
			remove(manyColumnsEntity);
		}
	}

	/**
	 * Returns the number of many columns entities.
	 *
	 * @return the number of many columns entities
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MANYCOLUMNSENTITY);

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
		return "manyColumnsEntityId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MANYCOLUMNSENTITY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ManyColumnsEntityModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the many columns entity persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			ManyColumnsEntityPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new ManyColumnsEntityModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", ManyColumnsEntity.class.getName()));

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
		entityCache.removeCache(ManyColumnsEntityImpl.class.getName());

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

	private static final String _SQL_SELECT_MANYCOLUMNSENTITY =
		"SELECT manyColumnsEntity FROM ManyColumnsEntity manyColumnsEntity";

	private static final String _SQL_COUNT_MANYCOLUMNSENTITY =
		"SELECT COUNT(manyColumnsEntity) FROM ManyColumnsEntity manyColumnsEntity";

	private static final String _ORDER_BY_ENTITY_ALIAS = "manyColumnsEntity.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ManyColumnsEntity exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		ManyColumnsEntityPersistenceImpl.class);

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

	private static class ManyColumnsEntityModelArgumentsResolver
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

			ManyColumnsEntityModelImpl manyColumnsEntityModelImpl =
				(ManyColumnsEntityModelImpl)baseModel;

			Object[] values = _getValue(
				manyColumnsEntityModelImpl, columnNames, original);

			if (!checkColumn ||
				!Arrays.equals(
					values,
					_getValue(
						manyColumnsEntityModelImpl, columnNames, !original))) {

				return values;
			}

			return null;
		}

		private Object[] _getValue(
			ManyColumnsEntityModelImpl manyColumnsEntityModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						manyColumnsEntityModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = manyColumnsEntityModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}