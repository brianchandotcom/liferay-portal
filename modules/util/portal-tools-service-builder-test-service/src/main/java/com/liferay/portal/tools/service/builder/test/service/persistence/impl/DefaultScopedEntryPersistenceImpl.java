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
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDefaultScopedEntryException;
import com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry;
import com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.DefaultScopedEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.DefaultScopedEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.DefaultScopedEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the default scoped entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DefaultScopedEntryPersistenceImpl
	extends BasePersistenceImpl<DefaultScopedEntry>
	implements DefaultScopedEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DefaultScopedEntryUtil</code> to access the default scoped entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DefaultScopedEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchDefaultScopedEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching default scoped entry
	 * @throws NoSuchDefaultScopedEntryException if a matching default scoped entry could not be found
	 */
	@Override
	public DefaultScopedEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchDefaultScopedEntryException {

		DefaultScopedEntry defaultScopedEntry = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (defaultScopedEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDefaultScopedEntryException(sb.toString());
		}

		return defaultScopedEntry;
	}

	/**
	 * Returns the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching default scoped entry, or <code>null</code> if a matching default scoped entry could not be found
	 */
	@Override
	public DefaultScopedEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching default scoped entry, or <code>null</code> if a matching default scoped entry could not be found
	 */
	@Override
	public DefaultScopedEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_ERC, finderArgs);
		}

		if (result instanceof DefaultScopedEntry) {
			DefaultScopedEntry defaultScopedEntry = (DefaultScopedEntry)result;

			if ((companyId != defaultScopedEntry.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					defaultScopedEntry.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DEFAULTSCOPEDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<DefaultScopedEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"DefaultScopedEntryPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					DefaultScopedEntry defaultScopedEntry = list.get(0);

					result = defaultScopedEntry;

					cacheResult(defaultScopedEntry);
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
			return (DefaultScopedEntry)result;
		}
	}

	/**
	 * Removes the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the default scoped entry that was removed
	 */
	@Override
	public DefaultScopedEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchDefaultScopedEntryException {

		DefaultScopedEntry defaultScopedEntry = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(defaultScopedEntry);
	}

	/**
	 * Returns the number of default scoped entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching default scoped entries
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DEFAULTSCOPEDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"defaultScopedEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"defaultScopedEntry.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(defaultScopedEntry.externalReferenceCode IS NULL OR defaultScopedEntry.externalReferenceCode = '')";

	public DefaultScopedEntryPersistenceImpl() {
		setModelClass(DefaultScopedEntry.class);

		setModelImplClass(DefaultScopedEntryImpl.class);
		setModelPKClass(long.class);

		setTable(DefaultScopedEntryTable.INSTANCE);
	}

	/**
	 * Caches the default scoped entry in the entity cache if it is enabled.
	 *
	 * @param defaultScopedEntry the default scoped entry
	 */
	@Override
	public void cacheResult(DefaultScopedEntry defaultScopedEntry) {
		entityCache.putResult(
			DefaultScopedEntryImpl.class, defaultScopedEntry.getPrimaryKey(),
			defaultScopedEntry);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				defaultScopedEntry.getCompanyId(),
				defaultScopedEntry.getExternalReferenceCode()
			},
			defaultScopedEntry);
	}

	/**
	 * Caches the default scoped entries in the entity cache if it is enabled.
	 *
	 * @param defaultScopedEntries the default scoped entries
	 */
	@Override
	public void cacheResult(List<DefaultScopedEntry> defaultScopedEntries) {
		for (DefaultScopedEntry defaultScopedEntry : defaultScopedEntries) {
			if (entityCache.getResult(
					DefaultScopedEntryImpl.class,
					defaultScopedEntry.getPrimaryKey()) == null) {

				cacheResult(defaultScopedEntry);
			}
		}
	}

	/**
	 * Clears the cache for all default scoped entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DefaultScopedEntryImpl.class);

		finderCache.clearCache(DefaultScopedEntryImpl.class);
	}

	/**
	 * Clears the cache for the default scoped entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DefaultScopedEntry defaultScopedEntry) {
		entityCache.removeResult(
			DefaultScopedEntryImpl.class, defaultScopedEntry);
	}

	@Override
	public void clearCache(List<DefaultScopedEntry> defaultScopedEntries) {
		for (DefaultScopedEntry defaultScopedEntry : defaultScopedEntries) {
			entityCache.removeResult(
				DefaultScopedEntryImpl.class, defaultScopedEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DefaultScopedEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DefaultScopedEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DefaultScopedEntryModelImpl defaultScopedEntryModelImpl) {

		Object[] args = new Object[] {
			defaultScopedEntryModelImpl.getCompanyId(),
			defaultScopedEntryModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, defaultScopedEntryModelImpl);
	}

	/**
	 * Creates a new default scoped entry with the primary key. Does not add the default scoped entry to the database.
	 *
	 * @param DefaultScopedEntryId the primary key for the new default scoped entry
	 * @return the new default scoped entry
	 */
	@Override
	public DefaultScopedEntry create(long DefaultScopedEntryId) {
		DefaultScopedEntry defaultScopedEntry = new DefaultScopedEntryImpl();

		defaultScopedEntry.setNew(true);
		defaultScopedEntry.setPrimaryKey(DefaultScopedEntryId);

		defaultScopedEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return defaultScopedEntry;
	}

	/**
	 * Removes the default scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry that was removed
	 * @throws NoSuchDefaultScopedEntryException if a default scoped entry with the primary key could not be found
	 */
	@Override
	public DefaultScopedEntry remove(long DefaultScopedEntryId)
		throws NoSuchDefaultScopedEntryException {

		return remove((Serializable)DefaultScopedEntryId);
	}

	/**
	 * Removes the default scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the default scoped entry
	 * @return the default scoped entry that was removed
	 * @throws NoSuchDefaultScopedEntryException if a default scoped entry with the primary key could not be found
	 */
	@Override
	public DefaultScopedEntry remove(Serializable primaryKey)
		throws NoSuchDefaultScopedEntryException {

		Session session = null;

		try {
			session = openSession();

			DefaultScopedEntry defaultScopedEntry =
				(DefaultScopedEntry)session.get(
					DefaultScopedEntryImpl.class, primaryKey);

			if (defaultScopedEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDefaultScopedEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(defaultScopedEntry);
		}
		catch (NoSuchDefaultScopedEntryException noSuchEntityException) {
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
	protected DefaultScopedEntry removeImpl(
		DefaultScopedEntry defaultScopedEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(defaultScopedEntry)) {
				defaultScopedEntry = (DefaultScopedEntry)session.get(
					DefaultScopedEntryImpl.class,
					defaultScopedEntry.getPrimaryKeyObj());
			}

			if (defaultScopedEntry != null) {
				session.delete(defaultScopedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (defaultScopedEntry != null) {
			clearCache(defaultScopedEntry);
		}

		return defaultScopedEntry;
	}

	@Override
	public DefaultScopedEntry updateImpl(
		DefaultScopedEntry defaultScopedEntry) {

		boolean isNew = defaultScopedEntry.isNew();

		if (!(defaultScopedEntry instanceof DefaultScopedEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(defaultScopedEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					defaultScopedEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in defaultScopedEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DefaultScopedEntry implementation " +
					defaultScopedEntry.getClass());
		}

		DefaultScopedEntryModelImpl defaultScopedEntryModelImpl =
			(DefaultScopedEntryModelImpl)defaultScopedEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(defaultScopedEntry);
			}
			else {
				defaultScopedEntry = (DefaultScopedEntry)session.merge(
					defaultScopedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DefaultScopedEntryImpl.class, defaultScopedEntryModelImpl, false,
			true);

		cacheUniqueFindersCache(defaultScopedEntryModelImpl);

		if (isNew) {
			defaultScopedEntry.setNew(false);
		}

		defaultScopedEntry.resetOriginalValues();

		return defaultScopedEntry;
	}

	/**
	 * Returns the default scoped entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the default scoped entry
	 * @return the default scoped entry
	 * @throws NoSuchDefaultScopedEntryException if a default scoped entry with the primary key could not be found
	 */
	@Override
	public DefaultScopedEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDefaultScopedEntryException {

		DefaultScopedEntry defaultScopedEntry = fetchByPrimaryKey(primaryKey);

		if (defaultScopedEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDefaultScopedEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return defaultScopedEntry;
	}

	/**
	 * Returns the default scoped entry with the primary key or throws a <code>NoSuchDefaultScopedEntryException</code> if it could not be found.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry
	 * @throws NoSuchDefaultScopedEntryException if a default scoped entry with the primary key could not be found
	 */
	@Override
	public DefaultScopedEntry findByPrimaryKey(long DefaultScopedEntryId)
		throws NoSuchDefaultScopedEntryException {

		return findByPrimaryKey((Serializable)DefaultScopedEntryId);
	}

	/**
	 * Returns the default scoped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry, or <code>null</code> if a default scoped entry with the primary key could not be found
	 */
	@Override
	public DefaultScopedEntry fetchByPrimaryKey(long DefaultScopedEntryId) {
		return fetchByPrimaryKey((Serializable)DefaultScopedEntryId);
	}

	/**
	 * Returns all the default scoped entries.
	 *
	 * @return the default scoped entries
	 */
	@Override
	public List<DefaultScopedEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @return the range of default scoped entries
	 */
	@Override
	public List<DefaultScopedEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of default scoped entries
	 */
	@Override
	public List<DefaultScopedEntry> findAll(
		int start, int end,
		OrderByComparator<DefaultScopedEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of default scoped entries
	 */
	@Override
	public List<DefaultScopedEntry> findAll(
		int start, int end,
		OrderByComparator<DefaultScopedEntry> orderByComparator,
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

		List<DefaultScopedEntry> list = null;

		if (useFinderCache) {
			list = (List<DefaultScopedEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DEFAULTSCOPEDENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DEFAULTSCOPEDENTRY;

				sql = sql.concat(DefaultScopedEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DefaultScopedEntry>)QueryUtil.list(
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
	 * Removes all the default scoped entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DefaultScopedEntry defaultScopedEntry : findAll()) {
			remove(defaultScopedEntry);
		}
	}

	/**
	 * Returns the number of default scoped entries.
	 *
	 * @return the number of default scoped entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_DEFAULTSCOPEDENTRY);

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
		return "DefaultScopedEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DEFAULTSCOPEDENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DefaultScopedEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the default scoped entry persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			DefaultScopedEntryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new DefaultScopedEntryModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, true);

		_finderPathCountByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, false);
	}

	public void destroy() {
		entityCache.removeCache(DefaultScopedEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DEFAULTSCOPEDENTRY =
		"SELECT defaultScopedEntry FROM DefaultScopedEntry defaultScopedEntry";

	private static final String _SQL_SELECT_DEFAULTSCOPEDENTRY_WHERE =
		"SELECT defaultScopedEntry FROM DefaultScopedEntry defaultScopedEntry WHERE ";

	private static final String _SQL_COUNT_DEFAULTSCOPEDENTRY =
		"SELECT COUNT(defaultScopedEntry) FROM DefaultScopedEntry defaultScopedEntry";

	private static final String _SQL_COUNT_DEFAULTSCOPEDENTRY_WHERE =
		"SELECT COUNT(defaultScopedEntry) FROM DefaultScopedEntry defaultScopedEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "defaultScopedEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DefaultScopedEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DefaultScopedEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultScopedEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class DefaultScopedEntryModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			DefaultScopedEntryModelImpl defaultScopedEntryModelImpl =
				(DefaultScopedEntryModelImpl)baseModel;

			long columnBitmask = defaultScopedEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					defaultScopedEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						defaultScopedEntryModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					defaultScopedEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return DefaultScopedEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return DefaultScopedEntryTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			DefaultScopedEntryModelImpl defaultScopedEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						defaultScopedEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = defaultScopedEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}