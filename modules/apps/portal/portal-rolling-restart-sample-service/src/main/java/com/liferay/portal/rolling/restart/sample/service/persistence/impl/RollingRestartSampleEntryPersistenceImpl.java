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

package com.liferay.portal.rolling.restart.sample.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.rolling.restart.sample.exception.NoSuchRollingRestartSampleEntryException;
import com.liferay.portal.rolling.restart.sample.model.RollingRestartSampleEntry;
import com.liferay.portal.rolling.restart.sample.model.RollingRestartSampleEntryTable;
import com.liferay.portal.rolling.restart.sample.model.impl.RollingRestartSampleEntryImpl;
import com.liferay.portal.rolling.restart.sample.model.impl.RollingRestartSampleEntryModelImpl;
import com.liferay.portal.rolling.restart.sample.service.persistence.RollingRestartSampleEntryPersistence;
import com.liferay.portal.rolling.restart.sample.service.persistence.impl.constants.RollingRestartSamplePortletPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the rolling restart sample entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		RollingRestartSampleEntryPersistence.class, BasePersistence.class
	}
)
public class RollingRestartSampleEntryPersistenceImpl
	extends BasePersistenceImpl<RollingRestartSampleEntry>
	implements RollingRestartSampleEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RollingRestartSampleEntryUtil</code> to access the rolling restart sample entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RollingRestartSampleEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public RollingRestartSampleEntryPersistenceImpl() {
		setModelClass(RollingRestartSampleEntry.class);

		setModelImplClass(RollingRestartSampleEntryImpl.class);
		setModelPKClass(long.class);

		setTable(RollingRestartSampleEntryTable.INSTANCE);
	}

	/**
	 * Caches the rolling restart sample entry in the entity cache if it is enabled.
	 *
	 * @param rollingRestartSampleEntry the rolling restart sample entry
	 */
	@Override
	public void cacheResult(
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		entityCache.putResult(
			RollingRestartSampleEntryImpl.class,
			rollingRestartSampleEntry.getPrimaryKey(),
			rollingRestartSampleEntry);
	}

	/**
	 * Caches the rolling restart sample entries in the entity cache if it is enabled.
	 *
	 * @param rollingRestartSampleEntries the rolling restart sample entries
	 */
	@Override
	public void cacheResult(
		List<RollingRestartSampleEntry> rollingRestartSampleEntries) {

		for (RollingRestartSampleEntry rollingRestartSampleEntry :
				rollingRestartSampleEntries) {

			if (entityCache.getResult(
					RollingRestartSampleEntryImpl.class,
					rollingRestartSampleEntry.getPrimaryKey()) == null) {

				cacheResult(rollingRestartSampleEntry);
			}
		}
	}

	/**
	 * Clears the cache for all rolling restart sample entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RollingRestartSampleEntryImpl.class);

		finderCache.clearCache(RollingRestartSampleEntryImpl.class);
	}

	/**
	 * Clears the cache for the rolling restart sample entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		entityCache.removeResult(
			RollingRestartSampleEntryImpl.class, rollingRestartSampleEntry);
	}

	@Override
	public void clearCache(
		List<RollingRestartSampleEntry> rollingRestartSampleEntries) {

		for (RollingRestartSampleEntry rollingRestartSampleEntry :
				rollingRestartSampleEntries) {

			entityCache.removeResult(
				RollingRestartSampleEntryImpl.class, rollingRestartSampleEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RollingRestartSampleEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				RollingRestartSampleEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new rolling restart sample entry with the primary key. Does not add the rolling restart sample entry to the database.
	 *
	 * @param entryId the primary key for the new rolling restart sample entry
	 * @return the new rolling restart sample entry
	 */
	@Override
	public RollingRestartSampleEntry create(long entryId) {
		RollingRestartSampleEntry rollingRestartSampleEntry =
			new RollingRestartSampleEntryImpl();

		rollingRestartSampleEntry.setNew(true);
		rollingRestartSampleEntry.setPrimaryKey(entryId);

		rollingRestartSampleEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return rollingRestartSampleEntry;
	}

	/**
	 * Removes the rolling restart sample entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry that was removed
	 * @throws NoSuchRollingRestartSampleEntryException if a rolling restart sample entry with the primary key could not be found
	 */
	@Override
	public RollingRestartSampleEntry remove(long entryId)
		throws NoSuchRollingRestartSampleEntryException {

		return remove((Serializable)entryId);
	}

	/**
	 * Removes the rolling restart sample entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry that was removed
	 * @throws NoSuchRollingRestartSampleEntryException if a rolling restart sample entry with the primary key could not be found
	 */
	@Override
	public RollingRestartSampleEntry remove(Serializable primaryKey)
		throws NoSuchRollingRestartSampleEntryException {

		Session session = null;

		try {
			session = openSession();

			RollingRestartSampleEntry rollingRestartSampleEntry =
				(RollingRestartSampleEntry)session.get(
					RollingRestartSampleEntryImpl.class, primaryKey);

			if (rollingRestartSampleEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRollingRestartSampleEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(rollingRestartSampleEntry);
		}
		catch (NoSuchRollingRestartSampleEntryException noSuchEntityException) {
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
	protected RollingRestartSampleEntry removeImpl(
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(rollingRestartSampleEntry)) {
				rollingRestartSampleEntry =
					(RollingRestartSampleEntry)session.get(
						RollingRestartSampleEntryImpl.class,
						rollingRestartSampleEntry.getPrimaryKeyObj());
			}

			if (rollingRestartSampleEntry != null) {
				session.delete(rollingRestartSampleEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (rollingRestartSampleEntry != null) {
			clearCache(rollingRestartSampleEntry);
		}

		return rollingRestartSampleEntry;
	}

	@Override
	public RollingRestartSampleEntry updateImpl(
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		boolean isNew = rollingRestartSampleEntry.isNew();

		if (!(rollingRestartSampleEntry instanceof
				RollingRestartSampleEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(rollingRestartSampleEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					rollingRestartSampleEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in rollingRestartSampleEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RollingRestartSampleEntry implementation " +
					rollingRestartSampleEntry.getClass());
		}

		RollingRestartSampleEntryModelImpl rollingRestartSampleEntryModelImpl =
			(RollingRestartSampleEntryModelImpl)rollingRestartSampleEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (rollingRestartSampleEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				rollingRestartSampleEntry.setCreateDate(date);
			}
			else {
				rollingRestartSampleEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!rollingRestartSampleEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				rollingRestartSampleEntry.setModifiedDate(date);
			}
			else {
				rollingRestartSampleEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(rollingRestartSampleEntry);
			}
			else {
				rollingRestartSampleEntry =
					(RollingRestartSampleEntry)session.merge(
						rollingRestartSampleEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RollingRestartSampleEntryImpl.class, rollingRestartSampleEntry,
			false, true);

		if (isNew) {
			rollingRestartSampleEntry.setNew(false);
		}

		rollingRestartSampleEntry.resetOriginalValues();

		return rollingRestartSampleEntry;
	}

	/**
	 * Returns the rolling restart sample entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry
	 * @throws NoSuchRollingRestartSampleEntryException if a rolling restart sample entry with the primary key could not be found
	 */
	@Override
	public RollingRestartSampleEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRollingRestartSampleEntryException {

		RollingRestartSampleEntry rollingRestartSampleEntry = fetchByPrimaryKey(
			primaryKey);

		if (rollingRestartSampleEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRollingRestartSampleEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return rollingRestartSampleEntry;
	}

	/**
	 * Returns the rolling restart sample entry with the primary key or throws a <code>NoSuchRollingRestartSampleEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry
	 * @throws NoSuchRollingRestartSampleEntryException if a rolling restart sample entry with the primary key could not be found
	 */
	@Override
	public RollingRestartSampleEntry findByPrimaryKey(long entryId)
		throws NoSuchRollingRestartSampleEntryException {

		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the rolling restart sample entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry, or <code>null</code> if a rolling restart sample entry with the primary key could not be found
	 */
	@Override
	public RollingRestartSampleEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns all the rolling restart sample entries.
	 *
	 * @return the rolling restart sample entries
	 */
	@Override
	public List<RollingRestartSampleEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the rolling restart sample entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RollingRestartSampleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rolling restart sample entries
	 * @param end the upper bound of the range of rolling restart sample entries (not inclusive)
	 * @return the range of rolling restart sample entries
	 */
	@Override
	public List<RollingRestartSampleEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the rolling restart sample entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RollingRestartSampleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rolling restart sample entries
	 * @param end the upper bound of the range of rolling restart sample entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rolling restart sample entries
	 */
	@Override
	public List<RollingRestartSampleEntry> findAll(
		int start, int end,
		OrderByComparator<RollingRestartSampleEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the rolling restart sample entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RollingRestartSampleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rolling restart sample entries
	 * @param end the upper bound of the range of rolling restart sample entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rolling restart sample entries
	 */
	@Override
	public List<RollingRestartSampleEntry> findAll(
		int start, int end,
		OrderByComparator<RollingRestartSampleEntry> orderByComparator,
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

		List<RollingRestartSampleEntry> list = null;

		if (useFinderCache) {
			list = (List<RollingRestartSampleEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ROLLINGRESTARTSAMPLEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ROLLINGRESTARTSAMPLEENTRY;

				sql = sql.concat(
					RollingRestartSampleEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RollingRestartSampleEntry>)QueryUtil.list(
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
	 * Removes all the rolling restart sample entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RollingRestartSampleEntry rollingRestartSampleEntry : findAll()) {
			remove(rollingRestartSampleEntry);
		}
	}

	/**
	 * Returns the number of rolling restart sample entries.
	 *
	 * @return the number of rolling restart sample entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_ROLLINGRESTARTSAMPLEENTRY);

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
		return "entryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ROLLINGRESTARTSAMPLEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RollingRestartSampleEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the rolling restart sample entry persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new RollingRestartSampleEntryModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(RollingRestartSampleEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = RollingRestartSamplePortletPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = RollingRestartSamplePortletPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RollingRestartSamplePortletPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ROLLINGRESTARTSAMPLEENTRY =
		"SELECT rollingRestartSampleEntry FROM RollingRestartSampleEntry rollingRestartSampleEntry";

	private static final String _SQL_COUNT_ROLLINGRESTARTSAMPLEENTRY =
		"SELECT COUNT(rollingRestartSampleEntry) FROM RollingRestartSampleEntry rollingRestartSampleEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"rollingRestartSampleEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RollingRestartSampleEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		RollingRestartSampleEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class RollingRestartSampleEntryModelArgumentsResolver
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

			RollingRestartSampleEntryModelImpl
				rollingRestartSampleEntryModelImpl =
					(RollingRestartSampleEntryModelImpl)baseModel;

			long columnBitmask =
				rollingRestartSampleEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					rollingRestartSampleEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						rollingRestartSampleEntryModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					rollingRestartSampleEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return RollingRestartSampleEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return RollingRestartSampleEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			RollingRestartSampleEntryModelImpl
				rollingRestartSampleEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						rollingRestartSampleEntryModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						rollingRestartSampleEntryModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}