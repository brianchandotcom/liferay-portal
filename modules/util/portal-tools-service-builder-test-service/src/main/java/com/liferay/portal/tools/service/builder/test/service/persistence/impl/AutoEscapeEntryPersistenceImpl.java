/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchAutoEscapeEntryException;
import com.liferay.portal.tools.service.builder.test.model.AutoEscapeEntry;
import com.liferay.portal.tools.service.builder.test.model.AutoEscapeEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.AutoEscapeEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.AutoEscapeEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.AutoEscapeEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.AutoEscapeEntryUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the auto escape entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AutoEscapeEntryPersistenceImpl
	extends BasePersistenceImpl<AutoEscapeEntry>
	implements AutoEscapeEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AutoEscapeEntryUtil</code> to access the auto escape entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AutoEscapeEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public AutoEscapeEntryPersistenceImpl() {
		setModelClass(AutoEscapeEntry.class);

		setModelImplClass(AutoEscapeEntryImpl.class);
		setModelPKClass(long.class);

		setTable(AutoEscapeEntryTable.INSTANCE);
	}

	/**
	 * Caches the auto escape entry in the entity cache if it is enabled.
	 *
	 * @param autoEscapeEntry the auto escape entry
	 */
	@Override
	public void cacheResult(AutoEscapeEntry autoEscapeEntry) {
		entityCache.putResult(
			AutoEscapeEntryImpl.class, autoEscapeEntry.getPrimaryKey(),
			autoEscapeEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the auto escape entries in the entity cache if it is enabled.
	 *
	 * @param autoEscapeEntries the auto escape entries
	 */
	@Override
	public void cacheResult(List<AutoEscapeEntry> autoEscapeEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (autoEscapeEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AutoEscapeEntry autoEscapeEntry : autoEscapeEntries) {
			if (entityCache.getResult(
					AutoEscapeEntryImpl.class,
					autoEscapeEntry.getPrimaryKey()) == null) {

				cacheResult(autoEscapeEntry);
			}
		}
	}

	/**
	 * Clears the cache for all auto escape entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AutoEscapeEntryImpl.class);

		finderCache.clearCache(AutoEscapeEntryImpl.class);
	}

	/**
	 * Clears the cache for the auto escape entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AutoEscapeEntry autoEscapeEntry) {
		entityCache.removeResult(AutoEscapeEntryImpl.class, autoEscapeEntry);
	}

	@Override
	public void clearCache(List<AutoEscapeEntry> autoEscapeEntries) {
		for (AutoEscapeEntry autoEscapeEntry : autoEscapeEntries) {
			entityCache.removeResult(
				AutoEscapeEntryImpl.class, autoEscapeEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AutoEscapeEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AutoEscapeEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new auto escape entry with the primary key. Does not add the auto escape entry to the database.
	 *
	 * @param autoEscapeEntryId the primary key for the new auto escape entry
	 * @return the new auto escape entry
	 */
	@Override
	public AutoEscapeEntry create(long autoEscapeEntryId) {
		AutoEscapeEntry autoEscapeEntry = new AutoEscapeEntryImpl();

		autoEscapeEntry.setNew(true);
		autoEscapeEntry.setPrimaryKey(autoEscapeEntryId);

		return autoEscapeEntry;
	}

	/**
	 * Removes the auto escape entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry that was removed
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	@Override
	public AutoEscapeEntry remove(long autoEscapeEntryId)
		throws NoSuchAutoEscapeEntryException {

		return remove((Serializable)autoEscapeEntryId);
	}

	/**
	 * Removes the auto escape entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the auto escape entry
	 * @return the auto escape entry that was removed
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	@Override
	public AutoEscapeEntry remove(Serializable primaryKey)
		throws NoSuchAutoEscapeEntryException {

		Session session = null;

		try {
			session = openSession();

			AutoEscapeEntry autoEscapeEntry = (AutoEscapeEntry)session.get(
				AutoEscapeEntryImpl.class, primaryKey);

			if (autoEscapeEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAutoEscapeEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(autoEscapeEntry);
		}
		catch (NoSuchAutoEscapeEntryException noSuchEntityException) {
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
	protected AutoEscapeEntry removeImpl(AutoEscapeEntry autoEscapeEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(autoEscapeEntry)) {
				autoEscapeEntry = (AutoEscapeEntry)session.get(
					AutoEscapeEntryImpl.class,
					autoEscapeEntry.getPrimaryKeyObj());
			}

			if (autoEscapeEntry != null) {
				session.delete(autoEscapeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (autoEscapeEntry != null) {
			clearCache(autoEscapeEntry);
		}

		return autoEscapeEntry;
	}

	@Override
	public AutoEscapeEntry updateImpl(AutoEscapeEntry autoEscapeEntry) {
		boolean isNew = autoEscapeEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(autoEscapeEntry);
			}
			else {
				autoEscapeEntry = (AutoEscapeEntry)session.merge(
					autoEscapeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AutoEscapeEntryImpl.class, autoEscapeEntry, false, true);

		if (isNew) {
			autoEscapeEntry.setNew(false);
		}

		autoEscapeEntry.resetOriginalValues();

		return autoEscapeEntry;
	}

	/**
	 * Returns the auto escape entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the auto escape entry
	 * @return the auto escape entry
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	@Override
	public AutoEscapeEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAutoEscapeEntryException {

		AutoEscapeEntry autoEscapeEntry = fetchByPrimaryKey(primaryKey);

		if (autoEscapeEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAutoEscapeEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return autoEscapeEntry;
	}

	/**
	 * Returns the auto escape entry with the primary key or throws a <code>NoSuchAutoEscapeEntryException</code> if it could not be found.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	@Override
	public AutoEscapeEntry findByPrimaryKey(long autoEscapeEntryId)
		throws NoSuchAutoEscapeEntryException {

		return findByPrimaryKey((Serializable)autoEscapeEntryId);
	}

	/**
	 * Returns the auto escape entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry, or <code>null</code> if a auto escape entry with the primary key could not be found
	 */
	@Override
	public AutoEscapeEntry fetchByPrimaryKey(long autoEscapeEntryId) {
		return fetchByPrimaryKey((Serializable)autoEscapeEntryId);
	}

	/**
	 * Returns all the auto escape entries.
	 *
	 * @return the auto escape entries
	 */
	@Override
	public List<AutoEscapeEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the auto escape entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AutoEscapeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of auto escape entries
	 * @param end the upper bound of the range of auto escape entries (not inclusive)
	 * @return the range of auto escape entries
	 */
	@Override
	public List<AutoEscapeEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the auto escape entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AutoEscapeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of auto escape entries
	 * @param end the upper bound of the range of auto escape entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of auto escape entries
	 */
	@Override
	public List<AutoEscapeEntry> findAll(
		int start, int end,
		OrderByComparator<AutoEscapeEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the auto escape entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AutoEscapeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of auto escape entries
	 * @param end the upper bound of the range of auto escape entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of auto escape entries
	 */
	@Override
	public List<AutoEscapeEntry> findAll(
		int start, int end,
		OrderByComparator<AutoEscapeEntry> orderByComparator,
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

		List<AutoEscapeEntry> list = null;

		if (useFinderCache) {
			list = (List<AutoEscapeEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_AUTOESCAPEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_AUTOESCAPEENTRY;

				sql = sql.concat(AutoEscapeEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AutoEscapeEntry>)QueryUtil.list(
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
	 * Removes all the auto escape entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AutoEscapeEntry autoEscapeEntry : findAll()) {
			remove(autoEscapeEntry);
		}
	}

	/**
	 * Returns the number of auto escape entries.
	 *
	 * @return the number of auto escape entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_AUTOESCAPEENTRY);

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
		return "autoEscapeEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_AUTOESCAPEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AutoEscapeEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the auto escape entry persistence.
	 */
	public void afterPropertiesSet() {
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

		AutoEscapeEntryUtil.setPersistence(this);
	}

	public void destroy() {
		AutoEscapeEntryUtil.setPersistence(null);

		entityCache.removeCache(AutoEscapeEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_AUTOESCAPEENTRY =
		"SELECT autoEscapeEntry FROM AutoEscapeEntry autoEscapeEntry";

	private static final String _SQL_COUNT_AUTOESCAPEENTRY =
		"SELECT COUNT(autoEscapeEntry) FROM AutoEscapeEntry autoEscapeEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "autoEscapeEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AutoEscapeEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		AutoEscapeEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}