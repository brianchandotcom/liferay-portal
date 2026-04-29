/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchNullConvertibleEntryException;
import com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry;
import com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.NullConvertibleEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.NullConvertibleEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.NullConvertibleEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.NullConvertibleEntryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;

/**
 * The persistence implementation for the null convertible entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class NullConvertibleEntryPersistenceImpl
	extends BasePersistenceImpl
		<NullConvertibleEntry, NoSuchNullConvertibleEntryException>
	implements NullConvertibleEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NullConvertibleEntryUtil</code> to access the null convertible entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NullConvertibleEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByName;
	private UniquePersistenceFinder<NullConvertibleEntry>
		_uniquePersistenceFinderByName;

	/**
	 * Returns the null convertible entry where name = &#63; or throws a <code>NoSuchNullConvertibleEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching null convertible entry
	 * @throws NoSuchNullConvertibleEntryException if a matching null convertible entry could not be found
	 */
	@Override
	public NullConvertibleEntry findByName(String name)
		throws NoSuchNullConvertibleEntryException {

		NullConvertibleEntry nullConvertibleEntry = fetchByName(name);

		if (nullConvertibleEntry == null) {
			String message =
				_uniquePersistenceFinderByName.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY, new Object[] {name});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchNullConvertibleEntryException(message);
		}

		return nullConvertibleEntry;
	}

	/**
	 * Returns the null convertible entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching null convertible entry, or <code>null</code> if a matching null convertible entry could not be found
	 */
	@Override
	public NullConvertibleEntry fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the null convertible entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching null convertible entry, or <code>null</code> if a matching null convertible entry could not be found
	 */
	@Override
	public NullConvertibleEntry fetchByName(
		String name, boolean useFinderCache) {

		return _uniquePersistenceFinderByName.fetch(
			dummyFinderCache, new Object[] {name}, useFinderCache);
	}

	/**
	 * Removes the null convertible entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the null convertible entry that was removed
	 */
	@Override
	public NullConvertibleEntry removeByName(String name)
		throws NoSuchNullConvertibleEntryException {

		NullConvertibleEntry nullConvertibleEntry = findByName(name);

		return remove(nullConvertibleEntry);
	}

	/**
	 * Returns the number of null convertible entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching null convertible entries
	 */
	@Override
	public int countByName(String name) {
		return _uniquePersistenceFinderByName.count(
			dummyFinderCache, new Object[] {name});
	}

	public NullConvertibleEntryPersistenceImpl() {
		setModelClass(NullConvertibleEntry.class);

		setModelImplClass(NullConvertibleEntryImpl.class);
		setModelPKClass(long.class);

		setTable(NullConvertibleEntryTable.INSTANCE);
	}

	/**
	 * Caches the null convertible entry in the entity cache if it is enabled.
	 *
	 * @param nullConvertibleEntry the null convertible entry
	 */
	@Override
	public void cacheResult(NullConvertibleEntry nullConvertibleEntry) {
		dummyEntityCache.putResult(
			NullConvertibleEntryImpl.class,
			nullConvertibleEntry.getPrimaryKey(), nullConvertibleEntry);

		dummyFinderCache.putResult(
			_finderPathFetchByName,
			new Object[] {nullConvertibleEntry.getName()},
			nullConvertibleEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the null convertible entries in the entity cache if it is enabled.
	 *
	 * @param nullConvertibleEntries the null convertible entries
	 */
	@Override
	public void cacheResult(List<NullConvertibleEntry> nullConvertibleEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (nullConvertibleEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NullConvertibleEntry nullConvertibleEntry :
				nullConvertibleEntries) {

			if (dummyEntityCache.getResult(
					NullConvertibleEntryImpl.class,
					nullConvertibleEntry.getPrimaryKey()) == null) {

				cacheResult(nullConvertibleEntry);
			}
		}
	}

	protected void cacheUniqueFindersCache(
		NullConvertibleEntryModelImpl nullConvertibleEntryModelImpl) {

		Object[] args = new Object[] {nullConvertibleEntryModelImpl.getName()};

		dummyFinderCache.putResult(
			_finderPathFetchByName, args, nullConvertibleEntryModelImpl);
	}

	/**
	 * Creates a new null convertible entry with the primary key. Does not add the null convertible entry to the database.
	 *
	 * @param nullConvertibleEntryId the primary key for the new null convertible entry
	 * @return the new null convertible entry
	 */
	@Override
	public NullConvertibleEntry create(long nullConvertibleEntryId) {
		NullConvertibleEntry nullConvertibleEntry =
			new NullConvertibleEntryImpl();

		nullConvertibleEntry.setNew(true);
		nullConvertibleEntry.setPrimaryKey(nullConvertibleEntryId);

		return nullConvertibleEntry;
	}

	/**
	 * Removes the null convertible entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry that was removed
	 * @throws NoSuchNullConvertibleEntryException if a null convertible entry with the primary key could not be found
	 */
	@Override
	public NullConvertibleEntry remove(long nullConvertibleEntryId)
		throws NoSuchNullConvertibleEntryException {

		return remove((Serializable)nullConvertibleEntryId);
	}

	@Override
	protected NullConvertibleEntry removeImpl(
		NullConvertibleEntry nullConvertibleEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(nullConvertibleEntry)) {
				nullConvertibleEntry = (NullConvertibleEntry)session.get(
					NullConvertibleEntryImpl.class,
					nullConvertibleEntry.getPrimaryKeyObj());
			}

			if (nullConvertibleEntry != null) {
				session.delete(nullConvertibleEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (nullConvertibleEntry != null) {
			clearCache(nullConvertibleEntry);
		}

		return nullConvertibleEntry;
	}

	@Override
	public NullConvertibleEntry updateImpl(
		NullConvertibleEntry nullConvertibleEntry) {

		boolean isNew = nullConvertibleEntry.isNew();

		if (!(nullConvertibleEntry instanceof NullConvertibleEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(nullConvertibleEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					nullConvertibleEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in nullConvertibleEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NullConvertibleEntry implementation " +
					nullConvertibleEntry.getClass());
		}

		NullConvertibleEntryModelImpl nullConvertibleEntryModelImpl =
			(NullConvertibleEntryModelImpl)nullConvertibleEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(nullConvertibleEntry);
			}
			else {
				nullConvertibleEntry = (NullConvertibleEntry)session.merge(
					nullConvertibleEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		dummyEntityCache.putResult(
			NullConvertibleEntryImpl.class, nullConvertibleEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(nullConvertibleEntryModelImpl);

		if (isNew) {
			nullConvertibleEntry.setNew(false);
		}

		nullConvertibleEntry.resetOriginalValues();

		return nullConvertibleEntry;
	}

	/**
	 * Returns the null convertible entry with the primary key or throws a <code>NoSuchNullConvertibleEntryException</code> if it could not be found.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry
	 * @throws NoSuchNullConvertibleEntryException if a null convertible entry with the primary key could not be found
	 */
	@Override
	public NullConvertibleEntry findByPrimaryKey(long nullConvertibleEntryId)
		throws NoSuchNullConvertibleEntryException {

		return findByPrimaryKey((Serializable)nullConvertibleEntryId);
	}

	/**
	 * Returns the null convertible entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry, or <code>null</code> if a null convertible entry with the primary key could not be found
	 */
	@Override
	public NullConvertibleEntry fetchByPrimaryKey(long nullConvertibleEntryId) {
		return fetchByPrimaryKey((Serializable)nullConvertibleEntryId);
	}

	/**
	 * Returns all the null convertible entries.
	 *
	 * @return the null convertible entries
	 */
	@Override
	public List<NullConvertibleEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @return the range of null convertible entries
	 */
	@Override
	public List<NullConvertibleEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of null convertible entries
	 */
	@Override
	public List<NullConvertibleEntry> findAll(
		int start, int end,
		OrderByComparator<NullConvertibleEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of null convertible entries
	 */
	@Override
	public List<NullConvertibleEntry> findAll(
		int start, int end,
		OrderByComparator<NullConvertibleEntry> orderByComparator,
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

		List<NullConvertibleEntry> list = null;

		if (useFinderCache) {
			list = (List<NullConvertibleEntry>)dummyFinderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NULLCONVERTIBLEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NULLCONVERTIBLEENTRY;

				sql = sql.concat(NullConvertibleEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<NullConvertibleEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					dummyFinderCache.putResult(finderPath, finderArgs, list);
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
	 * Removes all the null convertible entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NullConvertibleEntry nullConvertibleEntry : findAll()) {
			remove(nullConvertibleEntry);
		}
	}

	/**
	 * Returns the number of null convertible entries.
	 *
	 * @return the number of null convertible entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)dummyFinderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_NULLCONVERTIBLEENTRY);

				count = (Long)query.uniqueResult();

				dummyFinderCache.putResult(
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
		return dummyEntityCache;
	}

	@Override
	protected String getPKDBName() {
		return "nullConvertibleEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NULLCONVERTIBLEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NullConvertibleEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the null convertible entry persistence.
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

		_finderPathFetchByName = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByName",
			new String[] {String.class.getName()}, new String[] {"name"}, true);

		_uniquePersistenceFinderByName = new UniquePersistenceFinder<>(
			this, _finderPathFetchByName,
			_SQL_SELECT_NULLCONVERTIBLEENTRY_WHERE,
			new FinderColumn<>(
				"nullConvertibleEntry.", "name", FinderColumn.Type.STRING, "=",
				true, true, NullConvertibleEntry::getName));

		NullConvertibleEntryUtil.setPersistence(this);
	}

	public void destroy() {
		NullConvertibleEntryUtil.setPersistence(null);

		dummyEntityCache.removeCache(NullConvertibleEntryImpl.class.getName());
	}

	private static final String _SQL_SELECT_NULLCONVERTIBLEENTRY =
		"SELECT nullConvertibleEntry FROM NullConvertibleEntry nullConvertibleEntry";

	private static final String _SQL_SELECT_NULLCONVERTIBLEENTRY_WHERE =
		"SELECT nullConvertibleEntry FROM NullConvertibleEntry nullConvertibleEntry WHERE ";

	private static final String _SQL_COUNT_NULLCONVERTIBLEENTRY =
		"SELECT COUNT(nullConvertibleEntry) FROM NullConvertibleEntry nullConvertibleEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"nullConvertibleEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No NullConvertibleEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NullConvertibleEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return dummyFinderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1555649827