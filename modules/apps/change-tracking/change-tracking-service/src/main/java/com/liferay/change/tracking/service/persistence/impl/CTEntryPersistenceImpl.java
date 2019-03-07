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

package com.liferay.change.tracking.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.exception.NoSuchEntryException;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.impl.CTEntryImpl;
import com.liferay.change.tracking.model.impl.CTEntryModelImpl;
import com.liferay.change.tracking.service.persistence.CTCollectionPersistence;
import com.liferay.change.tracking.service.persistence.CTEntryAggregatePersistence;
import com.liferay.change.tracking.service.persistence.CTEntryPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the ct entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class CTEntryPersistenceImpl
	extends BasePersistenceImpl<CTEntry> implements CTEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTEntryUtil</code> to access the ct entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_C(long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_C(modelClassNameId, modelClassPK);

		if (ctEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("modelClassNameId=");
			msg.append(modelClassNameId);

			msg.append(", modelClassPK=");
			msg.append(modelClassPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_C(long modelClassNameId, long modelClassPK) {
		return fetchByC_C(modelClassNameId, modelClassPK, true);
	}

	/**
	 * Returns the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_C(
		long modelClassNameId, long modelClassPK, boolean retrieveFromCache) {

		Object[] finderArgs = new Object[] {modelClassNameId, modelClassPK};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof CTEntry) {
			CTEntry ctEntry = (CTEntry)result;

			if ((modelClassNameId != ctEntry.getModelClassNameId()) ||
				(modelClassPK != ctEntry.getModelClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_MODELCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

				List<CTEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByC_C, finderArgs, list);
				}
				else {
					CTEntry ctEntry = list.get(0);

					result = ctEntry;

					cacheResult(ctEntry);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByC_C, finderArgs);

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
			return (CTEntry)result;
		}
	}

	/**
	 * Removes the ct entry where modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the ct entry that was removed
	 */
	@Override
	public CTEntry removeByC_C(long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByC_C(modelClassNameId, modelClassPK);

		return remove(ctEntry);
	}

	/**
	 * Returns the number of ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_C(long modelClassNameId, long modelClassPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {modelClassNameId, modelClassPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_MODELCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

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

	private static final String _FINDER_COLUMN_C_C_MODELCLASSNAMEID_2 =
		"ctEntry.modelClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_MODELCLASSPK_2 =
		"ctEntry.modelClassPK = ?";

	public CTEntryPersistenceImpl() {
		setModelClass(CTEntry.class);

		setModelImplClass(CTEntryImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(CTEntryModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	@Override
	public void cacheResult(CTEntry ctEntry) {
		entityCache.putResult(
			CTEntryModelImpl.ENTITY_CACHE_ENABLED, CTEntryImpl.class,
			ctEntry.getPrimaryKey(), ctEntry);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK()
			},
			ctEntry);

		ctEntry.resetOriginalValues();
	}

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	@Override
	public void cacheResult(List<CTEntry> ctEntries) {
		for (CTEntry ctEntry : ctEntries) {
			if (entityCache.getResult(
					CTEntryModelImpl.ENTITY_CACHE_ENABLED, CTEntryImpl.class,
					ctEntry.getPrimaryKey()) == null) {

				cacheResult(ctEntry);
			}
			else {
				ctEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ct entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ct entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTEntry ctEntry) {
		entityCache.removeResult(
			CTEntryModelImpl.ENTITY_CACHE_ENABLED, CTEntryImpl.class,
			ctEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CTEntryModelImpl)ctEntry, true);
	}

	@Override
	public void clearCache(List<CTEntry> ctEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTEntry ctEntry : ctEntries) {
			entityCache.removeResult(
				CTEntryModelImpl.ENTITY_CACHE_ENABLED, CTEntryImpl.class,
				ctEntry.getPrimaryKey());

			clearUniqueFindersCache((CTEntryModelImpl)ctEntry, true);
		}
	}

	protected void cacheUniqueFindersCache(CTEntryModelImpl ctEntryModelImpl) {
		Object[] args = new Object[] {
			ctEntryModelImpl.getModelClassNameId(),
			ctEntryModelImpl.getModelClassPK()
		};

		finderCache.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C, args, ctEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CTEntryModelImpl ctEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ctEntryModelImpl.getModelClassNameId(),
				ctEntryModelImpl.getModelClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}

		if ((ctEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ctEntryModelImpl.getOriginalModelClassNameId(),
				ctEntryModelImpl.getOriginalModelClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}
	}

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	@Override
	public CTEntry create(long ctEntryId) {
		CTEntry ctEntry = new CTEntryImpl();

		ctEntry.setNew(true);
		ctEntry.setPrimaryKey(ctEntryId);

		ctEntry.setCompanyId(companyProvider.getCompanyId());

		return ctEntry;
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry remove(long ctEntryId) throws NoSuchEntryException {
		return remove((Serializable)ctEntryId);
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry remove(Serializable primaryKey) throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			CTEntry ctEntry = (CTEntry)session.get(
				CTEntryImpl.class, primaryKey);

			if (ctEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected CTEntry removeImpl(CTEntry ctEntry) {
		ctEntryToCTEntryAggregateTableMapper.deleteLeftPrimaryKeyTableMappings(
			ctEntry.getPrimaryKey());

		ctEntryToCTCollectionTableMapper.deleteLeftPrimaryKeyTableMappings(
			ctEntry.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctEntry)) {
				ctEntry = (CTEntry)session.get(
					CTEntryImpl.class, ctEntry.getPrimaryKeyObj());
			}

			if (ctEntry != null) {
				session.delete(ctEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ctEntry != null) {
			clearCache(ctEntry);
		}

		return ctEntry;
	}

	@Override
	public CTEntry updateImpl(CTEntry ctEntry) {
		boolean isNew = ctEntry.isNew();

		if (!(ctEntry instanceof CTEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTEntry implementation " +
					ctEntry.getClass());
		}

		CTEntryModelImpl ctEntryModelImpl = (CTEntryModelImpl)ctEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ctEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				ctEntry.setCreateDate(now);
			}
			else {
				ctEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ctEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ctEntry.setModifiedDate(now);
			}
			else {
				ctEntry.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctEntry.isNew()) {
				session.save(ctEntry);

				ctEntry.setNew(false);
			}
			else {
				ctEntry = (CTEntry)session.merge(ctEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CTEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			CTEntryModelImpl.ENTITY_CACHE_ENABLED, CTEntryImpl.class,
			ctEntry.getPrimaryKey(), ctEntry, false);

		clearUniqueFindersCache(ctEntryModelImpl, false);
		cacheUniqueFindersCache(ctEntryModelImpl);

		ctEntry.resetOriginalValues();

		return ctEntry;
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByPrimaryKey(primaryKey);

		if (ctEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry findByPrimaryKey(long ctEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)ctEntryId);
	}

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry fetchByPrimaryKey(long ctEntryId) {
		return fetchByPrimaryKey((Serializable)ctEntryId);
	}

	/**
	 * Returns all the ct entries.
	 *
	 * @return the ct entries
	 */
	@Override
	public List<CTEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CTENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CTENTRY;

				if (pagination) {
					sql = sql.concat(CTEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Removes all the ct entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTEntry ctEntry : findAll()) {
			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CTENTRY);

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

	/**
	 * Returns the primaryKeys of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return long[] of the primaryKeys of ct entry aggregates associated with the ct entry
	 */
	@Override
	public long[] getCTEntryAggregatePrimaryKeys(long pk) {
		long[] pks = ctEntryToCTEntryAggregateTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the ct entry aggregates associated with the ct entry
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryAggregates(long pk) {

		return getCTEntryAggregates(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the ct entry aggregates associated with the ct entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entry aggregates associated with the ct entry
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryAggregates(long pk, int start, int end) {

		return getCTEntryAggregates(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates associated with the ct entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entry aggregates associated with the ct entry
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryAggregates(
			long pk, int start, int end,
			OrderByComparator
				<com.liferay.change.tracking.model.CTEntryAggregate>
					orderByComparator) {

		return ctEntryToCTEntryAggregateTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the number of ct entry aggregates associated with the ct entry
	 */
	@Override
	public int getCTEntryAggregatesSize(long pk) {
		long[] pks = ctEntryToCTEntryAggregateTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct entry aggregate is associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 * @return <code>true</code> if the ct entry aggregate is associated with the ct entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		return ctEntryToCTEntryAggregateTableMapper.containsTableMapping(
			pk, ctEntryAggregatePK);
	}

	/**
	 * Returns <code>true</code> if the ct entry has any ct entry aggregates associated with it.
	 *
	 * @param pk the primary key of the ct entry to check for associations with ct entry aggregates
	 * @return <code>true</code> if the ct entry has any ct entry aggregates associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntryAggregates(long pk) {
		if (getCTEntryAggregatesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	@Override
	public void addCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctEntryAggregatePK);
		}
		else {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctEntryAggregatePK);
		}
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	@Override
	public void addCTEntryAggregate(
		long pk,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk,
				ctEntryAggregate.getPrimaryKey());
		}
		else {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctEntryAggregate.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	@Override
	public void addCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTEntryAggregateTableMapper.addTableMappings(
			companyId, pk, ctEntryAggregatePKs);
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	@Override
	public void addCTEntryAggregates(
		long pk,
		List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		addCTEntryAggregates(
			pk,
			ListUtil.toLongArray(
				ctEntryAggregates,
				com.liferay.change.tracking.model.CTEntryAggregate.
					CT_ENTRY_AGGREGATE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the ct entry and its ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry to clear the associated ct entry aggregates from
	 */
	@Override
	public void clearCTEntryAggregates(long pk) {
		ctEntryToCTEntryAggregateTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	@Override
	public void removeCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		ctEntryToCTEntryAggregateTableMapper.deleteTableMapping(
			pk, ctEntryAggregatePK);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	@Override
	public void removeCTEntryAggregate(
		long pk,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		ctEntryToCTEntryAggregateTableMapper.deleteTableMapping(
			pk, ctEntryAggregate.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	@Override
	public void removeCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		ctEntryToCTEntryAggregateTableMapper.deleteTableMappings(
			pk, ctEntryAggregatePKs);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	@Override
	public void removeCTEntryAggregates(
		long pk,
		List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		removeCTEntryAggregates(
			pk,
			ListUtil.toLongArray(
				ctEntryAggregates,
				com.liferay.change.tracking.model.CTEntryAggregate.
					CT_ENTRY_AGGREGATE_ID_ACCESSOR));
	}

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates to be associated with the ct entry
	 */
	@Override
	public void setCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		Set<Long> newCTEntryAggregatePKsSet = SetUtil.fromArray(
			ctEntryAggregatePKs);
		Set<Long> oldCTEntryAggregatePKsSet = SetUtil.fromArray(
			ctEntryToCTEntryAggregateTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeCTEntryAggregatePKsSet = new HashSet<Long>(
			oldCTEntryAggregatePKsSet);

		removeCTEntryAggregatePKsSet.removeAll(newCTEntryAggregatePKsSet);

		ctEntryToCTEntryAggregateTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeCTEntryAggregatePKsSet));

		newCTEntryAggregatePKsSet.removeAll(oldCTEntryAggregatePKsSet);

		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTEntryAggregateTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newCTEntryAggregatePKsSet));
	}

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates to be associated with the ct entry
	 */
	@Override
	public void setCTEntryAggregates(
		long pk,
		List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		try {
			long[] ctEntryAggregatePKs = new long[ctEntryAggregates.size()];

			for (int i = 0; i < ctEntryAggregates.size(); i++) {
				com.liferay.change.tracking.model.CTEntryAggregate
					ctEntryAggregate = ctEntryAggregates.get(i);

				ctEntryAggregatePKs[i] = ctEntryAggregate.getPrimaryKey();
			}

			setCTEntryAggregates(pk, ctEntryAggregatePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	/**
	 * Returns the primaryKeys of ct collections associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return long[] of the primaryKeys of ct collections associated with the ct entry
	 */
	@Override
	public long[] getCTCollectionPrimaryKeys(long pk) {
		long[] pks = ctEntryToCTCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct collections associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the ct collections associated with the ct entry
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(long pk) {

		return getCTCollections(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the ct collections associated with the ct entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct collections associated with the ct entry
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(long pk, int start, int end) {

		return getCTCollections(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct collections associated with the ct entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct collections associated with the ct entry
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(
			long pk, int start, int end,
			OrderByComparator<com.liferay.change.tracking.model.CTCollection>
				orderByComparator) {

		return ctEntryToCTCollectionTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct collections associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the number of ct collections associated with the ct entry
	 */
	@Override
	public int getCTCollectionsSize(long pk) {
		long[] pks = ctEntryToCTCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct collection is associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPK the primary key of the ct collection
	 * @return <code>true</code> if the ct collection is associated with the ct entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTCollection(long pk, long ctCollectionPK) {
		return ctEntryToCTCollectionTableMapper.containsTableMapping(
			pk, ctCollectionPK);
	}

	/**
	 * Returns <code>true</code> if the ct entry has any ct collections associated with it.
	 *
	 * @param pk the primary key of the ct entry to check for associations with ct collections
	 * @return <code>true</code> if the ct entry has any ct collections associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTCollections(long pk) {
		if (getCTCollectionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	@Override
	public void addCTCollection(long pk, long ctCollectionPK) {
		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTCollectionTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctCollectionPK);
		}
		else {
			ctEntryToCTCollectionTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctCollectionPK);
		}
	}

	/**
	 * Adds an association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollection the ct collection
	 */
	@Override
	public void addCTCollection(
		long pk, com.liferay.change.tracking.model.CTCollection ctCollection) {

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTCollectionTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk,
				ctCollection.getPrimaryKey());
		}
		else {
			ctEntryToCTCollectionTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctCollection.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	@Override
	public void addCTCollections(long pk, long[] ctCollectionPKs) {
		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTCollectionTableMapper.addTableMappings(
			companyId, pk, ctCollectionPKs);
	}

	/**
	 * Adds an association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollections the ct collections
	 */
	@Override
	public void addCTCollections(
		long pk,
		List<com.liferay.change.tracking.model.CTCollection> ctCollections) {

		addCTCollections(
			pk,
			ListUtil.toLongArray(
				ctCollections,
				com.liferay.change.tracking.model.CTCollection.
					CT_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the ct entry and its ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry to clear the associated ct collections from
	 */
	@Override
	public void clearCTCollections(long pk) {
		ctEntryToCTCollectionTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	@Override
	public void removeCTCollection(long pk, long ctCollectionPK) {
		ctEntryToCTCollectionTableMapper.deleteTableMapping(pk, ctCollectionPK);
	}

	/**
	 * Removes the association between the ct entry and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollection the ct collection
	 */
	@Override
	public void removeCTCollection(
		long pk, com.liferay.change.tracking.model.CTCollection ctCollection) {

		ctEntryToCTCollectionTableMapper.deleteTableMapping(
			pk, ctCollection.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	@Override
	public void removeCTCollections(long pk, long[] ctCollectionPKs) {
		ctEntryToCTCollectionTableMapper.deleteTableMappings(
			pk, ctCollectionPKs);
	}

	/**
	 * Removes the association between the ct entry and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollections the ct collections
	 */
	@Override
	public void removeCTCollections(
		long pk,
		List<com.liferay.change.tracking.model.CTCollection> ctCollections) {

		removeCTCollections(
			pk,
			ListUtil.toLongArray(
				ctCollections,
				com.liferay.change.tracking.model.CTCollection.
					CT_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Sets the ct collections associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollectionPKs the primary keys of the ct collections to be associated with the ct entry
	 */
	@Override
	public void setCTCollections(long pk, long[] ctCollectionPKs) {
		Set<Long> newCTCollectionPKsSet = SetUtil.fromArray(ctCollectionPKs);
		Set<Long> oldCTCollectionPKsSet = SetUtil.fromArray(
			ctEntryToCTCollectionTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeCTCollectionPKsSet = new HashSet<Long>(
			oldCTCollectionPKsSet);

		removeCTCollectionPKsSet.removeAll(newCTCollectionPKsSet);

		ctEntryToCTCollectionTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeCTCollectionPKsSet));

		newCTCollectionPKsSet.removeAll(oldCTCollectionPKsSet);

		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTCollectionTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newCTCollectionPKsSet));
	}

	/**
	 * Sets the ct collections associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctCollections the ct collections to be associated with the ct entry
	 */
	@Override
	public void setCTCollections(
		long pk,
		List<com.liferay.change.tracking.model.CTCollection> ctCollections) {

		try {
			long[] ctCollectionPKs = new long[ctCollections.size()];

			for (int i = 0; i < ctCollections.size(); i++) {
				com.liferay.change.tracking.model.CTCollection ctCollection =
					ctCollections.get(i);

				ctCollectionPKs[i] = ctCollection.getPrimaryKey();
			}

			setCTCollections(pk, ctCollectionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ctEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct entry persistence.
	 */
	public void afterPropertiesSet() {
		ctEntryToCTEntryAggregateTableMapper =
			TableMapperFactory.getTableMapper(
				"CTEntryAggregates_CTEntries", "companyId", "ctEntryId",
				"ctEntryAggregateId", this, ctEntryAggregatePersistence);

		ctEntryToCTCollectionTableMapper = TableMapperFactory.getTableMapper(
			"CTCollections_CTEntries", "companyId", "ctEntryId",
			"ctCollectionId", this, ctCollectionPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			CTEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryModelImpl.FINDER_CACHE_ENABLED, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CTEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryModelImpl.FINDER_CACHE_ENABLED, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CTEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByC_C = new FinderPath(
			CTEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryModelImpl.FINDER_CACHE_ENABLED, CTEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			CTEntryModelImpl.MODELCLASSNAMEID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELCLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			CTEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(CTEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("CTEntryAggregates_CTEntries");
		TableMapperFactory.removeTableMapper("CTCollections_CTEntries");
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	@BeanReference(type = CTEntryAggregatePersistence.class)
	protected CTEntryAggregatePersistence ctEntryAggregatePersistence;

	protected TableMapper
		<CTEntry, com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryToCTEntryAggregateTableMapper;

	@BeanReference(type = CTCollectionPersistence.class)
	protected CTCollectionPersistence ctCollectionPersistence;

	protected TableMapper
		<CTEntry, com.liferay.change.tracking.model.CTCollection>
			ctEntryToCTCollectionTableMapper;

	private static final String _SQL_SELECT_CTENTRY =
		"SELECT ctEntry FROM CTEntry ctEntry";

	private static final String _SQL_SELECT_CTENTRY_WHERE =
		"SELECT ctEntry FROM CTEntry ctEntry WHERE ";

	private static final String _SQL_COUNT_CTENTRY =
		"SELECT COUNT(ctEntry) FROM CTEntry ctEntry";

	private static final String _SQL_COUNT_CTENTRY_WHERE =
		"SELECT COUNT(ctEntry) FROM CTEntry ctEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTEntryPersistenceImpl.class);

}