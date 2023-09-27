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
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchRenameFinderColumnEntryException;
import com.liferay.portal.tools.service.builder.test.model.RenameFinderColumnEntry;
import com.liferay.portal.tools.service.builder.test.model.RenameFinderColumnEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.RenameFinderColumnEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.RenameFinderColumnEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.RenameFinderColumnEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.RenameFinderColumnEntryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the rename finder column entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RenameFinderColumnEntryPersistenceImpl
	extends BasePersistenceImpl<RenameFinderColumnEntry>
	implements RenameFinderColumnEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RenameFinderColumnEntryUtil</code> to access the rename finder column entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RenameFinderColumnEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByColumnToRename;
	private FinderPath _finderPathCountByColumnToRename;

	/**
	 * Returns the rename finder column entry where columnToRename = &#63; or throws a <code>NoSuchRenameFinderColumnEntryException</code> if it could not be found.
	 *
	 * @param columnToRename the column to rename
	 * @return the matching rename finder column entry
	 * @throws NoSuchRenameFinderColumnEntryException if a matching rename finder column entry could not be found
	 */
	@Override
	public RenameFinderColumnEntry findByColumnToRename(String columnToRename)
		throws NoSuchRenameFinderColumnEntryException {

		RenameFinderColumnEntry renameFinderColumnEntry = fetchByColumnToRename(
			columnToRename);

		if (renameFinderColumnEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("columnToRename=");
			sb.append(columnToRename);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRenameFinderColumnEntryException(sb.toString());
		}

		return renameFinderColumnEntry;
	}

	/**
	 * Returns the rename finder column entry where columnToRename = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param columnToRename the column to rename
	 * @return the matching rename finder column entry, or <code>null</code> if a matching rename finder column entry could not be found
	 */
	@Override
	public RenameFinderColumnEntry fetchByColumnToRename(
		String columnToRename) {

		return fetchByColumnToRename(columnToRename, true);
	}

	/**
	 * Returns the rename finder column entry where columnToRename = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param columnToRename the column to rename
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching rename finder column entry, or <code>null</code> if a matching rename finder column entry could not be found
	 */
	@Override
	public RenameFinderColumnEntry fetchByColumnToRename(
		String columnToRename, boolean useFinderCache) {

		columnToRename = Objects.toString(columnToRename, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {columnToRename};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByColumnToRename, finderArgs, this);
		}

		if (result instanceof RenameFinderColumnEntry) {
			RenameFinderColumnEntry renameFinderColumnEntry =
				(RenameFinderColumnEntry)result;

			if (!Objects.equals(
					columnToRename,
					renameFinderColumnEntry.getColumnToRename())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_RENAMEFINDERCOLUMNENTRY_WHERE);

			boolean bindColumnToRename = false;

			if (columnToRename.isEmpty()) {
				sb.append(_FINDER_COLUMN_COLUMNTORENAME_COLUMNTORENAME_3);
			}
			else {
				bindColumnToRename = true;

				sb.append(_FINDER_COLUMN_COLUMNTORENAME_COLUMNTORENAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindColumnToRename) {
					queryPos.add(columnToRename);
				}

				List<RenameFinderColumnEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByColumnToRename, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {columnToRename};
							}

							_log.warn(
								"RenameFinderColumnEntryPersistenceImpl.fetchByColumnToRename(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					RenameFinderColumnEntry renameFinderColumnEntry = list.get(
						0);

					result = renameFinderColumnEntry;

					cacheResult(renameFinderColumnEntry);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (RenameFinderColumnEntry)result;
		}
	}

	/**
	 * Removes the rename finder column entry where columnToRename = &#63; from the database.
	 *
	 * @param columnToRename the column to rename
	 * @return the rename finder column entry that was removed
	 */
	@Override
	public RenameFinderColumnEntry removeByColumnToRename(String columnToRename)
		throws NoSuchRenameFinderColumnEntryException {

		RenameFinderColumnEntry renameFinderColumnEntry = findByColumnToRename(
			columnToRename);

		return remove(renameFinderColumnEntry);
	}

	/**
	 * Returns the number of rename finder column entries where columnToRename = &#63;.
	 *
	 * @param columnToRename the column to rename
	 * @return the number of matching rename finder column entries
	 */
	@Override
	public int countByColumnToRename(String columnToRename) {
		columnToRename = Objects.toString(columnToRename, "");

		FinderPath finderPath = _finderPathCountByColumnToRename;

		Object[] finderArgs = new Object[] {columnToRename};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_RENAMEFINDERCOLUMNENTRY_WHERE);

			boolean bindColumnToRename = false;

			if (columnToRename.isEmpty()) {
				sb.append(_FINDER_COLUMN_COLUMNTORENAME_COLUMNTORENAME_3);
			}
			else {
				bindColumnToRename = true;

				sb.append(_FINDER_COLUMN_COLUMNTORENAME_COLUMNTORENAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindColumnToRename) {
					queryPos.add(columnToRename);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_COLUMNTORENAME_COLUMNTORENAME_2 =
		"renameFinderColumnEntry.columnToRename = ?";

	private static final String _FINDER_COLUMN_COLUMNTORENAME_COLUMNTORENAME_3 =
		"(renameFinderColumnEntry.columnToRename IS NULL OR renameFinderColumnEntry.columnToRename = '')";

	public RenameFinderColumnEntryPersistenceImpl() {
		setModelClass(RenameFinderColumnEntry.class);

		setModelImplClass(RenameFinderColumnEntryImpl.class);
		setModelPKClass(long.class);

		setTable(RenameFinderColumnEntryTable.INSTANCE);
	}

	/**
	 * Caches the rename finder column entry in the entity cache if it is enabled.
	 *
	 * @param renameFinderColumnEntry the rename finder column entry
	 */
	@Override
	public void cacheResult(RenameFinderColumnEntry renameFinderColumnEntry) {
		entityCache.putResult(
			RenameFinderColumnEntryImpl.class,
			renameFinderColumnEntry.getPrimaryKey(), renameFinderColumnEntry);

		finderCache.putResult(
			_finderPathFetchByColumnToRename,
			new Object[] {renameFinderColumnEntry.getColumnToRename()},
			renameFinderColumnEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the rename finder column entries in the entity cache if it is enabled.
	 *
	 * @param renameFinderColumnEntries the rename finder column entries
	 */
	@Override
	public void cacheResult(
		List<RenameFinderColumnEntry> renameFinderColumnEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (renameFinderColumnEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RenameFinderColumnEntry renameFinderColumnEntry :
				renameFinderColumnEntries) {

			if (entityCache.getResult(
					RenameFinderColumnEntryImpl.class,
					renameFinderColumnEntry.getPrimaryKey()) == null) {

				cacheResult(renameFinderColumnEntry);
			}
		}
	}

	/**
	 * Clears the cache for all rename finder column entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RenameFinderColumnEntryImpl.class);

		finderCache.clearCache(RenameFinderColumnEntryImpl.class);
	}

	/**
	 * Clears the cache for the rename finder column entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RenameFinderColumnEntry renameFinderColumnEntry) {
		entityCache.removeResult(
			RenameFinderColumnEntryImpl.class, renameFinderColumnEntry);
	}

	@Override
	public void clearCache(
		List<RenameFinderColumnEntry> renameFinderColumnEntries) {

		for (RenameFinderColumnEntry renameFinderColumnEntry :
				renameFinderColumnEntries) {

			entityCache.removeResult(
				RenameFinderColumnEntryImpl.class, renameFinderColumnEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RenameFinderColumnEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				RenameFinderColumnEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RenameFinderColumnEntryModelImpl renameFinderColumnEntryModelImpl) {

		Object[] args = new Object[] {
			renameFinderColumnEntryModelImpl.getColumnToRename()
		};

		finderCache.putResult(
			_finderPathCountByColumnToRename, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByColumnToRename, args,
			renameFinderColumnEntryModelImpl);
	}

	/**
	 * Creates a new rename finder column entry with the primary key. Does not add the rename finder column entry to the database.
	 *
	 * @param renameFinderColumnEntryId the primary key for the new rename finder column entry
	 * @return the new rename finder column entry
	 */
	@Override
	public RenameFinderColumnEntry create(long renameFinderColumnEntryId) {
		RenameFinderColumnEntry renameFinderColumnEntry =
			new RenameFinderColumnEntryImpl();

		renameFinderColumnEntry.setNew(true);
		renameFinderColumnEntry.setPrimaryKey(renameFinderColumnEntryId);

		return renameFinderColumnEntry;
	}

	/**
	 * Removes the rename finder column entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry that was removed
	 * @throws NoSuchRenameFinderColumnEntryException if a rename finder column entry with the primary key could not be found
	 */
	@Override
	public RenameFinderColumnEntry remove(long renameFinderColumnEntryId)
		throws NoSuchRenameFinderColumnEntryException {

		return remove((Serializable)renameFinderColumnEntryId);
	}

	/**
	 * Removes the rename finder column entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the rename finder column entry
	 * @return the rename finder column entry that was removed
	 * @throws NoSuchRenameFinderColumnEntryException if a rename finder column entry with the primary key could not be found
	 */
	@Override
	public RenameFinderColumnEntry remove(Serializable primaryKey)
		throws NoSuchRenameFinderColumnEntryException {

		Session session = null;

		try {
			session = openSession();

			RenameFinderColumnEntry renameFinderColumnEntry =
				(RenameFinderColumnEntry)session.get(
					RenameFinderColumnEntryImpl.class, primaryKey);

			if (renameFinderColumnEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRenameFinderColumnEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(renameFinderColumnEntry);
		}
		catch (NoSuchRenameFinderColumnEntryException noSuchEntityException) {
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
	protected RenameFinderColumnEntry removeImpl(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(renameFinderColumnEntry)) {
				renameFinderColumnEntry = (RenameFinderColumnEntry)session.get(
					RenameFinderColumnEntryImpl.class,
					renameFinderColumnEntry.getPrimaryKeyObj());
			}

			if (renameFinderColumnEntry != null) {
				session.delete(renameFinderColumnEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (renameFinderColumnEntry != null) {
			clearCache(renameFinderColumnEntry);
		}

		return renameFinderColumnEntry;
	}

	@Override
	public RenameFinderColumnEntry updateImpl(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		boolean isNew = renameFinderColumnEntry.isNew();

		if (!(renameFinderColumnEntry instanceof
				RenameFinderColumnEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(renameFinderColumnEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					renameFinderColumnEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in renameFinderColumnEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RenameFinderColumnEntry implementation " +
					renameFinderColumnEntry.getClass());
		}

		RenameFinderColumnEntryModelImpl renameFinderColumnEntryModelImpl =
			(RenameFinderColumnEntryModelImpl)renameFinderColumnEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(renameFinderColumnEntry);
			}
			else {
				renameFinderColumnEntry =
					(RenameFinderColumnEntry)session.merge(
						renameFinderColumnEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RenameFinderColumnEntryImpl.class, renameFinderColumnEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(renameFinderColumnEntryModelImpl);

		if (isNew) {
			renameFinderColumnEntry.setNew(false);
		}

		renameFinderColumnEntry.resetOriginalValues();

		return renameFinderColumnEntry;
	}

	/**
	 * Returns the rename finder column entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the rename finder column entry
	 * @return the rename finder column entry
	 * @throws NoSuchRenameFinderColumnEntryException if a rename finder column entry with the primary key could not be found
	 */
	@Override
	public RenameFinderColumnEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRenameFinderColumnEntryException {

		RenameFinderColumnEntry renameFinderColumnEntry = fetchByPrimaryKey(
			primaryKey);

		if (renameFinderColumnEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRenameFinderColumnEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return renameFinderColumnEntry;
	}

	/**
	 * Returns the rename finder column entry with the primary key or throws a <code>NoSuchRenameFinderColumnEntryException</code> if it could not be found.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry
	 * @throws NoSuchRenameFinderColumnEntryException if a rename finder column entry with the primary key could not be found
	 */
	@Override
	public RenameFinderColumnEntry findByPrimaryKey(
			long renameFinderColumnEntryId)
		throws NoSuchRenameFinderColumnEntryException {

		return findByPrimaryKey((Serializable)renameFinderColumnEntryId);
	}

	/**
	 * Returns the rename finder column entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry, or <code>null</code> if a rename finder column entry with the primary key could not be found
	 */
	@Override
	public RenameFinderColumnEntry fetchByPrimaryKey(
		long renameFinderColumnEntryId) {

		return fetchByPrimaryKey((Serializable)renameFinderColumnEntryId);
	}

	/**
	 * Returns all the rename finder column entries.
	 *
	 * @return the rename finder column entries
	 */
	@Override
	public List<RenameFinderColumnEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @return the range of rename finder column entries
	 */
	@Override
	public List<RenameFinderColumnEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rename finder column entries
	 */
	@Override
	public List<RenameFinderColumnEntry> findAll(
		int start, int end,
		OrderByComparator<RenameFinderColumnEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rename finder column entries
	 */
	@Override
	public List<RenameFinderColumnEntry> findAll(
		int start, int end,
		OrderByComparator<RenameFinderColumnEntry> orderByComparator,
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

		List<RenameFinderColumnEntry> list = null;

		if (useFinderCache) {
			list = (List<RenameFinderColumnEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_RENAMEFINDERCOLUMNENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_RENAMEFINDERCOLUMNENTRY;

				sql = sql.concat(
					RenameFinderColumnEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RenameFinderColumnEntry>)QueryUtil.list(
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
	 * Removes all the rename finder column entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RenameFinderColumnEntry renameFinderColumnEntry : findAll()) {
			remove(renameFinderColumnEntry);
		}
	}

	/**
	 * Returns the number of rename finder column entries.
	 *
	 * @return the number of rename finder column entries
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
					_SQL_COUNT_RENAMEFINDERCOLUMNENTRY);

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
		return "renameFinderColumnEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RENAMEFINDERCOLUMNENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RenameFinderColumnEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the rename finder column entry persistence.
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

		_finderPathFetchByColumnToRename = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByColumnToRename",
			new String[] {String.class.getName()},
			new String[] {"columnToRename"}, true);

		_finderPathCountByColumnToRename = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByColumnToRename",
			new String[] {String.class.getName()},
			new String[] {"columnToRename"}, false);

		RenameFinderColumnEntryUtil.setPersistence(this);
	}

	public void destroy() {
		RenameFinderColumnEntryUtil.setPersistence(null);

		entityCache.removeCache(RenameFinderColumnEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_RENAMEFINDERCOLUMNENTRY =
		"SELECT renameFinderColumnEntry FROM RenameFinderColumnEntry renameFinderColumnEntry";

	private static final String _SQL_SELECT_RENAMEFINDERCOLUMNENTRY_WHERE =
		"SELECT renameFinderColumnEntry FROM RenameFinderColumnEntry renameFinderColumnEntry WHERE ";

	private static final String _SQL_COUNT_RENAMEFINDERCOLUMNENTRY =
		"SELECT COUNT(renameFinderColumnEntry) FROM RenameFinderColumnEntry renameFinderColumnEntry";

	private static final String _SQL_COUNT_RENAMEFINDERCOLUMNENTRY_WHERE =
		"SELECT COUNT(renameFinderColumnEntry) FROM RenameFinderColumnEntry renameFinderColumnEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"renameFinderColumnEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RenameFinderColumnEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RenameFinderColumnEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RenameFinderColumnEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}