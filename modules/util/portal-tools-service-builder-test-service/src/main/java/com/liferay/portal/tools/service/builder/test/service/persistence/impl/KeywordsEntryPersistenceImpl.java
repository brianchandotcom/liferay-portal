/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchKeywordsEntryException;
import com.liferay.portal.tools.service.builder.test.model.KeywordsEntry;
import com.liferay.portal.tools.service.builder.test.model.KeywordsEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.KeywordsEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.KeywordsEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.IndexEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.KeywordsEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.KeywordsEntryUtil;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the keywords entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KeywordsEntryPersistenceImpl
	extends BasePersistenceImpl<KeywordsEntry>
	implements KeywordsEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KeywordsEntryUtil</code> to access the keywords entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KeywordsEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public KeywordsEntryPersistenceImpl() {
		setModelClass(KeywordsEntry.class);

		setModelImplClass(KeywordsEntryImpl.class);
		setModelPKClass(long.class);

		setTable(KeywordsEntryTable.INSTANCE);
	}

	/**
	 * Caches the keywords entry in the entity cache if it is enabled.
	 *
	 * @param keywordsEntry the keywords entry
	 */
	@Override
	public void cacheResult(KeywordsEntry keywordsEntry) {
		entityCache.putResult(
			KeywordsEntryImpl.class, keywordsEntry.getPrimaryKey(),
			keywordsEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the keywords entries in the entity cache if it is enabled.
	 *
	 * @param keywordsEntries the keywords entries
	 */
	@Override
	public void cacheResult(List<KeywordsEntry> keywordsEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (keywordsEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KeywordsEntry keywordsEntry : keywordsEntries) {
			if (entityCache.getResult(
					KeywordsEntryImpl.class, keywordsEntry.getPrimaryKey()) ==
						null) {

				cacheResult(keywordsEntry);
			}
		}
	}

	/**
	 * Clears the cache for all keywords entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KeywordsEntryImpl.class);

		finderCache.clearCache(KeywordsEntryImpl.class);
	}

	/**
	 * Clears the cache for the keywords entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KeywordsEntry keywordsEntry) {
		entityCache.removeResult(KeywordsEntryImpl.class, keywordsEntry);
	}

	@Override
	public void clearCache(List<KeywordsEntry> keywordsEntries) {
		for (KeywordsEntry keywordsEntry : keywordsEntries) {
			entityCache.removeResult(KeywordsEntryImpl.class, keywordsEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KeywordsEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(KeywordsEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new keywords entry with the primary key. Does not add the keywords entry to the database.
	 *
	 * @param keywordsEntryId the primary key for the new keywords entry
	 * @return the new keywords entry
	 */
	@Override
	public KeywordsEntry create(long keywordsEntryId) {
		KeywordsEntry keywordsEntry = new KeywordsEntryImpl();

		keywordsEntry.setNew(true);
		keywordsEntry.setPrimaryKey(keywordsEntryId);

		keywordsEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return keywordsEntry;
	}

	/**
	 * Removes the keywords entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry that was removed
	 * @throws NoSuchKeywordsEntryException if a keywords entry with the primary key could not be found
	 */
	@Override
	public KeywordsEntry remove(long keywordsEntryId)
		throws NoSuchKeywordsEntryException {

		return remove((Serializable)keywordsEntryId);
	}

	/**
	 * Removes the keywords entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the keywords entry
	 * @return the keywords entry that was removed
	 * @throws NoSuchKeywordsEntryException if a keywords entry with the primary key could not be found
	 */
	@Override
	public KeywordsEntry remove(Serializable primaryKey)
		throws NoSuchKeywordsEntryException {

		Session session = null;

		try {
			session = openSession();

			KeywordsEntry keywordsEntry = (KeywordsEntry)session.get(
				KeywordsEntryImpl.class, primaryKey);

			if (keywordsEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchKeywordsEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(keywordsEntry);
		}
		catch (NoSuchKeywordsEntryException noSuchEntityException) {
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
	protected KeywordsEntry removeImpl(KeywordsEntry keywordsEntry) {
		keywordsEntryToIndexEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			keywordsEntry.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(keywordsEntry)) {
				keywordsEntry = (KeywordsEntry)session.get(
					KeywordsEntryImpl.class, keywordsEntry.getPrimaryKeyObj());
			}

			if (keywordsEntry != null) {
				session.delete(keywordsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (keywordsEntry != null) {
			clearCache(keywordsEntry);
		}

		return keywordsEntry;
	}

	@Override
	public KeywordsEntry updateImpl(KeywordsEntry keywordsEntry) {
		boolean isNew = keywordsEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(keywordsEntry);
			}
			else {
				keywordsEntry = (KeywordsEntry)session.merge(keywordsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KeywordsEntryImpl.class, keywordsEntry, false, true);

		if (isNew) {
			keywordsEntry.setNew(false);
		}

		keywordsEntry.resetOriginalValues();

		return keywordsEntry;
	}

	/**
	 * Returns the keywords entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the keywords entry
	 * @return the keywords entry
	 * @throws NoSuchKeywordsEntryException if a keywords entry with the primary key could not be found
	 */
	@Override
	public KeywordsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchKeywordsEntryException {

		KeywordsEntry keywordsEntry = fetchByPrimaryKey(primaryKey);

		if (keywordsEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchKeywordsEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return keywordsEntry;
	}

	/**
	 * Returns the keywords entry with the primary key or throws a <code>NoSuchKeywordsEntryException</code> if it could not be found.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry
	 * @throws NoSuchKeywordsEntryException if a keywords entry with the primary key could not be found
	 */
	@Override
	public KeywordsEntry findByPrimaryKey(long keywordsEntryId)
		throws NoSuchKeywordsEntryException {

		return findByPrimaryKey((Serializable)keywordsEntryId);
	}

	/**
	 * Returns the keywords entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry, or <code>null</code> if a keywords entry with the primary key could not be found
	 */
	@Override
	public KeywordsEntry fetchByPrimaryKey(long keywordsEntryId) {
		return fetchByPrimaryKey((Serializable)keywordsEntryId);
	}

	/**
	 * Returns all the keywords entries.
	 *
	 * @return the keywords entries
	 */
	@Override
	public List<KeywordsEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the keywords entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @return the range of keywords entries
	 */
	@Override
	public List<KeywordsEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the keywords entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of keywords entries
	 */
	@Override
	public List<KeywordsEntry> findAll(
		int start, int end,
		OrderByComparator<KeywordsEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the keywords entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of keywords entries
	 */
	@Override
	public List<KeywordsEntry> findAll(
		int start, int end, OrderByComparator<KeywordsEntry> orderByComparator,
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

		List<KeywordsEntry> list = null;

		if (useFinderCache) {
			list = (List<KeywordsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KEYWORDSENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KEYWORDSENTRY;

				sql = sql.concat(KeywordsEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KeywordsEntry>)QueryUtil.list(
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
	 * Removes all the keywords entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KeywordsEntry keywordsEntry : findAll()) {
			remove(keywordsEntry);
		}
	}

	/**
	 * Returns the number of keywords entries.
	 *
	 * @return the number of keywords entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_KEYWORDSENTRY);

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

	/**
	 * Returns the primaryKeys of index entries associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @return long[] of the primaryKeys of index entries associated with the keywords entry
	 */
	@Override
	public long[] getIndexEntryPrimaryKeys(long pk) {
		long[] pks = keywordsEntryToIndexEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the index entries associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @return the index entries associated with the keywords entry
	 */
	@Override
	public List<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
		getIndexEntries(long pk) {

		return getIndexEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the index entries associated with the keywords entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the keywords entry
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @return the range of index entries associated with the keywords entry
	 */
	@Override
	public List<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
		getIndexEntries(long pk, int start, int end) {

		return getIndexEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries associated with the keywords entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the keywords entry
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of index entries associated with the keywords entry
	 */
	@Override
	public List<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
		getIndexEntries(
			long pk, int start, int end,
			OrderByComparator
				<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
					orderByComparator) {

		return keywordsEntryToIndexEntryTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of index entries associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @return the number of index entries associated with the keywords entry
	 */
	@Override
	public int getIndexEntriesSize(long pk) {
		long[] pks = keywordsEntryToIndexEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the index entry is associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPK the primary key of the index entry
	 * @return <code>true</code> if the index entry is associated with the keywords entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsIndexEntry(long pk, long indexEntryPK) {
		return keywordsEntryToIndexEntryTableMapper.containsTableMapping(
			pk, indexEntryPK);
	}

	/**
	 * Returns <code>true</code> if the keywords entry has any index entries associated with it.
	 *
	 * @param pk the primary key of the keywords entry to check for associations with index entries
	 * @return <code>true</code> if the keywords entry has any index entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsIndexEntries(long pk) {
		if (getIndexEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPK the primary key of the index entry
	 * @return <code>true</code> if an association between the keywords entry and the index entry was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addIndexEntry(long pk, long indexEntryPK) {
		KeywordsEntry keywordsEntry = fetchByPrimaryKey(pk);

		if (keywordsEntry == null) {
			return keywordsEntryToIndexEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, indexEntryPK);
		}
		else {
			return keywordsEntryToIndexEntryTableMapper.addTableMapping(
				keywordsEntry.getCompanyId(), pk, indexEntryPK);
		}
	}

	/**
	 * Adds an association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntry the index entry
	 * @return <code>true</code> if an association between the keywords entry and the index entry was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addIndexEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.IndexEntry
			indexEntry) {

		KeywordsEntry keywordsEntry = fetchByPrimaryKey(pk);

		if (keywordsEntry == null) {
			return keywordsEntryToIndexEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				indexEntry.getPrimaryKey());
		}
		else {
			return keywordsEntryToIndexEntryTableMapper.addTableMapping(
				keywordsEntry.getCompanyId(), pk, indexEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPKs the primary keys of the index entries
	 * @return <code>true</code> if at least one association between the keywords entry and the index entries was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addIndexEntries(long pk, long[] indexEntryPKs) {
		long companyId = 0;

		KeywordsEntry keywordsEntry = fetchByPrimaryKey(pk);

		if (keywordsEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = keywordsEntry.getCompanyId();
		}

		long[] addedKeys =
			keywordsEntryToIndexEntryTableMapper.addTableMappings(
				companyId, pk, indexEntryPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntries the index entries
	 * @return <code>true</code> if at least one association between the keywords entry and the index entries was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addIndexEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			indexEntries) {

		return addIndexEntries(
			pk,
			ListUtil.toLongArray(
				indexEntries,
				com.liferay.portal.tools.service.builder.test.model.IndexEntry.
					INDEX_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the keywords entry and its index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry to clear the associated index entries from
	 */
	@Override
	public void clearIndexEntries(long pk) {
		keywordsEntryToIndexEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPK the primary key of the index entry
	 */
	@Override
	public void removeIndexEntry(long pk, long indexEntryPK) {
		keywordsEntryToIndexEntryTableMapper.deleteTableMapping(
			pk, indexEntryPK);
	}

	/**
	 * Removes the association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntry the index entry
	 */
	@Override
	public void removeIndexEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.IndexEntry
			indexEntry) {

		keywordsEntryToIndexEntryTableMapper.deleteTableMapping(
			pk, indexEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPKs the primary keys of the index entries
	 */
	@Override
	public void removeIndexEntries(long pk, long[] indexEntryPKs) {
		keywordsEntryToIndexEntryTableMapper.deleteTableMappings(
			pk, indexEntryPKs);
	}

	/**
	 * Removes the association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntries the index entries
	 */
	@Override
	public void removeIndexEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			indexEntries) {

		removeIndexEntries(
			pk,
			ListUtil.toLongArray(
				indexEntries,
				com.liferay.portal.tools.service.builder.test.model.IndexEntry.
					INDEX_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the index entries associated with the keywords entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPKs the primary keys of the index entries to be associated with the keywords entry
	 */
	@Override
	public void setIndexEntries(long pk, long[] indexEntryPKs) {
		Set<Long> newIndexEntryPKsSet = SetUtil.fromArray(indexEntryPKs);
		Set<Long> oldIndexEntryPKsSet = SetUtil.fromArray(
			keywordsEntryToIndexEntryTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeIndexEntryPKsSet = new HashSet<Long>(
			oldIndexEntryPKsSet);

		removeIndexEntryPKsSet.removeAll(newIndexEntryPKsSet);

		keywordsEntryToIndexEntryTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeIndexEntryPKsSet));

		newIndexEntryPKsSet.removeAll(oldIndexEntryPKsSet);

		long companyId = 0;

		KeywordsEntry keywordsEntry = fetchByPrimaryKey(pk);

		if (keywordsEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = keywordsEntry.getCompanyId();
		}

		keywordsEntryToIndexEntryTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newIndexEntryPKsSet));
	}

	/**
	 * Sets the index entries associated with the keywords entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntries the index entries to be associated with the keywords entry
	 */
	@Override
	public void setIndexEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			indexEntries) {

		try {
			long[] indexEntryPKs = new long[indexEntries.size()];

			for (int i = 0; i < indexEntries.size(); i++) {
				com.liferay.portal.tools.service.builder.test.model.IndexEntry
					indexEntry = indexEntries.get(i);

				indexEntryPKs[i] = indexEntry.getPrimaryKey();
			}

			setIndexEntries(pk, indexEntryPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "keywordsEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KEYWORDSENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KeywordsEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the keywords entry persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		keywordsEntryToIndexEntryTableMapper =
			TableMapperFactory.getTableMapper(
				"IndexEntries_KeywordsEntries", "companyId", "keywordsEntryId",
				"indexEntryId", this, indexEntryPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		KeywordsEntryUtil.setPersistence(this);
	}

	public void destroy() {
		KeywordsEntryUtil.setPersistence(null);

		entityCache.removeCache(KeywordsEntryImpl.class.getName());

		TableMapperFactory.removeTableMapper("IndexEntries_KeywordsEntries");
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	@BeanReference(type = IndexEntryPersistence.class)
	protected IndexEntryPersistence indexEntryPersistence;

	protected TableMapper
		<KeywordsEntry,
		 com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			keywordsEntryToIndexEntryTableMapper;

	private static final String _SQL_SELECT_KEYWORDSENTRY =
		"SELECT keywordsEntry FROM KeywordsEntry keywordsEntry";

	private static final String _SQL_COUNT_KEYWORDSENTRY =
		"SELECT COUNT(keywordsEntry) FROM KeywordsEntry keywordsEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "keywordsEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KeywordsEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		KeywordsEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}