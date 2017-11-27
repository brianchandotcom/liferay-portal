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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPGroupException;
import com.liferay.commerce.product.model.CPGroup;
import com.liferay.commerce.product.model.impl.CPGroupImpl;
import com.liferay.commerce.product.model.impl.CPGroupModelImpl;
import com.liferay.commerce.product.service.persistence.CPGroupPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the cp group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPGroupPersistence
 * @see com.liferay.commerce.product.service.persistence.CPGroupUtil
 * @generated
 */
@ProviderType
public class CPGroupPersistenceImpl extends BasePersistenceImpl<CPGroup>
	implements CPGroupPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPGroupUtil} to access the cp group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPGroupImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
			CPGroupModelImpl.FINDER_CACHE_ENABLED, CPGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
			CPGroupModelImpl.FINDER_CACHE_ENABLED, CPGroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
			CPGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_GROUPID = new FinderPath(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
			CPGroupModelImpl.FINDER_CACHE_ENABLED, CPGroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByGroupId",
			new String[] { Long.class.getName() },
			CPGroupModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
			CPGroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the cp group where groupId = &#63; or throws a {@link NoSuchCPGroupException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching cp group
	 * @throws NoSuchCPGroupException if a matching cp group could not be found
	 */
	@Override
	public CPGroup findByGroupId(long groupId) throws NoSuchCPGroupException {
		CPGroup cpGroup = fetchByGroupId(groupId);

		if (cpGroup == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPGroupException(msg.toString());
		}

		return cpGroup;
	}

	/**
	 * Returns the cp group where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	 */
	@Override
	public CPGroup fetchByGroupId(long groupId) {
		return fetchByGroupId(groupId, true);
	}

	/**
	 * Returns the cp group where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	 */
	@Override
	public CPGroup fetchByGroupId(long groupId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_GROUPID,
					finderArgs, this);
		}

		if (result instanceof CPGroup) {
			CPGroup cpGroup = (CPGroup)result;

			if ((groupId != cpGroup.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CPGROUP_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<CPGroup> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_GROUPID,
						finderArgs, list);
				}
				else {
					CPGroup cpGroup = list.get(0);

					result = cpGroup;

					cacheResult(cpGroup);

					if ((cpGroup.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_GROUPID,
							finderArgs, cpGroup);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_GROUPID,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CPGroup)result;
		}
	}

	/**
	 * Removes the cp group where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the cp group that was removed
	 */
	@Override
	public CPGroup removeByGroupId(long groupId) throws NoSuchCPGroupException {
		CPGroup cpGroup = findByGroupId(groupId);

		return remove(cpGroup);
	}

	/**
	 * Returns the number of cp groups where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp groups
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPGROUP_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "cpGroup.groupId = ?";

	public CPGroupPersistenceImpl() {
		setModelClass(CPGroup.class);
	}

	/**
	 * Caches the cp group in the entity cache if it is enabled.
	 *
	 * @param cpGroup the cp group
	 */
	@Override
	public void cacheResult(CPGroup cpGroup) {
		entityCache.putResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
			CPGroupImpl.class, cpGroup.getPrimaryKey(), cpGroup);

		finderCache.putResult(FINDER_PATH_FETCH_BY_GROUPID,
			new Object[] { cpGroup.getGroupId() }, cpGroup);

		cpGroup.resetOriginalValues();
	}

	/**
	 * Caches the cp groups in the entity cache if it is enabled.
	 *
	 * @param cpGroups the cp groups
	 */
	@Override
	public void cacheResult(List<CPGroup> cpGroups) {
		for (CPGroup cpGroup : cpGroups) {
			if (entityCache.getResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
						CPGroupImpl.class, cpGroup.getPrimaryKey()) == null) {
				cacheResult(cpGroup);
			}
			else {
				cpGroup.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp groups.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPGroupImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp group.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPGroup cpGroup) {
		entityCache.removeResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
			CPGroupImpl.class, cpGroup.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPGroupModelImpl)cpGroup, true);
	}

	@Override
	public void clearCache(List<CPGroup> cpGroups) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPGroup cpGroup : cpGroups) {
			entityCache.removeResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
				CPGroupImpl.class, cpGroup.getPrimaryKey());

			clearUniqueFindersCache((CPGroupModelImpl)cpGroup, true);
		}
	}

	protected void cacheUniqueFindersCache(CPGroupModelImpl cpGroupModelImpl) {
		Object[] args = new Object[] { cpGroupModelImpl.getGroupId() };

		finderCache.putResult(FINDER_PATH_COUNT_BY_GROUPID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_GROUPID, args,
			cpGroupModelImpl, false);
	}

	protected void clearUniqueFindersCache(CPGroupModelImpl cpGroupModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] { cpGroupModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_GROUPID, args);
		}

		if ((cpGroupModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_GROUPID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] { cpGroupModelImpl.getOriginalGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_GROUPID, args);
		}
	}

	/**
	 * Creates a new cp group with the primary key. Does not add the cp group to the database.
	 *
	 * @param CPGroupId the primary key for the new cp group
	 * @return the new cp group
	 */
	@Override
	public CPGroup create(long CPGroupId) {
		CPGroup cpGroup = new CPGroupImpl();

		cpGroup.setNew(true);
		cpGroup.setPrimaryKey(CPGroupId);

		cpGroup.setCompanyId(companyProvider.getCompanyId());

		return cpGroup;
	}

	/**
	 * Removes the cp group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPGroupId the primary key of the cp group
	 * @return the cp group that was removed
	 * @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	 */
	@Override
	public CPGroup remove(long CPGroupId) throws NoSuchCPGroupException {
		return remove((Serializable)CPGroupId);
	}

	/**
	 * Removes the cp group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp group
	 * @return the cp group that was removed
	 * @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	 */
	@Override
	public CPGroup remove(Serializable primaryKey)
		throws NoSuchCPGroupException {
		Session session = null;

		try {
			session = openSession();

			CPGroup cpGroup = (CPGroup)session.get(CPGroupImpl.class, primaryKey);

			if (cpGroup == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpGroup);
		}
		catch (NoSuchCPGroupException nsee) {
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
	protected CPGroup removeImpl(CPGroup cpGroup) {
		cpGroup = toUnwrappedModel(cpGroup);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpGroup)) {
				cpGroup = (CPGroup)session.get(CPGroupImpl.class,
						cpGroup.getPrimaryKeyObj());
			}

			if (cpGroup != null) {
				session.delete(cpGroup);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpGroup != null) {
			clearCache(cpGroup);
		}

		return cpGroup;
	}

	@Override
	public CPGroup updateImpl(CPGroup cpGroup) {
		cpGroup = toUnwrappedModel(cpGroup);

		boolean isNew = cpGroup.isNew();

		CPGroupModelImpl cpGroupModelImpl = (CPGroupModelImpl)cpGroup;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpGroup.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpGroup.setCreateDate(now);
			}
			else {
				cpGroup.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!cpGroupModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpGroup.setModifiedDate(now);
			}
			else {
				cpGroup.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpGroup.isNew()) {
				session.save(cpGroup);

				cpGroup.setNew(false);
			}
			else {
				cpGroup = (CPGroup)session.merge(cpGroup);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPGroupModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
			CPGroupImpl.class, cpGroup.getPrimaryKey(), cpGroup, false);

		clearUniqueFindersCache(cpGroupModelImpl, false);
		cacheUniqueFindersCache(cpGroupModelImpl);

		cpGroup.resetOriginalValues();

		return cpGroup;
	}

	protected CPGroup toUnwrappedModel(CPGroup cpGroup) {
		if (cpGroup instanceof CPGroupImpl) {
			return cpGroup;
		}

		CPGroupImpl cpGroupImpl = new CPGroupImpl();

		cpGroupImpl.setNew(cpGroup.isNew());
		cpGroupImpl.setPrimaryKey(cpGroup.getPrimaryKey());

		cpGroupImpl.setCPGroupId(cpGroup.getCPGroupId());
		cpGroupImpl.setGroupId(cpGroup.getGroupId());
		cpGroupImpl.setCompanyId(cpGroup.getCompanyId());
		cpGroupImpl.setUserId(cpGroup.getUserId());
		cpGroupImpl.setUserName(cpGroup.getUserName());
		cpGroupImpl.setCreateDate(cpGroup.getCreateDate());
		cpGroupImpl.setModifiedDate(cpGroup.getModifiedDate());

		return cpGroupImpl;
	}

	/**
	 * Returns the cp group with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp group
	 * @return the cp group
	 * @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	 */
	@Override
	public CPGroup findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPGroupException {
		CPGroup cpGroup = fetchByPrimaryKey(primaryKey);

		if (cpGroup == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpGroup;
	}

	/**
	 * Returns the cp group with the primary key or throws a {@link NoSuchCPGroupException} if it could not be found.
	 *
	 * @param CPGroupId the primary key of the cp group
	 * @return the cp group
	 * @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	 */
	@Override
	public CPGroup findByPrimaryKey(long CPGroupId)
		throws NoSuchCPGroupException {
		return findByPrimaryKey((Serializable)CPGroupId);
	}

	/**
	 * Returns the cp group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp group
	 * @return the cp group, or <code>null</code> if a cp group with the primary key could not be found
	 */
	@Override
	public CPGroup fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
				CPGroupImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPGroup cpGroup = (CPGroup)serializable;

		if (cpGroup == null) {
			Session session = null;

			try {
				session = openSession();

				cpGroup = (CPGroup)session.get(CPGroupImpl.class, primaryKey);

				if (cpGroup != null) {
					cacheResult(cpGroup);
				}
				else {
					entityCache.putResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
						CPGroupImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
					CPGroupImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpGroup;
	}

	/**
	 * Returns the cp group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPGroupId the primary key of the cp group
	 * @return the cp group, or <code>null</code> if a cp group with the primary key could not be found
	 */
	@Override
	public CPGroup fetchByPrimaryKey(long CPGroupId) {
		return fetchByPrimaryKey((Serializable)CPGroupId);
	}

	@Override
	public Map<Serializable, CPGroup> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPGroup> map = new HashMap<Serializable, CPGroup>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPGroup cpGroup = fetchByPrimaryKey(primaryKey);

			if (cpGroup != null) {
				map.put(primaryKey, cpGroup);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
					CPGroupImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPGroup)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPGROUP_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CPGroup cpGroup : (List<CPGroup>)q.list()) {
				map.put(cpGroup.getPrimaryKeyObj(), cpGroup);

				cacheResult(cpGroup);

				uncachedPrimaryKeys.remove(cpGroup.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPGroupModelImpl.ENTITY_CACHE_ENABLED,
					CPGroupImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the cp groups.
	 *
	 * @return the cp groups
	 */
	@Override
	public List<CPGroup> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp groups
	 * @param end the upper bound of the range of cp groups (not inclusive)
	 * @return the range of cp groups
	 */
	@Override
	public List<CPGroup> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp groups
	 * @param end the upper bound of the range of cp groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp groups
	 */
	@Override
	public List<CPGroup> findAll(int start, int end,
		OrderByComparator<CPGroup> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp groups
	 * @param end the upper bound of the range of cp groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp groups
	 */
	@Override
	public List<CPGroup> findAll(int start, int end,
		OrderByComparator<CPGroup> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<CPGroup> list = null;

		if (retrieveFromCache) {
			list = (List<CPGroup>)finderCache.getResult(finderPath, finderArgs,
					this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPGROUP);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPGROUP;

				if (pagination) {
					sql = sql.concat(CPGroupModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPGroup>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPGroup>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cp groups from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPGroup cpGroup : findAll()) {
			remove(cpGroup);
		}
	}

	/**
	 * Returns the number of cp groups.
	 *
	 * @return the number of cp groups
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPGROUP);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPGroupModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp group persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPGroupImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_CPGROUP = "SELECT cpGroup FROM CPGroup cpGroup";
	private static final String _SQL_SELECT_CPGROUP_WHERE_PKS_IN = "SELECT cpGroup FROM CPGroup cpGroup WHERE CPGroupId IN (";
	private static final String _SQL_SELECT_CPGROUP_WHERE = "SELECT cpGroup FROM CPGroup cpGroup WHERE ";
	private static final String _SQL_COUNT_CPGROUP = "SELECT COUNT(cpGroup) FROM CPGroup cpGroup";
	private static final String _SQL_COUNT_CPGROUP_WHERE = "SELECT COUNT(cpGroup) FROM CPGroup cpGroup WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpGroup.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPGroup exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPGroup exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPGroupPersistenceImpl.class);
}