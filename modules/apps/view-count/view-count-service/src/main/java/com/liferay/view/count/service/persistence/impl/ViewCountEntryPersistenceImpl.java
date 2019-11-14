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

package com.liferay.view.count.service.persistence.impl;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.view.count.exception.NoSuchEntryException;
import com.liferay.view.count.model.ViewCountEntry;
import com.liferay.view.count.model.impl.ViewCountEntryImpl;
import com.liferay.view.count.model.impl.ViewCountEntryModelImpl;
import com.liferay.view.count.service.persistence.ViewCountEntryPK;
import com.liferay.view.count.service.persistence.ViewCountEntryPersistence;
import com.liferay.view.count.service.persistence.impl.constants.ViewCountPersistenceConstants;

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
 * The persistence implementation for the view count entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Preston Crary
 * @generated
 */
@Component(service = ViewCountEntryPersistence.class)
public class ViewCountEntryPersistenceImpl
	extends BasePersistenceImpl<ViewCountEntry>
	implements ViewCountEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ViewCountEntryUtil</code> to access the view count entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ViewCountEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public ViewCountEntryPersistenceImpl() {
		setModelClass(ViewCountEntry.class);

		setModelImplClass(ViewCountEntryImpl.class);
		setModelPKClass(ViewCountEntryPK.class);
	}

	/**
	 * Caches the view count entry in the entity cache if it is enabled.
	 *
	 * @param viewCountEntry the view count entry
	 */
	@Override
	public void cacheResult(ViewCountEntry viewCountEntry) {
		entityCache.putResult(
			entityCacheEnabled, ViewCountEntryImpl.class,
			viewCountEntry.getPrimaryKey(), viewCountEntry);

		viewCountEntry.resetOriginalValues();
	}

	/**
	 * Caches the view count entries in the entity cache if it is enabled.
	 *
	 * @param viewCountEntries the view count entries
	 */
	@Override
	public void cacheResult(List<ViewCountEntry> viewCountEntries) {
		for (ViewCountEntry viewCountEntry : viewCountEntries) {
			if (entityCache.getResult(
					entityCacheEnabled, ViewCountEntryImpl.class,
					viewCountEntry.getPrimaryKey()) == null) {

				cacheResult(viewCountEntry);
			}
			else {
				viewCountEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all view count entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ViewCountEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the view count entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ViewCountEntry viewCountEntry) {
		entityCache.removeResult(
			entityCacheEnabled, ViewCountEntryImpl.class,
			viewCountEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ViewCountEntry> viewCountEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ViewCountEntry viewCountEntry : viewCountEntries) {
			entityCache.removeResult(
				entityCacheEnabled, ViewCountEntryImpl.class,
				viewCountEntry.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, ViewCountEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new view count entry with the primary key. Does not add the view count entry to the database.
	 *
	 * @param viewCountEntryPK the primary key for the new view count entry
	 * @return the new view count entry
	 */
	@Override
	public ViewCountEntry create(ViewCountEntryPK viewCountEntryPK) {
		ViewCountEntry viewCountEntry = new ViewCountEntryImpl();

		viewCountEntry.setNew(true);
		viewCountEntry.setPrimaryKey(viewCountEntryPK);

		viewCountEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return viewCountEntry;
	}

	/**
	 * Removes the view count entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry that was removed
	 * @throws NoSuchEntryException if a view count entry with the primary key could not be found
	 */
	@Override
	public ViewCountEntry remove(ViewCountEntryPK viewCountEntryPK)
		throws NoSuchEntryException {

		return remove((Serializable)viewCountEntryPK);
	}

	/**
	 * Removes the view count entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the view count entry
	 * @return the view count entry that was removed
	 * @throws NoSuchEntryException if a view count entry with the primary key could not be found
	 */
	@Override
	public ViewCountEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			ViewCountEntry viewCountEntry = (ViewCountEntry)session.get(
				ViewCountEntryImpl.class, primaryKey);

			if (viewCountEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(viewCountEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected ViewCountEntry removeImpl(ViewCountEntry viewCountEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(viewCountEntry)) {
				viewCountEntry = (ViewCountEntry)session.get(
					ViewCountEntryImpl.class,
					viewCountEntry.getPrimaryKeyObj());
			}

			if (viewCountEntry != null) {
				session.delete(viewCountEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (viewCountEntry != null) {
			clearCache(viewCountEntry);
		}

		return viewCountEntry;
	}

	@Override
	public ViewCountEntry updateImpl(ViewCountEntry viewCountEntry) {
		boolean isNew = viewCountEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (viewCountEntry.isNew()) {
				session.save(viewCountEntry);

				viewCountEntry.setNew(false);
			}
			else {
				viewCountEntry = (ViewCountEntry)session.merge(viewCountEntry);
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
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			entityCacheEnabled, ViewCountEntryImpl.class,
			viewCountEntry.getPrimaryKey(), viewCountEntry, false);

		viewCountEntry.resetOriginalValues();

		return viewCountEntry;
	}

	/**
	 * Returns the view count entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the view count entry
	 * @return the view count entry
	 * @throws NoSuchEntryException if a view count entry with the primary key could not be found
	 */
	@Override
	public ViewCountEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		ViewCountEntry viewCountEntry = fetchByPrimaryKey(primaryKey);

		if (viewCountEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return viewCountEntry;
	}

	/**
	 * Returns the view count entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry
	 * @throws NoSuchEntryException if a view count entry with the primary key could not be found
	 */
	@Override
	public ViewCountEntry findByPrimaryKey(ViewCountEntryPK viewCountEntryPK)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)viewCountEntryPK);
	}

	/**
	 * Returns the view count entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry, or <code>null</code> if a view count entry with the primary key could not be found
	 */
	@Override
	public ViewCountEntry fetchByPrimaryKey(ViewCountEntryPK viewCountEntryPK) {
		return fetchByPrimaryKey((Serializable)viewCountEntryPK);
	}

	/**
	 * Returns all the view count entries.
	 *
	 * @return the view count entries
	 */
	@Override
	public List<ViewCountEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the view count entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @return the range of view count entries
	 */
	@Override
	public List<ViewCountEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the view count entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of view count entries
	 */
	@Override
	public List<ViewCountEntry> findAll(
		int start, int end,
		OrderByComparator<ViewCountEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the view count entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of view count entries
	 */
	@Override
	public List<ViewCountEntry> findAll(
		int start, int end, OrderByComparator<ViewCountEntry> orderByComparator,
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

		List<ViewCountEntry> list = null;

		if (useFinderCache) {
			list = (List<ViewCountEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_VIEWCOUNTENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_VIEWCOUNTENTRY;

				sql = sql.concat(ViewCountEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<ViewCountEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the view count entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ViewCountEntry viewCountEntry : findAll()) {
			remove(viewCountEntry);
		}
	}

	/**
	 * Returns the number of view count entries.
	 *
	 * @return the number of view count entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_VIEWCOUNTENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getCompoundPKColumnNames() {
		return _compoundPKColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "viewCountEntryPK";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_VIEWCOUNTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ViewCountEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the view count entry persistence.
	 */
	@Activate
	public void activate() {
		ViewCountEntryModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		ViewCountEntryModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ViewCountEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ViewCountEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ViewCountEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = ViewCountPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.view.count.model.ViewCountEntry"),
			true);
	}

	@Override
	@Reference(
		target = ViewCountPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ViewCountPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_VIEWCOUNTENTRY =
		"SELECT viewCountEntry FROM ViewCountEntry viewCountEntry";

	private static final String _SQL_COUNT_VIEWCOUNTENTRY =
		"SELECT COUNT(viewCountEntry) FROM ViewCountEntry viewCountEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "viewCountEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ViewCountEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		ViewCountEntryPersistenceImpl.class);

	private static final Set<String> _compoundPKColumnNames = SetUtil.fromArray(
		new String[] {"companyId", "classNameId", "classPK"});

	static {
		try {
			Class.forName(ViewCountPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}