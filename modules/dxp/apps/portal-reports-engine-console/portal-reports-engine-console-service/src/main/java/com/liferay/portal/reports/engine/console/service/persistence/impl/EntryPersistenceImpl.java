/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.reports.engine.console.exception.NoSuchEntryException;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.model.EntryTable;
import com.liferay.portal.reports.engine.console.model.impl.EntryImpl;
import com.liferay.portal.reports.engine.console.model.impl.EntryModelImpl;
import com.liferay.portal.reports.engine.console.service.persistence.EntryPersistence;
import com.liferay.portal.reports.engine.console.service.persistence.impl.constants.ReportsPersistenceConstants;

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
 * The persistence implementation for the entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {EntryPersistence.class, BasePersistence.class})
public class EntryPersistenceImpl
	extends BasePersistenceImpl<Entry> implements EntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>EntryUtil</code> to access the entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		EntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public EntryPersistenceImpl() {
		setModelClass(Entry.class);

		setModelImplClass(EntryImpl.class);
		setModelPKClass(long.class);

		setTable(EntryTable.INSTANCE);
	}

	/**
	 * Caches the entry in the entity cache if it is enabled.
	 *
	 * @param entry the entry
	 */
	@Override
	public void cacheResult(Entry entry) {
		entityCache.putResult(EntryImpl.class, entry.getPrimaryKey(), entry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the entries in the entity cache if it is enabled.
	 *
	 * @param entries the entries
	 */
	@Override
	public void cacheResult(List<Entry> entries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (entries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Entry entry : entries) {
			if (entityCache.getResult(EntryImpl.class, entry.getPrimaryKey()) ==
					null) {

				cacheResult(entry);
			}
		}
	}

	/**
	 * Clears the cache for all entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(EntryImpl.class);

		finderCache.clearCache(EntryImpl.class);
	}

	/**
	 * Clears the cache for the entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Entry entry) {
		entityCache.removeResult(EntryImpl.class, entry);
	}

	@Override
	public void clearCache(List<Entry> entries) {
		for (Entry entry : entries) {
			entityCache.removeResult(EntryImpl.class, entry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(EntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(EntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new entry with the primary key. Does not add the entry to the database.
	 *
	 * @param entryId the primary key for the new entry
	 * @return the new entry
	 */
	@Override
	public Entry create(long entryId) {
		Entry entry = new EntryImpl();

		entry.setNew(true);
		entry.setPrimaryKey(entryId);

		entry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return entry;
	}

	/**
	 * Removes the entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the entry
	 * @return the entry that was removed
	 * @throws NoSuchEntryException if a entry with the primary key could not be found
	 */
	@Override
	public Entry remove(long entryId) throws NoSuchEntryException {
		return remove((Serializable)entryId);
	}

	/**
	 * Removes the entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the entry
	 * @return the entry that was removed
	 * @throws NoSuchEntryException if a entry with the primary key could not be found
	 */
	@Override
	public Entry remove(Serializable primaryKey) throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			Entry entry = (Entry)session.get(EntryImpl.class, primaryKey);

			if (entry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(entry);
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
	protected Entry removeImpl(Entry entry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(entry)) {
				entry = (Entry)session.get(
					EntryImpl.class, entry.getPrimaryKeyObj());
			}

			if (entry != null) {
				session.delete(entry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (entry != null) {
			clearCache(entry);
		}

		return entry;
	}

	@Override
	public Entry updateImpl(Entry entry) {
		boolean isNew = entry.isNew();

		if (!(entry instanceof EntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(entry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(entry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in entry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Entry implementation " +
					entry.getClass());
		}

		EntryModelImpl entryModelImpl = (EntryModelImpl)entry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (entry.getCreateDate() == null)) {
			if (serviceContext == null) {
				entry.setCreateDate(date);
			}
			else {
				entry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!entryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				entry.setModifiedDate(date);
			}
			else {
				entry.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(entry);
			}
			else {
				entry = (Entry)session.merge(entry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(EntryImpl.class, entry, false, true);

		if (isNew) {
			entry.setNew(false);
		}

		entry.resetOriginalValues();

		return entry;
	}

	/**
	 * Returns the entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the entry
	 * @return the entry
	 * @throws NoSuchEntryException if a entry with the primary key could not be found
	 */
	@Override
	public Entry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		Entry entry = fetchByPrimaryKey(primaryKey);

		if (entry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return entry;
	}

	/**
	 * Returns the entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the entry
	 * @return the entry
	 * @throws NoSuchEntryException if a entry with the primary key could not be found
	 */
	@Override
	public Entry findByPrimaryKey(long entryId) throws NoSuchEntryException {
		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the entry
	 * @return the entry, or <code>null</code> if a entry with the primary key could not be found
	 */
	@Override
	public Entry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns all the entries.
	 *
	 * @return the entries
	 */
	@Override
	public List<Entry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of entries
	 * @param end the upper bound of the range of entries (not inclusive)
	 * @return the range of entries
	 */
	@Override
	public List<Entry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of entries
	 * @param end the upper bound of the range of entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of entries
	 */
	@Override
	public List<Entry> findAll(
		int start, int end, OrderByComparator<Entry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of entries
	 * @param end the upper bound of the range of entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of entries
	 */
	@Override
	public List<Entry> findAll(
		int start, int end, OrderByComparator<Entry> orderByComparator,
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

		List<Entry> list = null;

		if (useFinderCache) {
			list = (List<Entry>)finderCache.getResult(finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ENTRY;

				sql = sql.concat(EntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Entry>)QueryUtil.list(
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
	 * Removes all the entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Entry entry : findAll()) {
			remove(entry);
		}
	}

	/**
	 * Returns the number of entries.
	 *
	 * @return the number of entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ENTRY);

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
		return _SQL_SELECT_ENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return EntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the entry persistence.
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
		entityCache.removeCache(EntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = ReportsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ReportsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ReportsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ENTRY =
		"SELECT entry FROM Entry entry";

	private static final String _SQL_COUNT_ENTRY =
		"SELECT COUNT(entry) FROM Entry entry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "entry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Entry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		EntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private EntryModelArgumentsResolver _entryModelArgumentsResolver;

}