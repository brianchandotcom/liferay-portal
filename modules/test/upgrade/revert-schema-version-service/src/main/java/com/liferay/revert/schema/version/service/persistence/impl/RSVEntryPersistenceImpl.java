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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.revert.schema.version.exception.NoSuchEntryException;
import com.liferay.revert.schema.version.model.RSVEntry;
import com.liferay.revert.schema.version.model.RSVEntryTable;
import com.liferay.revert.schema.version.model.impl.RSVEntryImpl;
import com.liferay.revert.schema.version.model.impl.RSVEntryModelImpl;
import com.liferay.revert.schema.version.service.persistence.RSVEntryPersistence;
import com.liferay.revert.schema.version.service.persistence.impl.constants.RSVPersistenceConstants;

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
 * The persistence implementation for the rsv entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {RSVEntryPersistence.class, BasePersistence.class})
public class RSVEntryPersistenceImpl
	extends BasePersistenceImpl<RSVEntry> implements RSVEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RSVEntryUtil</code> to access the rsv entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RSVEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public RSVEntryPersistenceImpl() {
		setModelClass(RSVEntry.class);

		setModelImplClass(RSVEntryImpl.class);
		setModelPKClass(long.class);

		setTable(RSVEntryTable.INSTANCE);
	}

	/**
	 * Caches the rsv entry in the entity cache if it is enabled.
	 *
	 * @param rsvEntry the rsv entry
	 */
	@Override
	public void cacheResult(RSVEntry rsvEntry) {
		entityCache.putResult(
			RSVEntryImpl.class, rsvEntry.getPrimaryKey(), rsvEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the rsv entries in the entity cache if it is enabled.
	 *
	 * @param rsvEntries the rsv entries
	 */
	@Override
	public void cacheResult(List<RSVEntry> rsvEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (rsvEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RSVEntry rsvEntry : rsvEntries) {
			if (entityCache.getResult(
					RSVEntryImpl.class, rsvEntry.getPrimaryKey()) == null) {

				cacheResult(rsvEntry);
			}
		}
	}

	/**
	 * Clears the cache for all rsv entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RSVEntryImpl.class);

		finderCache.clearCache(RSVEntryImpl.class);
	}

	/**
	 * Clears the cache for the rsv entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RSVEntry rsvEntry) {
		entityCache.removeResult(RSVEntryImpl.class, rsvEntry);
	}

	@Override
	public void clearCache(List<RSVEntry> rsvEntries) {
		for (RSVEntry rsvEntry : rsvEntries) {
			entityCache.removeResult(RSVEntryImpl.class, rsvEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RSVEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(RSVEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new rsv entry with the primary key. Does not add the rsv entry to the database.
	 *
	 * @param rsvEntryId the primary key for the new rsv entry
	 * @return the new rsv entry
	 */
	@Override
	public RSVEntry create(long rsvEntryId) {
		RSVEntry rsvEntry = new RSVEntryImpl();

		rsvEntry.setNew(true);
		rsvEntry.setPrimaryKey(rsvEntryId);

		rsvEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return rsvEntry;
	}

	/**
	 * Removes the rsv entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry that was removed
	 * @throws NoSuchEntryException if a rsv entry with the primary key could not be found
	 */
	@Override
	public RSVEntry remove(long rsvEntryId) throws NoSuchEntryException {
		return remove((Serializable)rsvEntryId);
	}

	/**
	 * Removes the rsv entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the rsv entry
	 * @return the rsv entry that was removed
	 * @throws NoSuchEntryException if a rsv entry with the primary key could not be found
	 */
	@Override
	public RSVEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			RSVEntry rsvEntry = (RSVEntry)session.get(
				RSVEntryImpl.class, primaryKey);

			if (rsvEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(rsvEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected RSVEntry removeImpl(RSVEntry rsvEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(rsvEntry)) {
				rsvEntry = (RSVEntry)session.get(
					RSVEntryImpl.class, rsvEntry.getPrimaryKeyObj());
			}

			if (rsvEntry != null) {
				session.delete(rsvEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (rsvEntry != null) {
			clearCache(rsvEntry);
		}

		return rsvEntry;
	}

	@Override
	public RSVEntry updateImpl(RSVEntry rsvEntry) {
		boolean isNew = rsvEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(rsvEntry);
			}
			else {
				rsvEntry = (RSVEntry)session.merge(rsvEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(RSVEntryImpl.class, rsvEntry, false, true);

		if (isNew) {
			rsvEntry.setNew(false);
		}

		rsvEntry.resetOriginalValues();

		return rsvEntry;
	}

	/**
	 * Returns the rsv entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the rsv entry
	 * @return the rsv entry
	 * @throws NoSuchEntryException if a rsv entry with the primary key could not be found
	 */
	@Override
	public RSVEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		RSVEntry rsvEntry = fetchByPrimaryKey(primaryKey);

		if (rsvEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return rsvEntry;
	}

	/**
	 * Returns the rsv entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry
	 * @throws NoSuchEntryException if a rsv entry with the primary key could not be found
	 */
	@Override
	public RSVEntry findByPrimaryKey(long rsvEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)rsvEntryId);
	}

	/**
	 * Returns the rsv entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry, or <code>null</code> if a rsv entry with the primary key could not be found
	 */
	@Override
	public RSVEntry fetchByPrimaryKey(long rsvEntryId) {
		return fetchByPrimaryKey((Serializable)rsvEntryId);
	}

	/**
	 * Returns all the rsv entries.
	 *
	 * @return the rsv entries
	 */
	@Override
	public List<RSVEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the rsv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RSVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rsv entries
	 * @param end the upper bound of the range of rsv entries (not inclusive)
	 * @return the range of rsv entries
	 */
	@Override
	public List<RSVEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the rsv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RSVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rsv entries
	 * @param end the upper bound of the range of rsv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rsv entries
	 */
	@Override
	public List<RSVEntry> findAll(
		int start, int end, OrderByComparator<RSVEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the rsv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RSVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rsv entries
	 * @param end the upper bound of the range of rsv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rsv entries
	 */
	@Override
	public List<RSVEntry> findAll(
		int start, int end, OrderByComparator<RSVEntry> orderByComparator,
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

		List<RSVEntry> list = null;

		if (useFinderCache) {
			list = (List<RSVEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_RSVENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_RSVENTRY;

				sql = sql.concat(RSVEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RSVEntry>)QueryUtil.list(
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
	 * Removes all the rsv entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RSVEntry rsvEntry : findAll()) {
			remove(rsvEntry);
		}
	}

	/**
	 * Returns the number of rsv entries.
	 *
	 * @return the number of rsv entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_RSVENTRY);

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
		return "rsvEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RSVENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RSVEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the rsv entry persistence.
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
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(RSVEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = RSVPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = RSVPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RSVPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_RSVENTRY =
		"SELECT rsvEntry FROM RSVEntry rsvEntry";

	private static final String _SQL_COUNT_RSVENTRY =
		"SELECT COUNT(rsvEntry) FROM RSVEntry rsvEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "rsvEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RSVEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		RSVEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private RSVEntryModelArgumentsResolver _rsvEntryModelArgumentsResolver;

}