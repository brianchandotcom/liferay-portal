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
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.NestedSetsTreeManager;
import com.liferay.portal.kernel.service.persistence.impl.PersistenceNestedSetsTreeManager;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchNestedSetsTreeEntryException;
import com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry;
import com.liferay.portal.tools.service.builder.test.model.impl.NestedSetsTreeEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.NestedSetsTreeEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.NestedSetsTreeEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the nested sets tree entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class NestedSetsTreeEntryPersistenceImpl
	extends BasePersistenceImpl<NestedSetsTreeEntry>
	implements NestedSetsTreeEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NestedSetsTreeEntryUtil</code> to access the nested sets tree entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NestedSetsTreeEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationCountAncestors;
	private FinderPath _finderPathWithPaginationCountDescendants;
	private FinderPath _finderPathWithPaginationGetAncestors;
	private FinderPath _finderPathWithPaginationGetDescendants;

	public NestedSetsTreeEntryPersistenceImpl() {
		setModelClass(NestedSetsTreeEntry.class);

		setModelImplClass(NestedSetsTreeEntryImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the nested sets tree entry in the entity cache if it is enabled.
	 *
	 * @param nestedSetsTreeEntry the nested sets tree entry
	 */
	@Override
	public void cacheResult(NestedSetsTreeEntry nestedSetsTreeEntry) {
		entityCache.putResult(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryImpl.class, nestedSetsTreeEntry.getPrimaryKey(),
			nestedSetsTreeEntry);

		nestedSetsTreeEntry.resetOriginalValues();
	}

	/**
	 * Caches the nested sets tree entries in the entity cache if it is enabled.
	 *
	 * @param nestedSetsTreeEntries the nested sets tree entries
	 */
	@Override
	public void cacheResult(List<NestedSetsTreeEntry> nestedSetsTreeEntries) {
		for (NestedSetsTreeEntry nestedSetsTreeEntry : nestedSetsTreeEntries) {
			if (entityCache.getResult(
					NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
					NestedSetsTreeEntryImpl.class,
					nestedSetsTreeEntry.getPrimaryKey()) == null) {

				cacheResult(nestedSetsTreeEntry);
			}
			else {
				nestedSetsTreeEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all nested sets tree entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NestedSetsTreeEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the nested sets tree entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(NestedSetsTreeEntry nestedSetsTreeEntry) {
		entityCache.removeResult(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryImpl.class, nestedSetsTreeEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<NestedSetsTreeEntry> nestedSetsTreeEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (NestedSetsTreeEntry nestedSetsTreeEntry : nestedSetsTreeEntries) {
			entityCache.removeResult(
				NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
				NestedSetsTreeEntryImpl.class,
				nestedSetsTreeEntry.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
				NestedSetsTreeEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new nested sets tree entry with the primary key. Does not add the nested sets tree entry to the database.
	 *
	 * @param nestedSetsTreeEntryId the primary key for the new nested sets tree entry
	 * @return the new nested sets tree entry
	 */
	@Override
	public NestedSetsTreeEntry create(long nestedSetsTreeEntryId) {
		NestedSetsTreeEntry nestedSetsTreeEntry = new NestedSetsTreeEntryImpl();

		nestedSetsTreeEntry.setNew(true);
		nestedSetsTreeEntry.setPrimaryKey(nestedSetsTreeEntryId);

		return nestedSetsTreeEntry;
	}

	/**
	 * Removes the nested sets tree entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry that was removed
	 * @throws NoSuchNestedSetsTreeEntryException if a nested sets tree entry with the primary key could not be found
	 */
	@Override
	public NestedSetsTreeEntry remove(long nestedSetsTreeEntryId)
		throws NoSuchNestedSetsTreeEntryException {

		return remove((Serializable)nestedSetsTreeEntryId);
	}

	/**
	 * Removes the nested sets tree entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the nested sets tree entry
	 * @return the nested sets tree entry that was removed
	 * @throws NoSuchNestedSetsTreeEntryException if a nested sets tree entry with the primary key could not be found
	 */
	@Override
	public NestedSetsTreeEntry remove(Serializable primaryKey)
		throws NoSuchNestedSetsTreeEntryException {

		Session session = null;

		try {
			session = openSession();

			NestedSetsTreeEntry nestedSetsTreeEntry =
				(NestedSetsTreeEntry)session.get(
					NestedSetsTreeEntryImpl.class, primaryKey);

			if (nestedSetsTreeEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNestedSetsTreeEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(nestedSetsTreeEntry);
		}
		catch (NoSuchNestedSetsTreeEntryException nsee) {
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
	protected NestedSetsTreeEntry removeImpl(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		Session session = null;

		try {
			session = openSession();

			if (rebuildTreeEnabled) {
				if (session.isDirty()) {
					session.flush();
				}

				nestedSetsTreeManager.delete(nestedSetsTreeEntry);

				clearCache();

				session.clear();
			}

			if (!session.contains(nestedSetsTreeEntry)) {
				nestedSetsTreeEntry = (NestedSetsTreeEntry)session.get(
					NestedSetsTreeEntryImpl.class,
					nestedSetsTreeEntry.getPrimaryKeyObj());
			}

			if (nestedSetsTreeEntry != null) {
				session.delete(nestedSetsTreeEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (nestedSetsTreeEntry != null) {
			clearCache(nestedSetsTreeEntry);
		}

		return nestedSetsTreeEntry;
	}

	@Override
	public NestedSetsTreeEntry updateImpl(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		boolean isNew = nestedSetsTreeEntry.isNew();

		if (!(nestedSetsTreeEntry instanceof NestedSetsTreeEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(nestedSetsTreeEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					nestedSetsTreeEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in nestedSetsTreeEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NestedSetsTreeEntry implementation " +
					nestedSetsTreeEntry.getClass());
		}

		NestedSetsTreeEntryModelImpl nestedSetsTreeEntryModelImpl =
			(NestedSetsTreeEntryModelImpl)nestedSetsTreeEntry;

		Session session = null;

		try {
			session = openSession();

			if (rebuildTreeEnabled) {
				if (session.isDirty()) {
					session.flush();
				}

				if (isNew) {
					nestedSetsTreeManager.insert(
						nestedSetsTreeEntry,
						fetchByPrimaryKey(
							nestedSetsTreeEntry.
								getParentNestedSetsTreeEntryId()));
				}
				else if (nestedSetsTreeEntry.getParentNestedSetsTreeEntryId() !=
							nestedSetsTreeEntryModelImpl.
								getOriginalParentNestedSetsTreeEntryId()) {

					nestedSetsTreeManager.move(
						nestedSetsTreeEntry,
						fetchByPrimaryKey(
							nestedSetsTreeEntryModelImpl.
								getOriginalParentNestedSetsTreeEntryId()),
						fetchByPrimaryKey(
							nestedSetsTreeEntry.
								getParentNestedSetsTreeEntryId()));
				}

				clearCache();

				session.clear();
			}

			if (nestedSetsTreeEntry.isNew()) {
				session.save(nestedSetsTreeEntry);

				nestedSetsTreeEntry.setNew(false);
			}
			else {
				nestedSetsTreeEntry = (NestedSetsTreeEntry)session.merge(
					nestedSetsTreeEntry);
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
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryImpl.class, nestedSetsTreeEntry.getPrimaryKey(),
			nestedSetsTreeEntry, false);

		nestedSetsTreeEntry.resetOriginalValues();

		return nestedSetsTreeEntry;
	}

	/**
	 * Returns the nested sets tree entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the nested sets tree entry
	 * @return the nested sets tree entry
	 * @throws NoSuchNestedSetsTreeEntryException if a nested sets tree entry with the primary key could not be found
	 */
	@Override
	public NestedSetsTreeEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchNestedSetsTreeEntryException {

		NestedSetsTreeEntry nestedSetsTreeEntry = fetchByPrimaryKey(primaryKey);

		if (nestedSetsTreeEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNestedSetsTreeEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return nestedSetsTreeEntry;
	}

	/**
	 * Returns the nested sets tree entry with the primary key or throws a <code>NoSuchNestedSetsTreeEntryException</code> if it could not be found.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry
	 * @throws NoSuchNestedSetsTreeEntryException if a nested sets tree entry with the primary key could not be found
	 */
	@Override
	public NestedSetsTreeEntry findByPrimaryKey(long nestedSetsTreeEntryId)
		throws NoSuchNestedSetsTreeEntryException {

		return findByPrimaryKey((Serializable)nestedSetsTreeEntryId);
	}

	/**
	 * Returns the nested sets tree entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry, or <code>null</code> if a nested sets tree entry with the primary key could not be found
	 */
	@Override
	public NestedSetsTreeEntry fetchByPrimaryKey(long nestedSetsTreeEntryId) {
		return fetchByPrimaryKey((Serializable)nestedSetsTreeEntryId);
	}

	/**
	 * Returns all the nested sets tree entries.
	 *
	 * @return the nested sets tree entries
	 */
	@Override
	public List<NestedSetsTreeEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the nested sets tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NestedSetsTreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of nested sets tree entries
	 * @param end the upper bound of the range of nested sets tree entries (not inclusive)
	 * @return the range of nested sets tree entries
	 */
	@Override
	public List<NestedSetsTreeEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the nested sets tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NestedSetsTreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of nested sets tree entries
	 * @param end the upper bound of the range of nested sets tree entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of nested sets tree entries
	 */
	@Override
	public List<NestedSetsTreeEntry> findAll(
		int start, int end,
		OrderByComparator<NestedSetsTreeEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the nested sets tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NestedSetsTreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of nested sets tree entries
	 * @param end the upper bound of the range of nested sets tree entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of nested sets tree entries
	 */
	@Override
	public List<NestedSetsTreeEntry> findAll(
		int start, int end,
		OrderByComparator<NestedSetsTreeEntry> orderByComparator,
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

		List<NestedSetsTreeEntry> list = null;

		if (useFinderCache) {
			list = (List<NestedSetsTreeEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_NESTEDSETSTREEENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_NESTEDSETSTREEENTRY;

				sql = sql.concat(NestedSetsTreeEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<NestedSetsTreeEntry>)QueryUtil.list(
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
	 * Removes all the nested sets tree entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NestedSetsTreeEntry nestedSetsTreeEntry : findAll()) {
			remove(nestedSetsTreeEntry);
		}
	}

	/**
	 * Returns the number of nested sets tree entries.
	 *
	 * @return the number of nested sets tree entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_NESTEDSETSTREEENTRY);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "nestedSetsTreeEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NESTEDSETSTREEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NestedSetsTreeEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public long countAncestors(NestedSetsTreeEntry nestedSetsTreeEntry) {
		Object[] finderArgs = new Object[] {
			nestedSetsTreeEntry.getGroupId(),
			nestedSetsTreeEntry.getLeftNestedSetsTreeEntryId(),
			nestedSetsTreeEntry.getRightNestedSetsTreeEntryId()
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountAncestors, finderArgs, this);

		if (count == null) {
			try {
				count = nestedSetsTreeManager.countAncestors(
					nestedSetsTreeEntry);

				finderCache.putResult(
					_finderPathWithPaginationCountAncestors, finderArgs, count);
			}
			catch (SystemException se) {
				finderCache.removeResult(
					_finderPathWithPaginationCountAncestors, finderArgs);

				throw se;
			}
		}

		return count.intValue();
	}

	@Override
	public long countDescendants(NestedSetsTreeEntry nestedSetsTreeEntry) {
		Object[] finderArgs = new Object[] {
			nestedSetsTreeEntry.getGroupId(),
			nestedSetsTreeEntry.getLeftNestedSetsTreeEntryId(),
			nestedSetsTreeEntry.getRightNestedSetsTreeEntryId()
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountDescendants, finderArgs, this);

		if (count == null) {
			try {
				count = nestedSetsTreeManager.countDescendants(
					nestedSetsTreeEntry);

				finderCache.putResult(
					_finderPathWithPaginationCountDescendants, finderArgs,
					count);
			}
			catch (SystemException se) {
				finderCache.removeResult(
					_finderPathWithPaginationCountDescendants, finderArgs);

				throw se;
			}
		}

		return count.intValue();
	}

	@Override
	public List<NestedSetsTreeEntry> getAncestors(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		Object[] finderArgs = new Object[] {
			nestedSetsTreeEntry.getGroupId(),
			nestedSetsTreeEntry.getLeftNestedSetsTreeEntryId(),
			nestedSetsTreeEntry.getRightNestedSetsTreeEntryId()
		};

		List<NestedSetsTreeEntry> list =
			(List<NestedSetsTreeEntry>)finderCache.getResult(
				_finderPathWithPaginationGetAncestors, finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (NestedSetsTreeEntry tempNestedSetsTreeEntry : list) {
				if ((nestedSetsTreeEntry.getLeftNestedSetsTreeEntryId() <
						tempNestedSetsTreeEntry.
							getLeftNestedSetsTreeEntryId()) ||
					(nestedSetsTreeEntry.getRightNestedSetsTreeEntryId() >
						tempNestedSetsTreeEntry.
							getRightNestedSetsTreeEntryId())) {

					list = null;

					break;
				}
			}
		}

		if (list == null) {
			try {
				list = nestedSetsTreeManager.getAncestors(nestedSetsTreeEntry);

				cacheResult(list);

				finderCache.putResult(
					_finderPathWithPaginationGetAncestors, finderArgs, list);
			}
			catch (SystemException se) {
				finderCache.removeResult(
					_finderPathWithPaginationGetAncestors, finderArgs);

				throw se;
			}
		}

		return list;
	}

	@Override
	public List<NestedSetsTreeEntry> getDescendants(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		Object[] finderArgs = new Object[] {
			nestedSetsTreeEntry.getGroupId(),
			nestedSetsTreeEntry.getLeftNestedSetsTreeEntryId(),
			nestedSetsTreeEntry.getRightNestedSetsTreeEntryId()
		};

		List<NestedSetsTreeEntry> list =
			(List<NestedSetsTreeEntry>)finderCache.getResult(
				_finderPathWithPaginationGetDescendants, finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (NestedSetsTreeEntry tempNestedSetsTreeEntry : list) {
				if ((nestedSetsTreeEntry.getLeftNestedSetsTreeEntryId() >
						tempNestedSetsTreeEntry.
							getLeftNestedSetsTreeEntryId()) ||
					(nestedSetsTreeEntry.getRightNestedSetsTreeEntryId() <
						tempNestedSetsTreeEntry.
							getRightNestedSetsTreeEntryId())) {

					list = null;

					break;
				}
			}
		}

		if (list == null) {
			try {
				list = nestedSetsTreeManager.getDescendants(
					nestedSetsTreeEntry);

				cacheResult(list);

				finderCache.putResult(
					_finderPathWithPaginationGetDescendants, finderArgs, list);
			}
			catch (SystemException se) {
				finderCache.removeResult(
					_finderPathWithPaginationGetDescendants, finderArgs);

				throw se;
			}
		}

		return list;
	}

	/**
	 * Rebuilds the nested sets tree entries tree for the scope using the modified pre-order tree traversal algorithm.
	 *
	 * <p>
	 * Only call this method if the tree has become stale through operations other than normal CRUD. Under normal circumstances the tree is automatically rebuilt whenver necessary.
	 * </p>
	 *
	 * @param groupId the ID of the scope
	 * @param force whether to force the rebuild even if the tree is not stale
	 */
	@Override
	public void rebuildTree(long groupId, boolean force) {
		if (!rebuildTreeEnabled) {
			return;
		}

		if (force || (countOrphanTreeNodes(groupId) > 0)) {
			Session session = null;

			try {
				session = openSession();

				if (session.isDirty()) {
					session.flush();
				}

				SQLQuery selectQuery = session.createSQLQuery(
					"SELECT nestedSetsTreeEntryId FROM NestedSetsTreeEntry WHERE groupId = ? AND parentNestedSetsTreeEntryId = ? ORDER BY nestedSetsTreeEntryId ASC");

				selectQuery.addScalar(
					"nestedSetsTreeEntryId",
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				SQLQuery updateQuery = session.createSQLQuery(
					"UPDATE NestedSetsTreeEntry SET leftNestedSetsTreeEntryId = ?, rightNestedSetsTreeEntryId = ? WHERE nestedSetsTreeEntryId = ?");

				rebuildTree(session, selectQuery, updateQuery, groupId, 0, 0);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}

			clearCache();
		}
	}

	@Override
	public void setRebuildTreeEnabled(boolean rebuildTreeEnabled) {
		this.rebuildTreeEnabled = rebuildTreeEnabled;
	}

	protected long countOrphanTreeNodes(long groupId) {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
				"SELECT COUNT(*) AS COUNT_VALUE FROM NestedSetsTreeEntry WHERE groupId = ? AND (leftNestedSetsTreeEntryId = 0 OR leftNestedSetsTreeEntryId IS NULL OR rightNestedSetsTreeEntryId = 0 OR rightNestedSetsTreeEntryId IS NULL)");

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected long rebuildTree(
		Session session, SQLQuery selectQuery, SQLQuery updateQuery,
		long groupId, long parentNestedSetsTreeEntryId,
		long leftNestedSetsTreeEntryId) {

		long rightNestedSetsTreeEntryId = leftNestedSetsTreeEntryId + 1;

		QueryPos qPos = QueryPos.getInstance(selectQuery);

		qPos.add(groupId);
		qPos.add(parentNestedSetsTreeEntryId);

		List<Long> nestedSetsTreeEntryIds = selectQuery.list();

		for (long nestedSetsTreeEntryId : nestedSetsTreeEntryIds) {
			rightNestedSetsTreeEntryId = rebuildTree(
				session, selectQuery, updateQuery, groupId,
				nestedSetsTreeEntryId, rightNestedSetsTreeEntryId);
		}

		if (parentNestedSetsTreeEntryId > 0) {
			qPos = QueryPos.getInstance(updateQuery);

			qPos.add(leftNestedSetsTreeEntryId);
			qPos.add(rightNestedSetsTreeEntryId);
			qPos.add(parentNestedSetsTreeEntryId);

			updateQuery.executeUpdate();
		}

		return rightNestedSetsTreeEntryId + 1;
	}

	/**
	 * Initializes the nested sets tree entry persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryModelImpl.FINDER_CACHE_ENABLED,
			NestedSetsTreeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryModelImpl.FINDER_CACHE_ENABLED,
			NestedSetsTreeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationCountAncestors = new FinderPath(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countAncestors",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationCountDescendants = new FinderPath(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countDescendants",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationGetAncestors = new FinderPath(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryModelImpl.FINDER_CACHE_ENABLED,
			NestedSetsTreeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "getAncestors",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationGetDescendants = new FinderPath(
			NestedSetsTreeEntryModelImpl.ENTITY_CACHE_ENABLED,
			NestedSetsTreeEntryModelImpl.FINDER_CACHE_ENABLED,
			NestedSetsTreeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "getDescendants",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	public void destroy() {
		entityCache.removeCache(NestedSetsTreeEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	protected NestedSetsTreeManager<NestedSetsTreeEntry> nestedSetsTreeManager =
		new PersistenceNestedSetsTreeManager<NestedSetsTreeEntry>(
			this, "NestedSetsTreeEntry", "NestedSetsTreeEntry",
			NestedSetsTreeEntryImpl.class, "nestedSetsTreeEntryId", "groupId",
			"leftNestedSetsTreeEntryId", "rightNestedSetsTreeEntryId");
	protected boolean rebuildTreeEnabled = true;

	private static final String _SQL_SELECT_NESTEDSETSTREEENTRY =
		"SELECT nestedSetsTreeEntry FROM NestedSetsTreeEntry nestedSetsTreeEntry";

	private static final String _SQL_COUNT_NESTEDSETSTREEENTRY =
		"SELECT COUNT(nestedSetsTreeEntry) FROM NestedSetsTreeEntry nestedSetsTreeEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "nestedSetsTreeEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NestedSetsTreeEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		NestedSetsTreeEntryPersistenceImpl.class);

}