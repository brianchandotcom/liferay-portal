/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchPermissionPropagationEntryException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PermissionPropagationEntry;
import com.liferay.portal.kernel.model.PermissionPropagationEntryTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.PermissionPropagationEntryPersistence;
import com.liferay.portal.kernel.service.persistence.PermissionPropagationEntryUtil;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.impl.PermissionPropagationEntryImpl;
import com.liferay.portal.model.impl.PermissionPropagationEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the permission propagation entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PermissionPropagationEntryPersistenceImpl
	extends BasePersistenceImpl<PermissionPropagationEntry>
	implements PermissionPropagationEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PermissionPropagationEntryUtil</code> to access the permission propagation entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PermissionPropagationEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public PermissionPropagationEntryPersistenceImpl() {
		setModelClass(PermissionPropagationEntry.class);

		setModelImplClass(PermissionPropagationEntryImpl.class);
		setModelPKClass(long.class);

		setTable(PermissionPropagationEntryTable.INSTANCE);
	}

	/**
	 * Caches the permission propagation entry in the entity cache if it is enabled.
	 *
	 * @param permissionPropagationEntry the permission propagation entry
	 */
	@Override
	public void cacheResult(
		PermissionPropagationEntry permissionPropagationEntry) {

		if (permissionPropagationEntry.getCtCollectionId() != 0) {
			return;
		}

		EntityCacheUtil.putResult(
			PermissionPropagationEntryImpl.class,
			permissionPropagationEntry.getPrimaryKey(),
			permissionPropagationEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the permission propagation entries in the entity cache if it is enabled.
	 *
	 * @param permissionPropagationEntries the permission propagation entries
	 */
	@Override
	public void cacheResult(
		List<PermissionPropagationEntry> permissionPropagationEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (permissionPropagationEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PermissionPropagationEntry permissionPropagationEntry :
				permissionPropagationEntries) {

			if (permissionPropagationEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (EntityCacheUtil.getResult(
					PermissionPropagationEntryImpl.class,
					permissionPropagationEntry.getPrimaryKey()) == null) {

				cacheResult(permissionPropagationEntry);
			}
		}
	}

	/**
	 * Clears the cache for all permission propagation entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PermissionPropagationEntryImpl.class);

		FinderCacheUtil.clearCache(PermissionPropagationEntryImpl.class);
	}

	/**
	 * Clears the cache for the permission propagation entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		PermissionPropagationEntry permissionPropagationEntry) {

		EntityCacheUtil.removeResult(
			PermissionPropagationEntryImpl.class, permissionPropagationEntry);
	}

	@Override
	public void clearCache(
		List<PermissionPropagationEntry> permissionPropagationEntries) {

		for (PermissionPropagationEntry permissionPropagationEntry :
				permissionPropagationEntries) {

			EntityCacheUtil.removeResult(
				PermissionPropagationEntryImpl.class,
				permissionPropagationEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(PermissionPropagationEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				PermissionPropagationEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new permission propagation entry with the primary key. Does not add the permission propagation entry to the database.
	 *
	 * @param permissionPropagationEntryId the primary key for the new permission propagation entry
	 * @return the new permission propagation entry
	 */
	@Override
	public PermissionPropagationEntry create(
		long permissionPropagationEntryId) {

		PermissionPropagationEntry permissionPropagationEntry =
			new PermissionPropagationEntryImpl();

		permissionPropagationEntry.setNew(true);
		permissionPropagationEntry.setPrimaryKey(permissionPropagationEntryId);

		permissionPropagationEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return permissionPropagationEntry;
	}

	/**
	 * Removes the permission propagation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry that was removed
	 * @throws NoSuchPermissionPropagationEntryException if a permission propagation entry with the primary key could not be found
	 */
	@Override
	public PermissionPropagationEntry remove(long permissionPropagationEntryId)
		throws NoSuchPermissionPropagationEntryException {

		return remove((Serializable)permissionPropagationEntryId);
	}

	/**
	 * Removes the permission propagation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the permission propagation entry
	 * @return the permission propagation entry that was removed
	 * @throws NoSuchPermissionPropagationEntryException if a permission propagation entry with the primary key could not be found
	 */
	@Override
	public PermissionPropagationEntry remove(Serializable primaryKey)
		throws NoSuchPermissionPropagationEntryException {

		Session session = null;

		try {
			session = openSession();

			PermissionPropagationEntry permissionPropagationEntry =
				(PermissionPropagationEntry)session.get(
					PermissionPropagationEntryImpl.class, primaryKey);

			if (permissionPropagationEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPermissionPropagationEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(permissionPropagationEntry);
		}
		catch (NoSuchPermissionPropagationEntryException
					noSuchEntityException) {

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
	protected PermissionPropagationEntry removeImpl(
		PermissionPropagationEntry permissionPropagationEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(permissionPropagationEntry)) {
				permissionPropagationEntry =
					(PermissionPropagationEntry)session.get(
						PermissionPropagationEntryImpl.class,
						permissionPropagationEntry.getPrimaryKeyObj());
			}

			if ((permissionPropagationEntry != null) &&
				CTPersistenceHelperUtil.isRemove(permissionPropagationEntry)) {

				session.delete(permissionPropagationEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (permissionPropagationEntry != null) {
			clearCache(permissionPropagationEntry);
		}

		return permissionPropagationEntry;
	}

	@Override
	public PermissionPropagationEntry updateImpl(
		PermissionPropagationEntry permissionPropagationEntry) {

		boolean isNew = permissionPropagationEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(permissionPropagationEntry)) {
				if (!isNew) {
					session.evict(
						PermissionPropagationEntryImpl.class,
						permissionPropagationEntry.getPrimaryKeyObj());
				}

				session.save(permissionPropagationEntry);
			}
			else {
				permissionPropagationEntry =
					(PermissionPropagationEntry)session.merge(
						permissionPropagationEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (permissionPropagationEntry.getCtCollectionId() != 0) {
			if (isNew) {
				permissionPropagationEntry.setNew(false);
			}

			permissionPropagationEntry.resetOriginalValues();

			return permissionPropagationEntry;
		}

		EntityCacheUtil.putResult(
			PermissionPropagationEntryImpl.class, permissionPropagationEntry,
			false, true);

		if (isNew) {
			permissionPropagationEntry.setNew(false);
		}

		permissionPropagationEntry.resetOriginalValues();

		return permissionPropagationEntry;
	}

	/**
	 * Returns the permission propagation entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the permission propagation entry
	 * @return the permission propagation entry
	 * @throws NoSuchPermissionPropagationEntryException if a permission propagation entry with the primary key could not be found
	 */
	@Override
	public PermissionPropagationEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPermissionPropagationEntryException {

		PermissionPropagationEntry permissionPropagationEntry =
			fetchByPrimaryKey(primaryKey);

		if (permissionPropagationEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPermissionPropagationEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return permissionPropagationEntry;
	}

	/**
	 * Returns the permission propagation entry with the primary key or throws a <code>NoSuchPermissionPropagationEntryException</code> if it could not be found.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry
	 * @throws NoSuchPermissionPropagationEntryException if a permission propagation entry with the primary key could not be found
	 */
	@Override
	public PermissionPropagationEntry findByPrimaryKey(
			long permissionPropagationEntryId)
		throws NoSuchPermissionPropagationEntryException {

		return findByPrimaryKey((Serializable)permissionPropagationEntryId);
	}

	/**
	 * Returns the permission propagation entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the permission propagation entry
	 * @return the permission propagation entry, or <code>null</code> if a permission propagation entry with the primary key could not be found
	 */
	@Override
	public PermissionPropagationEntry fetchByPrimaryKey(
		Serializable primaryKey) {

		if (CTPersistenceHelperUtil.isProductionMode(
				PermissionPropagationEntry.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		PermissionPropagationEntry permissionPropagationEntry = null;

		Session session = null;

		try {
			session = openSession();

			permissionPropagationEntry =
				(PermissionPropagationEntry)session.get(
					PermissionPropagationEntryImpl.class, primaryKey);

			if (permissionPropagationEntry != null) {
				cacheResult(permissionPropagationEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return permissionPropagationEntry;
	}

	/**
	 * Returns the permission propagation entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry, or <code>null</code> if a permission propagation entry with the primary key could not be found
	 */
	@Override
	public PermissionPropagationEntry fetchByPrimaryKey(
		long permissionPropagationEntryId) {

		return fetchByPrimaryKey((Serializable)permissionPropagationEntryId);
	}

	@Override
	public Map<Serializable, PermissionPropagationEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				PermissionPropagationEntry.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PermissionPropagationEntry> map =
			new HashMap<Serializable, PermissionPropagationEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PermissionPropagationEntry permissionPropagationEntry =
				fetchByPrimaryKey(primaryKey);

			if (permissionPropagationEntry != null) {
				map.put(primaryKey, permissionPropagationEntry);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (PermissionPropagationEntry permissionPropagationEntry :
					(List<PermissionPropagationEntry>)query.list()) {

				map.put(
					permissionPropagationEntry.getPrimaryKeyObj(),
					permissionPropagationEntry);

				cacheResult(permissionPropagationEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the permission propagation entries.
	 *
	 * @return the permission propagation entries
	 */
	@Override
	public List<PermissionPropagationEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @return the range of permission propagation entries
	 */
	@Override
	public List<PermissionPropagationEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of permission propagation entries
	 */
	@Override
	public List<PermissionPropagationEntry> findAll(
		int start, int end,
		OrderByComparator<PermissionPropagationEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of permission propagation entries
	 */
	@Override
	public List<PermissionPropagationEntry> findAll(
		int start, int end,
		OrderByComparator<PermissionPropagationEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PermissionPropagationEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<PermissionPropagationEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<PermissionPropagationEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PERMISSIONPROPAGATIONENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PERMISSIONPROPAGATIONENTRY;

				sql = sql.concat(
					PermissionPropagationEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PermissionPropagationEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the permission propagation entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PermissionPropagationEntry permissionPropagationEntry :
				findAll()) {

			remove(permissionPropagationEntry);
		}
	}

	/**
	 * Returns the number of permission propagation entries.
	 *
	 * @return the number of permission propagation entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PermissionPropagationEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_PERMISSIONPROPAGATIONENTRY);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "permissionPropagationEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PERMISSIONPROPAGATIONENTRY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return PermissionPropagationEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "PermissionPropagationEntry";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("propagation");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("permissionPropagationEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the permission propagation entry persistence.
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

		PermissionPropagationEntryUtil.setPersistence(this);
	}

	public void destroy() {
		PermissionPropagationEntryUtil.setPersistence(null);

		EntityCacheUtil.removeCache(
			PermissionPropagationEntryImpl.class.getName());
	}

	private static final String _SQL_SELECT_PERMISSIONPROPAGATIONENTRY =
		"SELECT permissionPropagationEntry FROM PermissionPropagationEntry permissionPropagationEntry";

	private static final String _SQL_COUNT_PERMISSIONPROPAGATIONENTRY =
		"SELECT COUNT(permissionPropagationEntry) FROM PermissionPropagationEntry permissionPropagationEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"permissionPropagationEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PermissionPropagationEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		PermissionPropagationEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}