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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCGroupEntryException;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCGroupEntryPersistence;

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
 * The persistence implementation for the erc group entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ERCGroupEntryPersistenceImpl
	extends BasePersistenceImpl<ERCGroupEntry>
	implements ERCGroupEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ERCGroupEntryUtil</code> to access the erc group entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ERCGroupEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByG_ERC;
	private FinderPath _finderPathCountByG_ERC;

	/**
	 * Returns the erc group entry where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCGroupEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entry
	 * @throws NoSuchERCGroupEntryException if a matching erc group entry could not be found
	 */
	@Override
	public ERCGroupEntry findByG_ERC(long groupId, String externalReferenceCode)
		throws NoSuchERCGroupEntryException {

		ERCGroupEntry ercGroupEntry = fetchByG_ERC(
			groupId, externalReferenceCode);

		if (ercGroupEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchERCGroupEntryException(sb.toString());
		}

		return ercGroupEntry;
	}

	/**
	 * Returns the erc group entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entry, or <code>null</code> if a matching erc group entry could not be found
	 */
	@Override
	public ERCGroupEntry fetchByG_ERC(
		long groupId, String externalReferenceCode) {

		return fetchByG_ERC(groupId, externalReferenceCode, true);
	}

	/**
	 * Returns the erc group entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc group entry, or <code>null</code> if a matching erc group entry could not be found
	 */
	@Override
	public ERCGroupEntry fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByG_ERC, finderArgs);
		}

		if (result instanceof ERCGroupEntry) {
			ERCGroupEntry ercGroupEntry = (ERCGroupEntry)result;

			if ((groupId != ercGroupEntry.getGroupId()) ||
				!Objects.equals(
					externalReferenceCode,
					ercGroupEntry.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ERCGROUPENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_ERC_GROUPID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<ERCGroupEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									groupId, externalReferenceCode
								};
							}

							_log.warn(
								"ERCGroupEntryPersistenceImpl.fetchByG_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ERCGroupEntry ercGroupEntry = list.get(0);

					result = ercGroupEntry;

					cacheResult(ercGroupEntry);
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
			return (ERCGroupEntry)result;
		}
	}

	/**
	 * Removes the erc group entry where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc group entry that was removed
	 */
	@Override
	public ERCGroupEntry removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchERCGroupEntryException {

		ERCGroupEntry ercGroupEntry = findByG_ERC(
			groupId, externalReferenceCode);

		return remove(ercGroupEntry);
	}

	/**
	 * Returns the number of erc group entries where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc group entries
	 */
	@Override
	public int countByG_ERC(long groupId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByG_ERC;

		Object[] finderArgs = new Object[] {groupId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCGROUPENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_ERC_GROUPID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_ERC_GROUPID_2 =
		"ercGroupEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2 =
		"ercGroupEntry.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3 =
		"(ercGroupEntry.externalReferenceCode IS NULL OR ercGroupEntry.externalReferenceCode = '')";

	public ERCGroupEntryPersistenceImpl() {
		setModelClass(ERCGroupEntry.class);

		setModelImplClass(ERCGroupEntryImpl.class);
		setModelPKClass(long.class);

		setTable(ERCGroupEntryTable.INSTANCE);
	}

	/**
	 * Caches the erc group entry in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntry the erc group entry
	 */
	@Override
	public void cacheResult(ERCGroupEntry ercGroupEntry) {
		entityCache.putResult(
			ERCGroupEntryImpl.class, ercGroupEntry.getPrimaryKey(),
			ercGroupEntry);

		finderCache.putResult(
			_finderPathFetchByG_ERC,
			new Object[] {
				ercGroupEntry.getGroupId(),
				ercGroupEntry.getExternalReferenceCode()
			},
			ercGroupEntry);
	}

	/**
	 * Caches the erc group entries in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntries the erc group entries
	 */
	@Override
	public void cacheResult(List<ERCGroupEntry> ercGroupEntries) {
		for (ERCGroupEntry ercGroupEntry : ercGroupEntries) {
			if (entityCache.getResult(
					ERCGroupEntryImpl.class, ercGroupEntry.getPrimaryKey()) ==
						null) {

				cacheResult(ercGroupEntry);
			}
		}
	}

	/**
	 * Clears the cache for all erc group entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ERCGroupEntryImpl.class);

		finderCache.clearCache(ERCGroupEntryImpl.class);
	}

	/**
	 * Clears the cache for the erc group entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ERCGroupEntry ercGroupEntry) {
		entityCache.removeResult(ERCGroupEntryImpl.class, ercGroupEntry);
	}

	@Override
	public void clearCache(List<ERCGroupEntry> ercGroupEntries) {
		for (ERCGroupEntry ercGroupEntry : ercGroupEntries) {
			entityCache.removeResult(ERCGroupEntryImpl.class, ercGroupEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ERCGroupEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ERCGroupEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ERCGroupEntryModelImpl ercGroupEntryModelImpl) {

		Object[] args = new Object[] {
			ercGroupEntryModelImpl.getGroupId(),
			ercGroupEntryModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByG_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_ERC, args, ercGroupEntryModelImpl);
	}

	/**
	 * Creates a new erc group entry with the primary key. Does not add the erc group entry to the database.
	 *
	 * @param ercGroupEntryId the primary key for the new erc group entry
	 * @return the new erc group entry
	 */
	@Override
	public ERCGroupEntry create(long ercGroupEntryId) {
		ERCGroupEntry ercGroupEntry = new ERCGroupEntryImpl();

		ercGroupEntry.setNew(true);
		ercGroupEntry.setPrimaryKey(ercGroupEntryId);

		ercGroupEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ercGroupEntry;
	}

	/**
	 * Removes the erc group entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry that was removed
	 * @throws NoSuchERCGroupEntryException if a erc group entry with the primary key could not be found
	 */
	@Override
	public ERCGroupEntry remove(long ercGroupEntryId)
		throws NoSuchERCGroupEntryException {

		return remove((Serializable)ercGroupEntryId);
	}

	/**
	 * Removes the erc group entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the erc group entry
	 * @return the erc group entry that was removed
	 * @throws NoSuchERCGroupEntryException if a erc group entry with the primary key could not be found
	 */
	@Override
	public ERCGroupEntry remove(Serializable primaryKey)
		throws NoSuchERCGroupEntryException {

		Session session = null;

		try {
			session = openSession();

			ERCGroupEntry ercGroupEntry = (ERCGroupEntry)session.get(
				ERCGroupEntryImpl.class, primaryKey);

			if (ercGroupEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchERCGroupEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ercGroupEntry);
		}
		catch (NoSuchERCGroupEntryException noSuchEntityException) {
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
	protected ERCGroupEntry removeImpl(ERCGroupEntry ercGroupEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ercGroupEntry)) {
				ercGroupEntry = (ERCGroupEntry)session.get(
					ERCGroupEntryImpl.class, ercGroupEntry.getPrimaryKeyObj());
			}

			if (ercGroupEntry != null) {
				session.delete(ercGroupEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ercGroupEntry != null) {
			clearCache(ercGroupEntry);
		}

		return ercGroupEntry;
	}

	@Override
	public ERCGroupEntry updateImpl(ERCGroupEntry ercGroupEntry) {
		boolean isNew = ercGroupEntry.isNew();

		if (!(ercGroupEntry instanceof ERCGroupEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ercGroupEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ercGroupEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ercGroupEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ERCGroupEntry implementation " +
					ercGroupEntry.getClass());
		}

		ERCGroupEntryModelImpl ercGroupEntryModelImpl =
			(ERCGroupEntryModelImpl)ercGroupEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ercGroupEntry);
			}
			else {
				ercGroupEntry = (ERCGroupEntry)session.merge(ercGroupEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ERCGroupEntryImpl.class, ercGroupEntryModelImpl, false, true);

		cacheUniqueFindersCache(ercGroupEntryModelImpl);

		if (isNew) {
			ercGroupEntry.setNew(false);
		}

		ercGroupEntry.resetOriginalValues();

		return ercGroupEntry;
	}

	/**
	 * Returns the erc group entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the erc group entry
	 * @return the erc group entry
	 * @throws NoSuchERCGroupEntryException if a erc group entry with the primary key could not be found
	 */
	@Override
	public ERCGroupEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchERCGroupEntryException {

		ERCGroupEntry ercGroupEntry = fetchByPrimaryKey(primaryKey);

		if (ercGroupEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchERCGroupEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ercGroupEntry;
	}

	/**
	 * Returns the erc group entry with the primary key or throws a <code>NoSuchERCGroupEntryException</code> if it could not be found.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry
	 * @throws NoSuchERCGroupEntryException if a erc group entry with the primary key could not be found
	 */
	@Override
	public ERCGroupEntry findByPrimaryKey(long ercGroupEntryId)
		throws NoSuchERCGroupEntryException {

		return findByPrimaryKey((Serializable)ercGroupEntryId);
	}

	/**
	 * Returns the erc group entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry, or <code>null</code> if a erc group entry with the primary key could not be found
	 */
	@Override
	public ERCGroupEntry fetchByPrimaryKey(long ercGroupEntryId) {
		return fetchByPrimaryKey((Serializable)ercGroupEntryId);
	}

	/**
	 * Returns all the erc group entries.
	 *
	 * @return the erc group entries
	 */
	@Override
	public List<ERCGroupEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @return the range of erc group entries
	 */
	@Override
	public List<ERCGroupEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc group entries
	 */
	@Override
	public List<ERCGroupEntry> findAll(
		int start, int end,
		OrderByComparator<ERCGroupEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc group entries
	 */
	@Override
	public List<ERCGroupEntry> findAll(
		int start, int end, OrderByComparator<ERCGroupEntry> orderByComparator,
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

		List<ERCGroupEntry> list = null;

		if (useFinderCache) {
			list = (List<ERCGroupEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ERCGROUPENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ERCGROUPENTRY;

				sql = sql.concat(ERCGroupEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ERCGroupEntry>)QueryUtil.list(
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
	 * Removes all the erc group entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ERCGroupEntry ercGroupEntry : findAll()) {
			remove(ercGroupEntry);
		}
	}

	/**
	 * Returns the number of erc group entries.
	 *
	 * @return the number of erc group entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ERCGROUPENTRY);

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
		return "ercGroupEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ERCGROUPENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ERCGroupEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the erc group entry persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			ERCGroupEntryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new ERCGroupEntryModelArgumentsResolver(),
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

		_finderPathFetchByG_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "externalReferenceCode"}, true);

		_finderPathCountByG_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "externalReferenceCode"}, false);
	}

	public void destroy() {
		entityCache.removeCache(ERCGroupEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ERCGROUPENTRY =
		"SELECT ercGroupEntry FROM ERCGroupEntry ercGroupEntry";

	private static final String _SQL_SELECT_ERCGROUPENTRY_WHERE =
		"SELECT ercGroupEntry FROM ERCGroupEntry ercGroupEntry WHERE ";

	private static final String _SQL_COUNT_ERCGROUPENTRY =
		"SELECT COUNT(ercGroupEntry) FROM ERCGroupEntry ercGroupEntry";

	private static final String _SQL_COUNT_ERCGROUPENTRY_WHERE =
		"SELECT COUNT(ercGroupEntry) FROM ERCGroupEntry ercGroupEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ercGroupEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ERCGroupEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ERCGroupEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ERCGroupEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class ERCGroupEntryModelArgumentsResolver
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

			ERCGroupEntryModelImpl ercGroupEntryModelImpl =
				(ERCGroupEntryModelImpl)baseModel;

			long columnBitmask = ercGroupEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(ercGroupEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						ercGroupEntryModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(ercGroupEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return ERCGroupEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return ERCGroupEntryTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			ERCGroupEntryModelImpl ercGroupEntryModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						ercGroupEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = ercGroupEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}