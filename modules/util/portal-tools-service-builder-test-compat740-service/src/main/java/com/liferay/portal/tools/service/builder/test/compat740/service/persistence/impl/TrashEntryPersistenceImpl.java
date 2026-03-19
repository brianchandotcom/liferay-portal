/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl;

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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchTrashEntryException;
import com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry;
import com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntryTable;
import com.liferay.portal.tools.service.builder.test.compat740.model.impl.TrashEntryImpl;
import com.liferay.portal.tools.service.builder.test.compat740.model.impl.TrashEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.TrashEntryPersistence;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.TrashEntryUtil;
import com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl.constants.SBCompat740PersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the trash entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = TrashEntryPersistence.class)
public class TrashEntryPersistenceImpl
	extends BasePersistenceImpl<TrashEntry> implements TrashEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TrashEntryUtil</code> to access the trash entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TrashEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public TrashEntryPersistenceImpl() {
		setModelClass(TrashEntry.class);

		setModelImplClass(TrashEntryImpl.class);
		setModelPKClass(long.class);

		setTable(TrashEntryTable.INSTANCE);
	}

	/**
	 * Caches the trash entry in the entity cache if it is enabled.
	 *
	 * @param trashEntry the trash entry
	 */
	@Override
	public void cacheResult(TrashEntry trashEntry) {
		entityCache.putResult(
			TrashEntryImpl.class, trashEntry.getPrimaryKey(), trashEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the trash entries in the entity cache if it is enabled.
	 *
	 * @param trashEntries the trash entries
	 */
	@Override
	public void cacheResult(List<TrashEntry> trashEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (trashEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (TrashEntry trashEntry : trashEntries) {
			if (entityCache.getResult(
					TrashEntryImpl.class, trashEntry.getPrimaryKey()) == null) {

				cacheResult(trashEntry);
			}
		}
	}

	/**
	 * Clears the cache for all trash entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TrashEntryImpl.class);

		finderCache.clearCache(TrashEntryImpl.class);
	}

	/**
	 * Clears the cache for the trash entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TrashEntry trashEntry) {
		entityCache.removeResult(TrashEntryImpl.class, trashEntry);
	}

	@Override
	public void clearCache(List<TrashEntry> trashEntries) {
		for (TrashEntry trashEntry : trashEntries) {
			entityCache.removeResult(TrashEntryImpl.class, trashEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(TrashEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(TrashEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	 *
	 * @param trashEntryId the primary key for the new trash entry
	 * @return the new trash entry
	 */
	@Override
	public TrashEntry create(long trashEntryId) {
		TrashEntry trashEntry = new TrashEntryImpl();

		trashEntry.setNew(true);
		trashEntry.setPrimaryKey(trashEntryId);

		trashEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return trashEntry;
	}

	/**
	 * Removes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry that was removed
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	@Override
	public TrashEntry remove(long trashEntryId)
		throws NoSuchTrashEntryException {

		return remove((Serializable)trashEntryId);
	}

	/**
	 * Removes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the trash entry
	 * @return the trash entry that was removed
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	@Override
	public TrashEntry remove(Serializable primaryKey)
		throws NoSuchTrashEntryException {

		Session session = null;

		try {
			session = openSession();

			TrashEntry trashEntry = (TrashEntry)session.get(
				TrashEntryImpl.class, primaryKey);

			if (trashEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTrashEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(trashEntry);
		}
		catch (NoSuchTrashEntryException noSuchEntityException) {
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
	protected TrashEntry removeImpl(TrashEntry trashEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(trashEntry)) {
				trashEntry = (TrashEntry)session.get(
					TrashEntryImpl.class, trashEntry.getPrimaryKeyObj());
			}

			if (trashEntry != null) {
				session.delete(trashEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (trashEntry != null) {
			clearCache(trashEntry);
		}

		return trashEntry;
	}

	@Override
	public TrashEntry updateImpl(TrashEntry trashEntry) {
		boolean isNew = trashEntry.isNew();

		if (!(trashEntry instanceof TrashEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(trashEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(trashEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in trashEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TrashEntry implementation " +
					trashEntry.getClass());
		}

		TrashEntryModelImpl trashEntryModelImpl =
			(TrashEntryModelImpl)trashEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (trashEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				trashEntry.setCreateDate(date);
			}
			else {
				trashEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!trashEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				trashEntry.setModifiedDate(date);
			}
			else {
				trashEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(trashEntry);
			}
			else {
				trashEntry = (TrashEntry)session.merge(trashEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(TrashEntryImpl.class, trashEntry, false, true);

		if (isNew) {
			trashEntry.setNew(false);
		}

		trashEntry.resetOriginalValues();

		return trashEntry;
	}

	/**
	 * Returns the trash entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the trash entry
	 * @return the trash entry
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	@Override
	public TrashEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTrashEntryException {

		TrashEntry trashEntry = fetchByPrimaryKey(primaryKey);

		if (trashEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTrashEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return trashEntry;
	}

	/**
	 * Returns the trash entry with the primary key or throws a <code>NoSuchTrashEntryException</code> if it could not be found.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	@Override
	public TrashEntry findByPrimaryKey(long trashEntryId)
		throws NoSuchTrashEntryException {

		return findByPrimaryKey((Serializable)trashEntryId);
	}

	/**
	 * Returns the trash entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry, or <code>null</code> if a trash entry with the primary key could not be found
	 */
	@Override
	public TrashEntry fetchByPrimaryKey(long trashEntryId) {
		return fetchByPrimaryKey((Serializable)trashEntryId);
	}

	/**
	 * Returns all the trash entries.
	 *
	 * @return the trash entries
	 */
	@Override
	public List<TrashEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @return the range of trash entries
	 */
	@Override
	public List<TrashEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of trash entries
	 */
	@Override
	public List<TrashEntry> findAll(
		int start, int end, OrderByComparator<TrashEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of trash entries
	 */
	@Override
	public List<TrashEntry> findAll(
		int start, int end, OrderByComparator<TrashEntry> orderByComparator,
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

		List<TrashEntry> list = null;

		if (useFinderCache) {
			list = (List<TrashEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TRASHENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TRASHENTRY;

				sql = sql.concat(TrashEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TrashEntry>)QueryUtil.list(
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
	 * Removes all the trash entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TrashEntry trashEntry : findAll()) {
			remove(trashEntry);
		}
	}

	/**
	 * Returns the number of trash entries.
	 *
	 * @return the number of trash entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TRASHENTRY);

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
		return "trashEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_TRASHENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TrashEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the trash entry persistence.
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

		TrashEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		TrashEntryUtil.setPersistence(null);

		entityCache.removeCache(TrashEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = SBCompat740PersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SBCompat740PersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SBCompat740PersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_TRASHENTRY =
		"SELECT trashEntry FROM TrashEntry trashEntry";

	private static final String _SQL_COUNT_TRASHENTRY =
		"SELECT COUNT(trashEntry) FROM TrashEntry trashEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "trashEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TrashEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		TrashEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1343372063