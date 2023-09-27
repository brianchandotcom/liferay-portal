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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchRedundantIndexEntryException;
import com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry;
import com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.RedundantIndexEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.RedundantIndexEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.RedundantIndexEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.RedundantIndexEntryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the redundant index entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RedundantIndexEntryPersistenceImpl
	extends BasePersistenceImpl<RedundantIndexEntry>
	implements RedundantIndexEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RedundantIndexEntryUtil</code> to access the redundant index entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RedundantIndexEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the redundant index entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchRedundantIndexEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching redundant index entry
	 * @throws NoSuchRedundantIndexEntryException if a matching redundant index entry could not be found
	 */
	@Override
	public RedundantIndexEntry findByC_N(long companyId, String name)
		throws NoSuchRedundantIndexEntryException {

		RedundantIndexEntry redundantIndexEntry = fetchByC_N(companyId, name);

		if (redundantIndexEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRedundantIndexEntryException(sb.toString());
		}

		return redundantIndexEntry;
	}

	/**
	 * Returns the redundant index entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching redundant index entry, or <code>null</code> if a matching redundant index entry could not be found
	 */
	@Override
	public RedundantIndexEntry fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the redundant index entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redundant index entry, or <code>null</code> if a matching redundant index entry could not be found
	 */
	@Override
	public RedundantIndexEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_N, finderArgs, this);
		}

		if (result instanceof RedundantIndexEntry) {
			RedundantIndexEntry redundantIndexEntry =
				(RedundantIndexEntry)result;

			if ((companyId != redundantIndexEntry.getCompanyId()) ||
				!Objects.equals(name, redundantIndexEntry.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_REDUNDANTINDEXENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				List<RedundantIndexEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					RedundantIndexEntry redundantIndexEntry = list.get(0);

					result = redundantIndexEntry;

					cacheResult(redundantIndexEntry);
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
			return (RedundantIndexEntry)result;
		}
	}

	/**
	 * Removes the redundant index entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the redundant index entry that was removed
	 */
	@Override
	public RedundantIndexEntry removeByC_N(long companyId, String name)
		throws NoSuchRedundantIndexEntryException {

		RedundantIndexEntry redundantIndexEntry = findByC_N(companyId, name);

		return remove(redundantIndexEntry);
	}

	/**
	 * Returns the number of redundant index entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching redundant index entries
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REDUNDANTINDEXENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"redundantIndexEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"redundantIndexEntry.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(redundantIndexEntry.name IS NULL OR redundantIndexEntry.name = '')";

	public RedundantIndexEntryPersistenceImpl() {
		setModelClass(RedundantIndexEntry.class);

		setModelImplClass(RedundantIndexEntryImpl.class);
		setModelPKClass(long.class);

		setTable(RedundantIndexEntryTable.INSTANCE);
	}

	/**
	 * Caches the redundant index entry in the entity cache if it is enabled.
	 *
	 * @param redundantIndexEntry the redundant index entry
	 */
	@Override
	public void cacheResult(RedundantIndexEntry redundantIndexEntry) {
		entityCache.putResult(
			RedundantIndexEntryImpl.class, redundantIndexEntry.getPrimaryKey(),
			redundantIndexEntry);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				redundantIndexEntry.getCompanyId(),
				redundantIndexEntry.getName()
			},
			redundantIndexEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the redundant index entries in the entity cache if it is enabled.
	 *
	 * @param redundantIndexEntries the redundant index entries
	 */
	@Override
	public void cacheResult(List<RedundantIndexEntry> redundantIndexEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (redundantIndexEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RedundantIndexEntry redundantIndexEntry : redundantIndexEntries) {
			if (entityCache.getResult(
					RedundantIndexEntryImpl.class,
					redundantIndexEntry.getPrimaryKey()) == null) {

				cacheResult(redundantIndexEntry);
			}
		}
	}

	/**
	 * Clears the cache for all redundant index entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RedundantIndexEntryImpl.class);

		finderCache.clearCache(RedundantIndexEntryImpl.class);
	}

	/**
	 * Clears the cache for the redundant index entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RedundantIndexEntry redundantIndexEntry) {
		entityCache.removeResult(
			RedundantIndexEntryImpl.class, redundantIndexEntry);
	}

	@Override
	public void clearCache(List<RedundantIndexEntry> redundantIndexEntries) {
		for (RedundantIndexEntry redundantIndexEntry : redundantIndexEntries) {
			entityCache.removeResult(
				RedundantIndexEntryImpl.class, redundantIndexEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RedundantIndexEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(RedundantIndexEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RedundantIndexEntryModelImpl redundantIndexEntryModelImpl) {

		Object[] args = new Object[] {
			redundantIndexEntryModelImpl.getCompanyId(),
			redundantIndexEntryModelImpl.getName()
		};

		finderCache.putResult(_finderPathCountByC_N, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_N, args, redundantIndexEntryModelImpl);
	}

	/**
	 * Creates a new redundant index entry with the primary key. Does not add the redundant index entry to the database.
	 *
	 * @param redundantIndexEntryId the primary key for the new redundant index entry
	 * @return the new redundant index entry
	 */
	@Override
	public RedundantIndexEntry create(long redundantIndexEntryId) {
		RedundantIndexEntry redundantIndexEntry = new RedundantIndexEntryImpl();

		redundantIndexEntry.setNew(true);
		redundantIndexEntry.setPrimaryKey(redundantIndexEntryId);

		redundantIndexEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return redundantIndexEntry;
	}

	/**
	 * Removes the redundant index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry that was removed
	 * @throws NoSuchRedundantIndexEntryException if a redundant index entry with the primary key could not be found
	 */
	@Override
	public RedundantIndexEntry remove(long redundantIndexEntryId)
		throws NoSuchRedundantIndexEntryException {

		return remove((Serializable)redundantIndexEntryId);
	}

	/**
	 * Removes the redundant index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the redundant index entry
	 * @return the redundant index entry that was removed
	 * @throws NoSuchRedundantIndexEntryException if a redundant index entry with the primary key could not be found
	 */
	@Override
	public RedundantIndexEntry remove(Serializable primaryKey)
		throws NoSuchRedundantIndexEntryException {

		Session session = null;

		try {
			session = openSession();

			RedundantIndexEntry redundantIndexEntry =
				(RedundantIndexEntry)session.get(
					RedundantIndexEntryImpl.class, primaryKey);

			if (redundantIndexEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRedundantIndexEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(redundantIndexEntry);
		}
		catch (NoSuchRedundantIndexEntryException noSuchEntityException) {
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
	protected RedundantIndexEntry removeImpl(
		RedundantIndexEntry redundantIndexEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(redundantIndexEntry)) {
				redundantIndexEntry = (RedundantIndexEntry)session.get(
					RedundantIndexEntryImpl.class,
					redundantIndexEntry.getPrimaryKeyObj());
			}

			if (redundantIndexEntry != null) {
				session.delete(redundantIndexEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (redundantIndexEntry != null) {
			clearCache(redundantIndexEntry);
		}

		return redundantIndexEntry;
	}

	@Override
	public RedundantIndexEntry updateImpl(
		RedundantIndexEntry redundantIndexEntry) {

		boolean isNew = redundantIndexEntry.isNew();

		if (!(redundantIndexEntry instanceof RedundantIndexEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(redundantIndexEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					redundantIndexEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in redundantIndexEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RedundantIndexEntry implementation " +
					redundantIndexEntry.getClass());
		}

		RedundantIndexEntryModelImpl redundantIndexEntryModelImpl =
			(RedundantIndexEntryModelImpl)redundantIndexEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(redundantIndexEntry);
			}
			else {
				redundantIndexEntry = (RedundantIndexEntry)session.merge(
					redundantIndexEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RedundantIndexEntryImpl.class, redundantIndexEntryModelImpl, false,
			true);

		cacheUniqueFindersCache(redundantIndexEntryModelImpl);

		if (isNew) {
			redundantIndexEntry.setNew(false);
		}

		redundantIndexEntry.resetOriginalValues();

		return redundantIndexEntry;
	}

	/**
	 * Returns the redundant index entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the redundant index entry
	 * @return the redundant index entry
	 * @throws NoSuchRedundantIndexEntryException if a redundant index entry with the primary key could not be found
	 */
	@Override
	public RedundantIndexEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRedundantIndexEntryException {

		RedundantIndexEntry redundantIndexEntry = fetchByPrimaryKey(primaryKey);

		if (redundantIndexEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRedundantIndexEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return redundantIndexEntry;
	}

	/**
	 * Returns the redundant index entry with the primary key or throws a <code>NoSuchRedundantIndexEntryException</code> if it could not be found.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry
	 * @throws NoSuchRedundantIndexEntryException if a redundant index entry with the primary key could not be found
	 */
	@Override
	public RedundantIndexEntry findByPrimaryKey(long redundantIndexEntryId)
		throws NoSuchRedundantIndexEntryException {

		return findByPrimaryKey((Serializable)redundantIndexEntryId);
	}

	/**
	 * Returns the redundant index entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry, or <code>null</code> if a redundant index entry with the primary key could not be found
	 */
	@Override
	public RedundantIndexEntry fetchByPrimaryKey(long redundantIndexEntryId) {
		return fetchByPrimaryKey((Serializable)redundantIndexEntryId);
	}

	/**
	 * Returns all the redundant index entries.
	 *
	 * @return the redundant index entries
	 */
	@Override
	public List<RedundantIndexEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @return the range of redundant index entries
	 */
	@Override
	public List<RedundantIndexEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of redundant index entries
	 */
	@Override
	public List<RedundantIndexEntry> findAll(
		int start, int end,
		OrderByComparator<RedundantIndexEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of redundant index entries
	 */
	@Override
	public List<RedundantIndexEntry> findAll(
		int start, int end,
		OrderByComparator<RedundantIndexEntry> orderByComparator,
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

		List<RedundantIndexEntry> list = null;

		if (useFinderCache) {
			list = (List<RedundantIndexEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REDUNDANTINDEXENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REDUNDANTINDEXENTRY;

				sql = sql.concat(RedundantIndexEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RedundantIndexEntry>)QueryUtil.list(
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
	 * Removes all the redundant index entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RedundantIndexEntry redundantIndexEntry : findAll()) {
			remove(redundantIndexEntry);
		}
	}

	/**
	 * Returns the number of redundant index entries.
	 *
	 * @return the number of redundant index entries
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
					_SQL_COUNT_REDUNDANTINDEXENTRY);

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
		return "redundantIndexEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REDUNDANTINDEXENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RedundantIndexEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the redundant index entry persistence.
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

		_finderPathFetchByC_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		_finderPathCountByC_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, false);

		RedundantIndexEntryUtil.setPersistence(this);
	}

	public void destroy() {
		RedundantIndexEntryUtil.setPersistence(null);

		entityCache.removeCache(RedundantIndexEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REDUNDANTINDEXENTRY =
		"SELECT redundantIndexEntry FROM RedundantIndexEntry redundantIndexEntry";

	private static final String _SQL_SELECT_REDUNDANTINDEXENTRY_WHERE =
		"SELECT redundantIndexEntry FROM RedundantIndexEntry redundantIndexEntry WHERE ";

	private static final String _SQL_COUNT_REDUNDANTINDEXENTRY =
		"SELECT COUNT(redundantIndexEntry) FROM RedundantIndexEntry redundantIndexEntry";

	private static final String _SQL_COUNT_REDUNDANTINDEXENTRY_WHERE =
		"SELECT COUNT(redundantIndexEntry) FROM RedundantIndexEntry redundantIndexEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "redundantIndexEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RedundantIndexEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RedundantIndexEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RedundantIndexEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}