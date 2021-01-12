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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCCompanyEntityException;
import com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity;
import com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntityTable;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntityImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntityModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCCompanyEntityPersistence;

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
 * The persistence implementation for the erc company entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ERCCompanyEntityPersistenceImpl
	extends BasePersistenceImpl<ERCCompanyEntity>
	implements ERCCompanyEntityPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ERCCompanyEntityUtil</code> to access the erc company entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ERCCompanyEntityImpl.class.getName();

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
	 * Returns the erc company entity where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCCompanyEntityException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc company entity
	 * @throws NoSuchERCCompanyEntityException if a matching erc company entity could not be found
	 */
	@Override
	public ERCCompanyEntity findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchERCCompanyEntityException {

		ERCCompanyEntity ercCompanyEntity = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (ercCompanyEntity == null) {
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

			throw new NoSuchERCCompanyEntityException(sb.toString());
		}

		return ercCompanyEntity;
	}

	/**
	 * Returns the erc company entity where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc company entity, or <code>null</code> if a matching erc company entity could not be found
	 */
	@Override
	public ERCCompanyEntity fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the erc company entity where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc company entity, or <code>null</code> if a matching erc company entity could not be found
	 */
	@Override
	public ERCCompanyEntity fetchByC_ERC(
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

		if (result instanceof ERCCompanyEntity) {
			ERCCompanyEntity ercCompanyEntity = (ERCCompanyEntity)result;

			if ((companyId != ercCompanyEntity.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					ercCompanyEntity.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ERCCOMPANYENTITY_WHERE);

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

				List<ERCCompanyEntity> list = query.list();

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
								"ERCCompanyEntityPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ERCCompanyEntity ercCompanyEntity = list.get(0);

					result = ercCompanyEntity;

					cacheResult(ercCompanyEntity);
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
			return (ERCCompanyEntity)result;
		}
	}

	/**
	 * Removes the erc company entity where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc company entity that was removed
	 */
	@Override
	public ERCCompanyEntity removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchERCCompanyEntityException {

		ERCCompanyEntity ercCompanyEntity = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(ercCompanyEntity);
	}

	/**
	 * Returns the number of erc company entities where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc company entities
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ERCCOMPANYENTITY_WHERE);

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
		"ercCompanyEntity.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"ercCompanyEntity.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(ercCompanyEntity.externalReferenceCode IS NULL OR ercCompanyEntity.externalReferenceCode = '')";

	public ERCCompanyEntityPersistenceImpl() {
		setModelClass(ERCCompanyEntity.class);

		setModelImplClass(ERCCompanyEntityImpl.class);
		setModelPKClass(long.class);

		setTable(ERCCompanyEntityTable.INSTANCE);
	}

	/**
	 * Caches the erc company entity in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntity the erc company entity
	 */
	@Override
	public void cacheResult(ERCCompanyEntity ercCompanyEntity) {
		entityCache.putResult(
			ERCCompanyEntityImpl.class, ercCompanyEntity.getPrimaryKey(),
			ercCompanyEntity);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				ercCompanyEntity.getCompanyId(),
				ercCompanyEntity.getExternalReferenceCode()
			},
			ercCompanyEntity);
	}

	/**
	 * Caches the erc company entities in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntities the erc company entities
	 */
	@Override
	public void cacheResult(List<ERCCompanyEntity> ercCompanyEntities) {
		for (ERCCompanyEntity ercCompanyEntity : ercCompanyEntities) {
			if (entityCache.getResult(
					ERCCompanyEntityImpl.class,
					ercCompanyEntity.getPrimaryKey()) == null) {

				cacheResult(ercCompanyEntity);
			}
		}
	}

	/**
	 * Clears the cache for all erc company entities.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ERCCompanyEntityImpl.class);

		finderCache.clearCache(ERCCompanyEntityImpl.class);
	}

	/**
	 * Clears the cache for the erc company entity.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ERCCompanyEntity ercCompanyEntity) {
		entityCache.removeResult(ERCCompanyEntityImpl.class, ercCompanyEntity);
	}

	@Override
	public void clearCache(List<ERCCompanyEntity> ercCompanyEntities) {
		for (ERCCompanyEntity ercCompanyEntity : ercCompanyEntities) {
			entityCache.removeResult(
				ERCCompanyEntityImpl.class, ercCompanyEntity);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ERCCompanyEntityImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ERCCompanyEntityImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ERCCompanyEntityModelImpl ercCompanyEntityModelImpl) {

		Object[] args = new Object[] {
			ercCompanyEntityModelImpl.getCompanyId(),
			ercCompanyEntityModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, ercCompanyEntityModelImpl);
	}

	/**
	 * Creates a new erc company entity with the primary key. Does not add the erc company entity to the database.
	 *
	 * @param ercCompanyEntityId the primary key for the new erc company entity
	 * @return the new erc company entity
	 */
	@Override
	public ERCCompanyEntity create(long ercCompanyEntityId) {
		ERCCompanyEntity ercCompanyEntity = new ERCCompanyEntityImpl();

		ercCompanyEntity.setNew(true);
		ercCompanyEntity.setPrimaryKey(ercCompanyEntityId);

		ercCompanyEntity.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ercCompanyEntity;
	}

	/**
	 * Removes the erc company entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity that was removed
	 * @throws NoSuchERCCompanyEntityException if a erc company entity with the primary key could not be found
	 */
	@Override
	public ERCCompanyEntity remove(long ercCompanyEntityId)
		throws NoSuchERCCompanyEntityException {

		return remove((Serializable)ercCompanyEntityId);
	}

	/**
	 * Removes the erc company entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the erc company entity
	 * @return the erc company entity that was removed
	 * @throws NoSuchERCCompanyEntityException if a erc company entity with the primary key could not be found
	 */
	@Override
	public ERCCompanyEntity remove(Serializable primaryKey)
		throws NoSuchERCCompanyEntityException {

		Session session = null;

		try {
			session = openSession();

			ERCCompanyEntity ercCompanyEntity = (ERCCompanyEntity)session.get(
				ERCCompanyEntityImpl.class, primaryKey);

			if (ercCompanyEntity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchERCCompanyEntityException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ercCompanyEntity);
		}
		catch (NoSuchERCCompanyEntityException noSuchEntityException) {
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
	protected ERCCompanyEntity removeImpl(ERCCompanyEntity ercCompanyEntity) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ercCompanyEntity)) {
				ercCompanyEntity = (ERCCompanyEntity)session.get(
					ERCCompanyEntityImpl.class,
					ercCompanyEntity.getPrimaryKeyObj());
			}

			if (ercCompanyEntity != null) {
				session.delete(ercCompanyEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ercCompanyEntity != null) {
			clearCache(ercCompanyEntity);
		}

		return ercCompanyEntity;
	}

	@Override
	public ERCCompanyEntity updateImpl(ERCCompanyEntity ercCompanyEntity) {
		boolean isNew = ercCompanyEntity.isNew();

		if (!(ercCompanyEntity instanceof ERCCompanyEntityModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ercCompanyEntity.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ercCompanyEntity);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ercCompanyEntity proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ERCCompanyEntity implementation " +
					ercCompanyEntity.getClass());
		}

		ERCCompanyEntityModelImpl ercCompanyEntityModelImpl =
			(ERCCompanyEntityModelImpl)ercCompanyEntity;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ercCompanyEntity);
			}
			else {
				ercCompanyEntity = (ERCCompanyEntity)session.merge(
					ercCompanyEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ERCCompanyEntityImpl.class, ercCompanyEntityModelImpl, false, true);

		cacheUniqueFindersCache(ercCompanyEntityModelImpl);

		if (isNew) {
			ercCompanyEntity.setNew(false);
		}

		ercCompanyEntity.resetOriginalValues();

		return ercCompanyEntity;
	}

	/**
	 * Returns the erc company entity with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the erc company entity
	 * @return the erc company entity
	 * @throws NoSuchERCCompanyEntityException if a erc company entity with the primary key could not be found
	 */
	@Override
	public ERCCompanyEntity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchERCCompanyEntityException {

		ERCCompanyEntity ercCompanyEntity = fetchByPrimaryKey(primaryKey);

		if (ercCompanyEntity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchERCCompanyEntityException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ercCompanyEntity;
	}

	/**
	 * Returns the erc company entity with the primary key or throws a <code>NoSuchERCCompanyEntityException</code> if it could not be found.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity
	 * @throws NoSuchERCCompanyEntityException if a erc company entity with the primary key could not be found
	 */
	@Override
	public ERCCompanyEntity findByPrimaryKey(long ercCompanyEntityId)
		throws NoSuchERCCompanyEntityException {

		return findByPrimaryKey((Serializable)ercCompanyEntityId);
	}

	/**
	 * Returns the erc company entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity, or <code>null</code> if a erc company entity with the primary key could not be found
	 */
	@Override
	public ERCCompanyEntity fetchByPrimaryKey(long ercCompanyEntityId) {
		return fetchByPrimaryKey((Serializable)ercCompanyEntityId);
	}

	/**
	 * Returns all the erc company entities.
	 *
	 * @return the erc company entities
	 */
	@Override
	public List<ERCCompanyEntity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the erc company entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entities
	 * @param end the upper bound of the range of erc company entities (not inclusive)
	 * @return the range of erc company entities
	 */
	@Override
	public List<ERCCompanyEntity> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the erc company entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entities
	 * @param end the upper bound of the range of erc company entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc company entities
	 */
	@Override
	public List<ERCCompanyEntity> findAll(
		int start, int end,
		OrderByComparator<ERCCompanyEntity> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the erc company entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entities
	 * @param end the upper bound of the range of erc company entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc company entities
	 */
	@Override
	public List<ERCCompanyEntity> findAll(
		int start, int end,
		OrderByComparator<ERCCompanyEntity> orderByComparator,
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

		List<ERCCompanyEntity> list = null;

		if (useFinderCache) {
			list = (List<ERCCompanyEntity>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ERCCOMPANYENTITY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ERCCOMPANYENTITY;

				sql = sql.concat(ERCCompanyEntityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ERCCompanyEntity>)QueryUtil.list(
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
	 * Removes all the erc company entities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ERCCompanyEntity ercCompanyEntity : findAll()) {
			remove(ercCompanyEntity);
		}
	}

	/**
	 * Returns the number of erc company entities.
	 *
	 * @return the number of erc company entities
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ERCCOMPANYENTITY);

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
		return "ercCompanyEntityId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ERCCOMPANYENTITY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ERCCompanyEntityModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the erc company entity persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			ERCCompanyEntityPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new ERCCompanyEntityModelArgumentsResolver(),
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
		entityCache.removeCache(ERCCompanyEntityImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ERCCOMPANYENTITY =
		"SELECT ercCompanyEntity FROM ERCCompanyEntity ercCompanyEntity";

	private static final String _SQL_SELECT_ERCCOMPANYENTITY_WHERE =
		"SELECT ercCompanyEntity FROM ERCCompanyEntity ercCompanyEntity WHERE ";

	private static final String _SQL_COUNT_ERCCOMPANYENTITY =
		"SELECT COUNT(ercCompanyEntity) FROM ERCCompanyEntity ercCompanyEntity";

	private static final String _SQL_COUNT_ERCCOMPANYENTITY_WHERE =
		"SELECT COUNT(ercCompanyEntity) FROM ERCCompanyEntity ercCompanyEntity WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ercCompanyEntity.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ERCCompanyEntity exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ERCCompanyEntity exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ERCCompanyEntityPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class ERCCompanyEntityModelArgumentsResolver
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

			ERCCompanyEntityModelImpl ercCompanyEntityModelImpl =
				(ERCCompanyEntityModelImpl)baseModel;

			long columnBitmask = ercCompanyEntityModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					ercCompanyEntityModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						ercCompanyEntityModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					ercCompanyEntityModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return ERCCompanyEntityImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return ERCCompanyEntityTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			ERCCompanyEntityModelImpl ercCompanyEntityModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						ercCompanyEntityModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = ercCompanyEntityModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}