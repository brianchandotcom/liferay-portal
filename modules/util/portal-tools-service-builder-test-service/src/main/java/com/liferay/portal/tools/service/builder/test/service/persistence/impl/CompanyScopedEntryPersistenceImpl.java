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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCompanyScopedEntryException;
import com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry;
import com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.CompanyScopedEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.CompanyScopedEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.CompanyScopedEntryPersistence;

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
 * The persistence implementation for the company scoped entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CompanyScopedEntryPersistenceImpl
	extends BasePersistenceImpl<CompanyScopedEntry>
	implements CompanyScopedEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CompanyScopedEntryUtil</code> to access the company scoped entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CompanyScopedEntryImpl.class.getName();

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
	 * Returns the company scoped entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCompanyScopedEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching company scoped entry
	 * @throws NoSuchCompanyScopedEntryException if a matching company scoped entry could not be found
	 */
	@Override
	public CompanyScopedEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCompanyScopedEntryException {

		CompanyScopedEntry companyScopedEntry = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (companyScopedEntry == null) {
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

			throw new NoSuchCompanyScopedEntryException(sb.toString());
		}

		return companyScopedEntry;
	}

	/**
	 * Returns the company scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching company scoped entry, or <code>null</code> if a matching company scoped entry could not be found
	 */
	@Override
	public CompanyScopedEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the company scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching company scoped entry, or <code>null</code> if a matching company scoped entry could not be found
	 */
	@Override
	public CompanyScopedEntry fetchByC_ERC(
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

		if (result instanceof CompanyScopedEntry) {
			CompanyScopedEntry companyScopedEntry = (CompanyScopedEntry)result;

			if ((companyId != companyScopedEntry.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					companyScopedEntry.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMPANYSCOPEDENTRY_WHERE);

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

				List<CompanyScopedEntry> list = query.list();

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
								"CompanyScopedEntryPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CompanyScopedEntry companyScopedEntry = list.get(0);

					result = companyScopedEntry;

					cacheResult(companyScopedEntry);
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
			return (CompanyScopedEntry)result;
		}
	}

	/**
	 * Removes the company scoped entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the company scoped entry that was removed
	 */
	@Override
	public CompanyScopedEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCompanyScopedEntryException {

		CompanyScopedEntry companyScopedEntry = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(companyScopedEntry);
	}

	/**
	 * Returns the number of company scoped entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching company scoped entries
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMPANYSCOPEDENTRY_WHERE);

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
		"companyScopedEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"companyScopedEntry.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(companyScopedEntry.externalReferenceCode IS NULL OR companyScopedEntry.externalReferenceCode = '')";

	public CompanyScopedEntryPersistenceImpl() {
		setModelClass(CompanyScopedEntry.class);

		setModelImplClass(CompanyScopedEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CompanyScopedEntryTable.INSTANCE);
	}

	/**
	 * Caches the company scoped entry in the entity cache if it is enabled.
	 *
	 * @param companyScopedEntry the company scoped entry
	 */
	@Override
	public void cacheResult(CompanyScopedEntry companyScopedEntry) {
		entityCache.putResult(
			CompanyScopedEntryImpl.class, companyScopedEntry.getPrimaryKey(),
			companyScopedEntry);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				companyScopedEntry.getCompanyId(),
				companyScopedEntry.getExternalReferenceCode()
			},
			companyScopedEntry);
	}

	/**
	 * Caches the company scoped entries in the entity cache if it is enabled.
	 *
	 * @param companyScopedEntries the company scoped entries
	 */
	@Override
	public void cacheResult(List<CompanyScopedEntry> companyScopedEntries) {
		for (CompanyScopedEntry companyScopedEntry : companyScopedEntries) {
			if (entityCache.getResult(
					CompanyScopedEntryImpl.class,
					companyScopedEntry.getPrimaryKey()) == null) {

				cacheResult(companyScopedEntry);
			}
		}
	}

	/**
	 * Clears the cache for all company scoped entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CompanyScopedEntryImpl.class);

		finderCache.clearCache(CompanyScopedEntryImpl.class);
	}

	/**
	 * Clears the cache for the company scoped entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CompanyScopedEntry companyScopedEntry) {
		entityCache.removeResult(
			CompanyScopedEntryImpl.class, companyScopedEntry);
	}

	@Override
	public void clearCache(List<CompanyScopedEntry> companyScopedEntries) {
		for (CompanyScopedEntry companyScopedEntry : companyScopedEntries) {
			entityCache.removeResult(
				CompanyScopedEntryImpl.class, companyScopedEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CompanyScopedEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CompanyScopedEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CompanyScopedEntryModelImpl companyScopedEntryModelImpl) {

		Object[] args = new Object[] {
			companyScopedEntryModelImpl.getCompanyId(),
			companyScopedEntryModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, companyScopedEntryModelImpl);
	}

	/**
	 * Creates a new company scoped entry with the primary key. Does not add the company scoped entry to the database.
	 *
	 * @param CompanyScopedEntryId the primary key for the new company scoped entry
	 * @return the new company scoped entry
	 */
	@Override
	public CompanyScopedEntry create(long CompanyScopedEntryId) {
		CompanyScopedEntry companyScopedEntry = new CompanyScopedEntryImpl();

		companyScopedEntry.setNew(true);
		companyScopedEntry.setPrimaryKey(CompanyScopedEntryId);

		companyScopedEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return companyScopedEntry;
	}

	/**
	 * Removes the company scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CompanyScopedEntryId the primary key of the company scoped entry
	 * @return the company scoped entry that was removed
	 * @throws NoSuchCompanyScopedEntryException if a company scoped entry with the primary key could not be found
	 */
	@Override
	public CompanyScopedEntry remove(long CompanyScopedEntryId)
		throws NoSuchCompanyScopedEntryException {

		return remove((Serializable)CompanyScopedEntryId);
	}

	/**
	 * Removes the company scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the company scoped entry
	 * @return the company scoped entry that was removed
	 * @throws NoSuchCompanyScopedEntryException if a company scoped entry with the primary key could not be found
	 */
	@Override
	public CompanyScopedEntry remove(Serializable primaryKey)
		throws NoSuchCompanyScopedEntryException {

		Session session = null;

		try {
			session = openSession();

			CompanyScopedEntry companyScopedEntry =
				(CompanyScopedEntry)session.get(
					CompanyScopedEntryImpl.class, primaryKey);

			if (companyScopedEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCompanyScopedEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(companyScopedEntry);
		}
		catch (NoSuchCompanyScopedEntryException noSuchEntityException) {
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
	protected CompanyScopedEntry removeImpl(
		CompanyScopedEntry companyScopedEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(companyScopedEntry)) {
				companyScopedEntry = (CompanyScopedEntry)session.get(
					CompanyScopedEntryImpl.class,
					companyScopedEntry.getPrimaryKeyObj());
			}

			if (companyScopedEntry != null) {
				session.delete(companyScopedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (companyScopedEntry != null) {
			clearCache(companyScopedEntry);
		}

		return companyScopedEntry;
	}

	@Override
	public CompanyScopedEntry updateImpl(
		CompanyScopedEntry companyScopedEntry) {

		boolean isNew = companyScopedEntry.isNew();

		if (!(companyScopedEntry instanceof CompanyScopedEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(companyScopedEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					companyScopedEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in companyScopedEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CompanyScopedEntry implementation " +
					companyScopedEntry.getClass());
		}

		CompanyScopedEntryModelImpl companyScopedEntryModelImpl =
			(CompanyScopedEntryModelImpl)companyScopedEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(companyScopedEntry);
			}
			else {
				companyScopedEntry = (CompanyScopedEntry)session.merge(
					companyScopedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CompanyScopedEntryImpl.class, companyScopedEntryModelImpl, false,
			true);

		cacheUniqueFindersCache(companyScopedEntryModelImpl);

		if (isNew) {
			companyScopedEntry.setNew(false);
		}

		companyScopedEntry.resetOriginalValues();

		return companyScopedEntry;
	}

	/**
	 * Returns the company scoped entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the company scoped entry
	 * @return the company scoped entry
	 * @throws NoSuchCompanyScopedEntryException if a company scoped entry with the primary key could not be found
	 */
	@Override
	public CompanyScopedEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCompanyScopedEntryException {

		CompanyScopedEntry companyScopedEntry = fetchByPrimaryKey(primaryKey);

		if (companyScopedEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCompanyScopedEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return companyScopedEntry;
	}

	/**
	 * Returns the company scoped entry with the primary key or throws a <code>NoSuchCompanyScopedEntryException</code> if it could not be found.
	 *
	 * @param CompanyScopedEntryId the primary key of the company scoped entry
	 * @return the company scoped entry
	 * @throws NoSuchCompanyScopedEntryException if a company scoped entry with the primary key could not be found
	 */
	@Override
	public CompanyScopedEntry findByPrimaryKey(long CompanyScopedEntryId)
		throws NoSuchCompanyScopedEntryException {

		return findByPrimaryKey((Serializable)CompanyScopedEntryId);
	}

	/**
	 * Returns the company scoped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CompanyScopedEntryId the primary key of the company scoped entry
	 * @return the company scoped entry, or <code>null</code> if a company scoped entry with the primary key could not be found
	 */
	@Override
	public CompanyScopedEntry fetchByPrimaryKey(long CompanyScopedEntryId) {
		return fetchByPrimaryKey((Serializable)CompanyScopedEntryId);
	}

	/**
	 * Returns all the company scoped entries.
	 *
	 * @return the company scoped entries
	 */
	@Override
	public List<CompanyScopedEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the company scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company scoped entries
	 * @param end the upper bound of the range of company scoped entries (not inclusive)
	 * @return the range of company scoped entries
	 */
	@Override
	public List<CompanyScopedEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the company scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company scoped entries
	 * @param end the upper bound of the range of company scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of company scoped entries
	 */
	@Override
	public List<CompanyScopedEntry> findAll(
		int start, int end,
		OrderByComparator<CompanyScopedEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the company scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CompanyScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company scoped entries
	 * @param end the upper bound of the range of company scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of company scoped entries
	 */
	@Override
	public List<CompanyScopedEntry> findAll(
		int start, int end,
		OrderByComparator<CompanyScopedEntry> orderByComparator,
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

		List<CompanyScopedEntry> list = null;

		if (useFinderCache) {
			list = (List<CompanyScopedEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMPANYSCOPEDENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMPANYSCOPEDENTRY;

				sql = sql.concat(CompanyScopedEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CompanyScopedEntry>)QueryUtil.list(
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
	 * Removes all the company scoped entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CompanyScopedEntry companyScopedEntry : findAll()) {
			remove(companyScopedEntry);
		}
	}

	/**
	 * Returns the number of company scoped entries.
	 *
	 * @return the number of company scoped entries
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
					_SQL_COUNT_COMPANYSCOPEDENTRY);

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
		return "CompanyScopedEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMPANYSCOPEDENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CompanyScopedEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the company scoped entry persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CompanyScopedEntryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CompanyScopedEntryModelArgumentsResolver(),
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
		entityCache.removeCache(CompanyScopedEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMPANYSCOPEDENTRY =
		"SELECT companyScopedEntry FROM CompanyScopedEntry companyScopedEntry";

	private static final String _SQL_SELECT_COMPANYSCOPEDENTRY_WHERE =
		"SELECT companyScopedEntry FROM CompanyScopedEntry companyScopedEntry WHERE ";

	private static final String _SQL_COUNT_COMPANYSCOPEDENTRY =
		"SELECT COUNT(companyScopedEntry) FROM CompanyScopedEntry companyScopedEntry";

	private static final String _SQL_COUNT_COMPANYSCOPEDENTRY_WHERE =
		"SELECT COUNT(companyScopedEntry) FROM CompanyScopedEntry companyScopedEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "companyScopedEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CompanyScopedEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CompanyScopedEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyScopedEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CompanyScopedEntryModelArgumentsResolver
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

			CompanyScopedEntryModelImpl companyScopedEntryModelImpl =
				(CompanyScopedEntryModelImpl)baseModel;

			long columnBitmask = companyScopedEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					companyScopedEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						companyScopedEntryModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					companyScopedEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CompanyScopedEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CompanyScopedEntryTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CompanyScopedEntryModelImpl companyScopedEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						companyScopedEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = companyScopedEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}