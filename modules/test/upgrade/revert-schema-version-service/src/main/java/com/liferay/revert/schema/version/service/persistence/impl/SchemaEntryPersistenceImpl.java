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

package com.liferay.revert.schema.version.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.revert.schema.version.exception.NoSuchSchemaEntryException;
import com.liferay.revert.schema.version.model.SchemaEntry;
import com.liferay.revert.schema.version.model.impl.SchemaEntryImpl;
import com.liferay.revert.schema.version.model.impl.SchemaEntryModelImpl;
import com.liferay.revert.schema.version.service.persistence.SchemaEntryPersistence;
import com.liferay.revert.schema.version.service.persistence.impl.constants.RevertSchemaVersionPersistenceConstants;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the schema entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = SchemaEntryPersistence.class)
public class SchemaEntryPersistenceImpl
	extends BasePersistenceImpl<SchemaEntry> implements SchemaEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SchemaEntryUtil</code> to access the schema entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SchemaEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public SchemaEntryPersistenceImpl() {
		setModelClass(SchemaEntry.class);

		setModelImplClass(SchemaEntryImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the schema entry in the entity cache if it is enabled.
	 *
	 * @param schemaEntry the schema entry
	 */
	@Override
	public void cacheResult(SchemaEntry schemaEntry) {
		entityCache.putResult(
			entityCacheEnabled, SchemaEntryImpl.class,
			schemaEntry.getPrimaryKey(), schemaEntry);

		schemaEntry.resetOriginalValues();
	}

	/**
	 * Caches the schema entries in the entity cache if it is enabled.
	 *
	 * @param schemaEntries the schema entries
	 */
	@Override
	public void cacheResult(List<SchemaEntry> schemaEntries) {
		for (SchemaEntry schemaEntry : schemaEntries) {
			if (entityCache.getResult(
					entityCacheEnabled, SchemaEntryImpl.class,
					schemaEntry.getPrimaryKey()) == null) {

				cacheResult(schemaEntry);
			}
			else {
				schemaEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all schema entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SchemaEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the schema entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SchemaEntry schemaEntry) {
		entityCache.removeResult(
			entityCacheEnabled, SchemaEntryImpl.class,
			schemaEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SchemaEntry> schemaEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SchemaEntry schemaEntry : schemaEntries) {
			entityCache.removeResult(
				entityCacheEnabled, SchemaEntryImpl.class,
				schemaEntry.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, SchemaEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new schema entry with the primary key. Does not add the schema entry to the database.
	 *
	 * @param entryId the primary key for the new schema entry
	 * @return the new schema entry
	 */
	@Override
	public SchemaEntry create(long entryId) {
		SchemaEntry schemaEntry = new SchemaEntryImpl();

		schemaEntry.setNew(true);
		schemaEntry.setPrimaryKey(entryId);

		schemaEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return schemaEntry;
	}

	/**
	 * Removes the schema entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry that was removed
	 * @throws NoSuchSchemaEntryException if a schema entry with the primary key could not be found
	 */
	@Override
	public SchemaEntry remove(long entryId) throws NoSuchSchemaEntryException {
		return remove((Serializable)entryId);
	}

	/**
	 * Removes the schema entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the schema entry
	 * @return the schema entry that was removed
	 * @throws NoSuchSchemaEntryException if a schema entry with the primary key could not be found
	 */
	@Override
	public SchemaEntry remove(Serializable primaryKey)
		throws NoSuchSchemaEntryException {

		Session session = null;

		try {
			session = openSession();

			SchemaEntry schemaEntry = (SchemaEntry)session.get(
				SchemaEntryImpl.class, primaryKey);

			if (schemaEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSchemaEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(schemaEntry);
		}
		catch (NoSuchSchemaEntryException noSuchEntityException) {
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
	protected SchemaEntry removeImpl(SchemaEntry schemaEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(schemaEntry)) {
				schemaEntry = (SchemaEntry)session.get(
					SchemaEntryImpl.class, schemaEntry.getPrimaryKeyObj());
			}

			if (schemaEntry != null) {
				session.delete(schemaEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (schemaEntry != null) {
			clearCache(schemaEntry);
		}

		return schemaEntry;
	}

	@Override
	public SchemaEntry updateImpl(SchemaEntry schemaEntry) {
		boolean isNew = schemaEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (schemaEntry.isNew()) {
				session.save(schemaEntry);

				schemaEntry.setNew(false);
			}
			else {
				schemaEntry = (SchemaEntry)session.merge(schemaEntry);
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
			entityCacheEnabled, SchemaEntryImpl.class,
			schemaEntry.getPrimaryKey(), schemaEntry, false);

		schemaEntry.resetOriginalValues();

		return schemaEntry;
	}

	/**
	 * Returns the schema entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the schema entry
	 * @return the schema entry
	 * @throws NoSuchSchemaEntryException if a schema entry with the primary key could not be found
	 */
	@Override
	public SchemaEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSchemaEntryException {

		SchemaEntry schemaEntry = fetchByPrimaryKey(primaryKey);

		if (schemaEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSchemaEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return schemaEntry;
	}

	/**
	 * Returns the schema entry with the primary key or throws a <code>NoSuchSchemaEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry
	 * @throws NoSuchSchemaEntryException if a schema entry with the primary key could not be found
	 */
	@Override
	public SchemaEntry findByPrimaryKey(long entryId)
		throws NoSuchSchemaEntryException {

		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the schema entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry, or <code>null</code> if a schema entry with the primary key could not be found
	 */
	@Override
	public SchemaEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns all the schema entries.
	 *
	 * @return the schema entries
	 */
	@Override
	public List<SchemaEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @return the range of schema entries
	 */
	@Override
	public List<SchemaEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of schema entries
	 */
	@Override
	public List<SchemaEntry> findAll(
		int start, int end, OrderByComparator<SchemaEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of schema entries
	 */
	@Override
	public List<SchemaEntry> findAll(
		int start, int end, OrderByComparator<SchemaEntry> orderByComparator,
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

		List<SchemaEntry> list = null;

		if (useFinderCache) {
			list = (List<SchemaEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SCHEMAENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SCHEMAENTRY;

				sql = sql.concat(SchemaEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SchemaEntry>)QueryUtil.list(
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
	 * Removes all the schema entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SchemaEntry schemaEntry : findAll()) {
			remove(schemaEntry);
		}
	}

	/**
	 * Returns the number of schema entries.
	 *
	 * @return the number of schema entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SCHEMAENTRY);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "entryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SCHEMAENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SchemaEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the schema entry persistence.
	 */
	@Activate
	public void activate() {
		SchemaEntryModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		SchemaEntryModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, SchemaEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, SchemaEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SchemaEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = RevertSchemaVersionPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.revert.schema.version.model.SchemaEntry"),
			true);
	}

	@Override
	@Reference(
		target = RevertSchemaVersionPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RevertSchemaVersionPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SCHEMAENTRY =
		"SELECT schemaEntry FROM SchemaEntry schemaEntry";

	private static final String _SQL_COUNT_SCHEMAENTRY =
		"SELECT COUNT(schemaEntry) FROM SchemaEntry schemaEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "schemaEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SchemaEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		SchemaEntryPersistenceImpl.class);

	static {
		try {
			Class.forName(
				RevertSchemaVersionPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}