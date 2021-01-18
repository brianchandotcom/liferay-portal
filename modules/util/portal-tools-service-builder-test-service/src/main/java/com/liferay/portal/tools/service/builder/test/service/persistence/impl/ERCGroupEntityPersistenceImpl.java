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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCGroupEntityException;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntityTable;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntityImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntityModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCGroupEntityPersistence;

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
 * The persistence implementation for the erc group entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ERCGroupEntityPersistenceImpl
	extends BasePersistenceImpl<ERCGroupEntity>
	implements ERCGroupEntityPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ERCGroupEntityUtil</code> to access the erc group entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ERCGroupEntityImpl.class.getName();

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
	 * Returns the erc group entity where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCGroupEntityException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entity
	 * @throws NoSuchERCGroupEntityException if a matching erc group entity could not be found
	 */
	@Override
	public ERCGroupEntity findByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchERCGroupEntityException {

		ERCGroupEntity ercGroupEntity = fetchByG_ERC(
			groupId, externalReferenceCode);

		if (ercGroupEntity == null) {
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

			throw new NoSuchERCGroupEntityException(sb.toString());
		}

		return ercGroupEntity;
	}

	/**
	 * Returns the erc group entity where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entity, or <code>null</code> if a matching erc group entity could not be found
	 */
	@Override
	public ERCGroupEntity fetchByG_ERC(
		long groupId, String externalReferenceCode) {

		return fetchByG_ERC(groupId, externalReferenceCode, true);
	}

	/**
	 * Returns the erc group entity where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc group entity, or <code>null</code> if a matching erc group entity could not be found
	 */
	@Override
	public ERCGroupEntity fetchByG_ERC(
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

		if (result instanceof ERCGroupEntity) {
			ERCGroupEntity ercGroupEntity = (ERCGroupEntity)result;

			if ((groupId != ercGroupEntity.getGroupId()) ||
				!Objects.equals(
					externalReferenceCode,
					ercGroupEntity.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ERCGROUPENTITY_WHERE);

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

				List<ERCGroupEntity> list = query.list();

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
								"ERCGroupEntityPersistenceImpl.fetchByG_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ERCGroupEntity ercGroupEntity = list.get(0);

					result = ercGroupEntity;

					cacheResult(ercGroupEntity);
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
			return (ERCGroupEntity)result;
		}
	}

	/**
	 * Removes the erc group entity where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc group entity that was removed
	 */
	@Override
	public ERCGroupEntity removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchERCGroupEntityException {

		ERCGroupEntity ercGroupEntity = findByG_ERC(
			groupId, externalReferenceCode);

		return remove(ercGroupEntity);
	}

	/**
	 * Returns the number of erc group entities where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc group entities
	 */
	@Override
	public int countByG_ERC(long groupId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByG_ERC;

		Object[] finderArgs = new Object[] {groupId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCGROUPENTITY_WHERE);

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
		"ercGroupEntity.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_2 =
		"ercGroupEntity.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_G_ERC_EXTERNALREFERENCECODE_3 =
		"(ercGroupEntity.externalReferenceCode IS NULL OR ercGroupEntity.externalReferenceCode = '')";

	public ERCGroupEntityPersistenceImpl() {
		setModelClass(ERCGroupEntity.class);

		setModelImplClass(ERCGroupEntityImpl.class);
		setModelPKClass(long.class);

		setTable(ERCGroupEntityTable.INSTANCE);
	}

	/**
	 * Caches the erc group entity in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntity the erc group entity
	 */
	@Override
	public void cacheResult(ERCGroupEntity ercGroupEntity) {
		entityCache.putResult(
			ERCGroupEntityImpl.class, ercGroupEntity.getPrimaryKey(),
			ercGroupEntity);

		finderCache.putResult(
			_finderPathFetchByG_ERC,
			new Object[] {
				ercGroupEntity.getGroupId(),
				ercGroupEntity.getExternalReferenceCode()
			},
			ercGroupEntity);
	}

	/**
	 * Caches the erc group entities in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntities the erc group entities
	 */
	@Override
	public void cacheResult(List<ERCGroupEntity> ercGroupEntities) {
		for (ERCGroupEntity ercGroupEntity : ercGroupEntities) {
			if (entityCache.getResult(
					ERCGroupEntityImpl.class, ercGroupEntity.getPrimaryKey()) ==
						null) {

				cacheResult(ercGroupEntity);
			}
		}
	}

	/**
	 * Clears the cache for all erc group entities.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ERCGroupEntityImpl.class);

		finderCache.clearCache(ERCGroupEntityImpl.class);
	}

	/**
	 * Clears the cache for the erc group entity.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ERCGroupEntity ercGroupEntity) {
		entityCache.removeResult(ERCGroupEntityImpl.class, ercGroupEntity);
	}

	@Override
	public void clearCache(List<ERCGroupEntity> ercGroupEntities) {
		for (ERCGroupEntity ercGroupEntity : ercGroupEntities) {
			entityCache.removeResult(ERCGroupEntityImpl.class, ercGroupEntity);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ERCGroupEntityImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ERCGroupEntityImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ERCGroupEntityModelImpl ercGroupEntityModelImpl) {

		Object[] args = new Object[] {
			ercGroupEntityModelImpl.getGroupId(),
			ercGroupEntityModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByG_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_ERC, args, ercGroupEntityModelImpl);
	}

	/**
	 * Creates a new erc group entity with the primary key. Does not add the erc group entity to the database.
	 *
	 * @param ercGroupEntityId the primary key for the new erc group entity
	 * @return the new erc group entity
	 */
	@Override
	public ERCGroupEntity create(long ercGroupEntityId) {
		ERCGroupEntity ercGroupEntity = new ERCGroupEntityImpl();

		ercGroupEntity.setNew(true);
		ercGroupEntity.setPrimaryKey(ercGroupEntityId);

		ercGroupEntity.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ercGroupEntity;
	}

	/**
	 * Removes the erc group entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity that was removed
	 * @throws NoSuchERCGroupEntityException if a erc group entity with the primary key could not be found
	 */
	@Override
	public ERCGroupEntity remove(long ercGroupEntityId)
		throws NoSuchERCGroupEntityException {

		return remove((Serializable)ercGroupEntityId);
	}

	/**
	 * Removes the erc group entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the erc group entity
	 * @return the erc group entity that was removed
	 * @throws NoSuchERCGroupEntityException if a erc group entity with the primary key could not be found
	 */
	@Override
	public ERCGroupEntity remove(Serializable primaryKey)
		throws NoSuchERCGroupEntityException {

		Session session = null;

		try {
			session = openSession();

			ERCGroupEntity ercGroupEntity = (ERCGroupEntity)session.get(
				ERCGroupEntityImpl.class, primaryKey);

			if (ercGroupEntity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchERCGroupEntityException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ercGroupEntity);
		}
		catch (NoSuchERCGroupEntityException noSuchEntityException) {
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
	protected ERCGroupEntity removeImpl(ERCGroupEntity ercGroupEntity) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ercGroupEntity)) {
				ercGroupEntity = (ERCGroupEntity)session.get(
					ERCGroupEntityImpl.class,
					ercGroupEntity.getPrimaryKeyObj());
			}

			if (ercGroupEntity != null) {
				session.delete(ercGroupEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ercGroupEntity != null) {
			clearCache(ercGroupEntity);
		}

		return ercGroupEntity;
	}

	@Override
	public ERCGroupEntity updateImpl(ERCGroupEntity ercGroupEntity) {
		boolean isNew = ercGroupEntity.isNew();

		if (!(ercGroupEntity instanceof ERCGroupEntityModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ercGroupEntity.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ercGroupEntity);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ercGroupEntity proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ERCGroupEntity implementation " +
					ercGroupEntity.getClass());
		}

		ERCGroupEntityModelImpl ercGroupEntityModelImpl =
			(ERCGroupEntityModelImpl)ercGroupEntity;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ercGroupEntity);
			}
			else {
				ercGroupEntity = (ERCGroupEntity)session.merge(ercGroupEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ERCGroupEntityImpl.class, ercGroupEntityModelImpl, false, true);

		cacheUniqueFindersCache(ercGroupEntityModelImpl);

		if (isNew) {
			ercGroupEntity.setNew(false);
		}

		ercGroupEntity.resetOriginalValues();

		return ercGroupEntity;
	}

	/**
	 * Returns the erc group entity with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the erc group entity
	 * @return the erc group entity
	 * @throws NoSuchERCGroupEntityException if a erc group entity with the primary key could not be found
	 */
	@Override
	public ERCGroupEntity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchERCGroupEntityException {

		ERCGroupEntity ercGroupEntity = fetchByPrimaryKey(primaryKey);

		if (ercGroupEntity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchERCGroupEntityException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ercGroupEntity;
	}

	/**
	 * Returns the erc group entity with the primary key or throws a <code>NoSuchERCGroupEntityException</code> if it could not be found.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity
	 * @throws NoSuchERCGroupEntityException if a erc group entity with the primary key could not be found
	 */
	@Override
	public ERCGroupEntity findByPrimaryKey(long ercGroupEntityId)
		throws NoSuchERCGroupEntityException {

		return findByPrimaryKey((Serializable)ercGroupEntityId);
	}

	/**
	 * Returns the erc group entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity, or <code>null</code> if a erc group entity with the primary key could not be found
	 */
	@Override
	public ERCGroupEntity fetchByPrimaryKey(long ercGroupEntityId) {
		return fetchByPrimaryKey((Serializable)ercGroupEntityId);
	}

	/**
	 * Returns all the erc group entities.
	 *
	 * @return the erc group entities
	 */
	@Override
	public List<ERCGroupEntity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @return the range of erc group entities
	 */
	@Override
	public List<ERCGroupEntity> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc group entities
	 */
	@Override
	public List<ERCGroupEntity> findAll(
		int start, int end,
		OrderByComparator<ERCGroupEntity> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc group entities
	 */
	@Override
	public List<ERCGroupEntity> findAll(
		int start, int end, OrderByComparator<ERCGroupEntity> orderByComparator,
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

		List<ERCGroupEntity> list = null;

		if (useFinderCache) {
			list = (List<ERCGroupEntity>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ERCGROUPENTITY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ERCGROUPENTITY;

				sql = sql.concat(ERCGroupEntityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ERCGroupEntity>)QueryUtil.list(
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
	 * Removes all the erc group entities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ERCGroupEntity ercGroupEntity : findAll()) {
			remove(ercGroupEntity);
		}
	}

	/**
	 * Returns the number of erc group entities.
	 *
	 * @return the number of erc group entities
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ERCGROUPENTITY);

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
		return "ercGroupEntityId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ERCGROUPENTITY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ERCGroupEntityModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the erc group entity persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			ERCGroupEntityPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new ERCGroupEntityModelArgumentsResolver(),
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
		entityCache.removeCache(ERCGroupEntityImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ERCGROUPENTITY =
		"SELECT ercGroupEntity FROM ERCGroupEntity ercGroupEntity";

	private static final String _SQL_SELECT_ERCGROUPENTITY_WHERE =
		"SELECT ercGroupEntity FROM ERCGroupEntity ercGroupEntity WHERE ";

	private static final String _SQL_COUNT_ERCGROUPENTITY =
		"SELECT COUNT(ercGroupEntity) FROM ERCGroupEntity ercGroupEntity";

	private static final String _SQL_COUNT_ERCGROUPENTITY_WHERE =
		"SELECT COUNT(ercGroupEntity) FROM ERCGroupEntity ercGroupEntity WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ercGroupEntity.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ERCGroupEntity exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ERCGroupEntity exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ERCGroupEntityPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class ERCGroupEntityModelArgumentsResolver
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

			ERCGroupEntityModelImpl ercGroupEntityModelImpl =
				(ERCGroupEntityModelImpl)baseModel;

			long columnBitmask = ercGroupEntityModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					ercGroupEntityModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						ercGroupEntityModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					ercGroupEntityModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return ERCGroupEntityImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return ERCGroupEntityTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			ERCGroupEntityModelImpl ercGroupEntityModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						ercGroupEntityModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = ercGroupEntityModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}