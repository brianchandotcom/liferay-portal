/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.RepositoryPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchSyncException;
import com.liferay.portlet.documentlibrary.model.DLSync;
import com.liferay.portlet.documentlibrary.model.impl.DLSyncImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLSyncModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The persistence implementation for the d l sync service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncPersistence
 * @see DLSyncUtil
 * @generated
 */
public class DLSyncPersistenceImpl extends BasePersistenceImpl<DLSync>
	implements DLSyncPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLSyncUtil} to access the d l sync persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLSyncImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_FILEID = new FinderPath(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncModelImpl.FINDER_CACHE_ENABLED, DLSyncImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByFileId",
			new String[] { Long.class.getName() },
			DLSyncModelImpl.FILEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEID = new FinderPath(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_M_R = new FinderPath(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncModelImpl.FINDER_CACHE_ENABLED, DLSyncImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_M_R",
			new String[] {
				Long.class.getName(), Date.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_M_R = new FinderPath(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_M_R",
			new String[] {
				Long.class.getName(), Date.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncModelImpl.FINDER_CACHE_ENABLED, DLSyncImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncModelImpl.FINDER_CACHE_ENABLED, DLSyncImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the d l sync in the entity cache if it is enabled.
	 *
	 * @param dlSync the d l sync
	 */
	public void cacheResult(DLSync dlSync) {
		EntityCacheUtil.putResult(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncImpl.class, dlSync.getPrimaryKey(), dlSync);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FILEID,
			new Object[] { Long.valueOf(dlSync.getFileId()) }, dlSync);

		dlSync.resetOriginalValues();
	}

	/**
	 * Caches the d l syncs in the entity cache if it is enabled.
	 *
	 * @param dlSyncs the d l syncs
	 */
	public void cacheResult(List<DLSync> dlSyncs) {
		for (DLSync dlSync : dlSyncs) {
			if (EntityCacheUtil.getResult(
						DLSyncModelImpl.ENTITY_CACHE_ENABLED, DLSyncImpl.class,
						dlSync.getPrimaryKey()) == null) {
				cacheResult(dlSync);
			}
			else {
				dlSync.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d l syncs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLSyncImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLSyncImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d l sync.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLSync dlSync) {
		EntityCacheUtil.removeResult(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncImpl.class, dlSync.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(dlSync);
	}

	@Override
	public void clearCache(List<DLSync> dlSyncs) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLSync dlSync : dlSyncs) {
			EntityCacheUtil.removeResult(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
				DLSyncImpl.class, dlSync.getPrimaryKey());

			clearUniqueFindersCache(dlSync);
		}
	}

	protected void clearUniqueFindersCache(DLSync dlSync) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FILEID,
			new Object[] { Long.valueOf(dlSync.getFileId()) });
	}

	/**
	 * Creates a new d l sync with the primary key. Does not add the d l sync to the database.
	 *
	 * @param syncId the primary key for the new d l sync
	 * @return the new d l sync
	 */
	public DLSync create(long syncId) {
		DLSync dlSync = new DLSyncImpl();

		dlSync.setNew(true);
		dlSync.setPrimaryKey(syncId);

		return dlSync;
	}

	/**
	 * Removes the d l sync with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncId the primary key of the d l sync
	 * @return the d l sync that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a d l sync with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync remove(long syncId)
		throws NoSuchSyncException, SystemException {
		return remove(Long.valueOf(syncId));
	}

	/**
	 * Removes the d l sync with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d l sync
	 * @return the d l sync that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a d l sync with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLSync remove(Serializable primaryKey)
		throws NoSuchSyncException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLSync dlSync = (DLSync)session.get(DLSyncImpl.class, primaryKey);

			if (dlSync == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSyncException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlSync);
		}
		catch (NoSuchSyncException nsee) {
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
	protected DLSync removeImpl(DLSync dlSync) throws SystemException {
		dlSync = toUnwrappedModel(dlSync);

		Session session = null;

		try {
			session = openSession();

			if (dlSync.isCachedModel()) {
				dlSync = (DLSync)session.get(DLSyncImpl.class,
						dlSync.getPrimaryKeyObj());
			}

			if (dlSync != null) {
				session.delete(dlSync);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlSync != null) {
			clearCache(dlSync);
		}

		return dlSync;
	}

	@Override
	public DLSync updateImpl(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync)
		throws SystemException {
		dlSync = toUnwrappedModel(dlSync);

		boolean isNew = dlSync.isNew();

		DLSyncModelImpl dlSyncModelImpl = (DLSyncModelImpl)dlSync;

		Session session = null;

		try {
			session = openSession();

			if (dlSync.isNew()) {
				session.save(dlSync);

				dlSync.setNew(false);
			}
			else {
				session.merge(dlSync);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLSyncModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
			DLSyncImpl.class, dlSync.getPrimaryKey(), dlSync);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FILEID,
				new Object[] { Long.valueOf(dlSync.getFileId()) }, dlSync);
		}
		else {
			if ((dlSyncModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_FILEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlSyncModelImpl.getOriginalFileId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FILEID, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FILEID, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FILEID,
					new Object[] { Long.valueOf(dlSync.getFileId()) }, dlSync);
			}
		}

		return dlSync;
	}

	protected DLSync toUnwrappedModel(DLSync dlSync) {
		if (dlSync instanceof DLSyncImpl) {
			return dlSync;
		}

		DLSyncImpl dlSyncImpl = new DLSyncImpl();

		dlSyncImpl.setNew(dlSync.isNew());
		dlSyncImpl.setPrimaryKey(dlSync.getPrimaryKey());

		dlSyncImpl.setSyncId(dlSync.getSyncId());
		dlSyncImpl.setCompanyId(dlSync.getCompanyId());
		dlSyncImpl.setCreateDate(dlSync.getCreateDate());
		dlSyncImpl.setModifiedDate(dlSync.getModifiedDate());
		dlSyncImpl.setFileId(dlSync.getFileId());
		dlSyncImpl.setFileUuid(dlSync.getFileUuid());
		dlSyncImpl.setRepositoryId(dlSync.getRepositoryId());
		dlSyncImpl.setParentFolderId(dlSync.getParentFolderId());
		dlSyncImpl.setName(dlSync.getName());
		dlSyncImpl.setDescription(dlSync.getDescription());
		dlSyncImpl.setEvent(dlSync.getEvent());
		dlSyncImpl.setType(dlSync.getType());
		dlSyncImpl.setVersion(dlSync.getVersion());

		return dlSyncImpl;
	}

	/**
	 * Returns the d l sync with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l sync
	 * @return the d l sync
	 * @throws com.liferay.portal.NoSuchModelException if a d l sync with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLSync findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d l sync with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchSyncException} if it could not be found.
	 *
	 * @param syncId the primary key of the d l sync
	 * @return the d l sync
	 * @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a d l sync with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync findByPrimaryKey(long syncId)
		throws NoSuchSyncException, SystemException {
		DLSync dlSync = fetchByPrimaryKey(syncId);

		if (dlSync == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + syncId);
			}

			throw new NoSuchSyncException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				syncId);
		}

		return dlSync;
	}

	/**
	 * Returns the d l sync with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d l sync
	 * @return the d l sync, or <code>null</code> if a d l sync with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLSync fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d l sync with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncId the primary key of the d l sync
	 * @return the d l sync, or <code>null</code> if a d l sync with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync fetchByPrimaryKey(long syncId) throws SystemException {
		DLSync dlSync = (DLSync)EntityCacheUtil.getResult(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
				DLSyncImpl.class, syncId);

		if (dlSync == _nullDLSync) {
			return null;
		}

		if (dlSync == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				dlSync = (DLSync)session.get(DLSyncImpl.class,
						Long.valueOf(syncId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (dlSync != null) {
					cacheResult(dlSync);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DLSyncModelImpl.ENTITY_CACHE_ENABLED,
						DLSyncImpl.class, syncId, _nullDLSync);
				}

				closeSession(session);
			}
		}

		return dlSync;
	}

	/**
	 * Returns the d l sync where fileId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchSyncException} if it could not be found.
	 *
	 * @param fileId the file ID
	 * @return the matching d l sync
	 * @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a matching d l sync could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync findByFileId(long fileId)
		throws NoSuchSyncException, SystemException {
		DLSync dlSync = fetchByFileId(fileId);

		if (dlSync == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileId=");
			msg.append(fileId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchSyncException(msg.toString());
		}

		return dlSync;
	}

	/**
	 * Returns the d l sync where fileId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileId the file ID
	 * @return the matching d l sync, or <code>null</code> if a matching d l sync could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync fetchByFileId(long fileId) throws SystemException {
		return fetchByFileId(fileId, true);
	}

	/**
	 * Returns the d l sync where fileId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileId the file ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d l sync, or <code>null</code> if a matching d l sync could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync fetchByFileId(long fileId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { fileId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_FILEID,
					finderArgs, this);
		}

		if (result instanceof DLSync) {
			DLSync dlSync = (DLSync)result;

			if ((fileId != dlSync.getFileId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DLSYNC_WHERE);

			query.append(_FINDER_COLUMN_FILEID_FILEID_2);

			query.append(DLSyncModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileId);

				List<DLSync> list = q.list();

				result = list;

				DLSync dlSync = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FILEID,
						finderArgs, list);
				}
				else {
					dlSync = list.get(0);

					cacheResult(dlSync);

					if ((dlSync.getFileId() != fileId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FILEID,
							finderArgs, dlSync);
					}
				}

				return dlSync;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FILEID,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DLSync)result;
			}
		}
	}

	/**
	 * Returns all the d l syncs where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @return the matching d l syncs
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLSync> findByC_M_R(long companyId, Date modifiedDate,
		long repositoryId) throws SystemException {
		return findByC_M_R(companyId, modifiedDate, repositoryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d l syncs where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of d l syncs
	 * @param end the upper bound of the range of d l syncs (not inclusive)
	 * @return the range of matching d l syncs
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLSync> findByC_M_R(long companyId, Date modifiedDate,
		long repositoryId, int start, int end) throws SystemException {
		return findByC_M_R(companyId, modifiedDate, repositoryId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the d l syncs where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of d l syncs
	 * @param end the upper bound of the range of d l syncs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d l syncs
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLSync> findByC_M_R(long companyId, Date modifiedDate,
		long repositoryId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_M_R;
		finderArgs = new Object[] {
				companyId, modifiedDate, repositoryId,
				
				start, end, orderByComparator
			};

		List<DLSync> list = (List<DLSync>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLSync dlSync : list) {
				if ((companyId != dlSync.getCompanyId()) ||
						!Validator.equals(modifiedDate, dlSync.getModifiedDate()) ||
						(repositoryId != dlSync.getRepositoryId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DLSYNC_WHERE);

			query.append(_FINDER_COLUMN_C_M_R_COMPANYID_2);

			if (modifiedDate == null) {
				query.append(_FINDER_COLUMN_C_M_R_MODIFIEDDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_C_M_R_MODIFIEDDATE_2);
			}

			query.append(_FINDER_COLUMN_C_M_R_REPOSITORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLSyncModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (modifiedDate != null) {
					qPos.add(CalendarUtil.getTimestamp(modifiedDate));
				}

				qPos.add(repositoryId);

				list = (List<DLSync>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d l sync in the ordered set where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d l sync
	 * @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a matching d l sync could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync findByC_M_R_First(long companyId, Date modifiedDate,
		long repositoryId, OrderByComparator orderByComparator)
		throws NoSuchSyncException, SystemException {
		DLSync dlSync = fetchByC_M_R_First(companyId, modifiedDate,
				repositoryId, orderByComparator);

		if (dlSync != null) {
			return dlSync;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", modifiedDate=");
		msg.append(modifiedDate);

		msg.append(", repositoryId=");
		msg.append(repositoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSyncException(msg.toString());
	}

	/**
	 * Returns the first d l sync in the ordered set where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d l sync, or <code>null</code> if a matching d l sync could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync fetchByC_M_R_First(long companyId, Date modifiedDate,
		long repositoryId, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLSync> list = findByC_M_R(companyId, modifiedDate, repositoryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d l sync in the ordered set where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d l sync
	 * @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a matching d l sync could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync findByC_M_R_Last(long companyId, Date modifiedDate,
		long repositoryId, OrderByComparator orderByComparator)
		throws NoSuchSyncException, SystemException {
		DLSync dlSync = fetchByC_M_R_Last(companyId, modifiedDate,
				repositoryId, orderByComparator);

		if (dlSync != null) {
			return dlSync;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", modifiedDate=");
		msg.append(modifiedDate);

		msg.append(", repositoryId=");
		msg.append(repositoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSyncException(msg.toString());
	}

	/**
	 * Returns the last d l sync in the ordered set where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d l sync, or <code>null</code> if a matching d l sync could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync fetchByC_M_R_Last(long companyId, Date modifiedDate,
		long repositoryId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_M_R(companyId, modifiedDate, repositoryId);

		List<DLSync> list = findByC_M_R(companyId, modifiedDate, repositoryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d l syncs before and after the current d l sync in the ordered set where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63;.
	 *
	 * @param syncId the primary key of the current d l sync
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d l sync
	 * @throws com.liferay.portlet.documentlibrary.NoSuchSyncException if a d l sync with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync[] findByC_M_R_PrevAndNext(long syncId, long companyId,
		Date modifiedDate, long repositoryId,
		OrderByComparator orderByComparator)
		throws NoSuchSyncException, SystemException {
		DLSync dlSync = findByPrimaryKey(syncId);

		Session session = null;

		try {
			session = openSession();

			DLSync[] array = new DLSyncImpl[3];

			array[0] = getByC_M_R_PrevAndNext(session, dlSync, companyId,
					modifiedDate, repositoryId, orderByComparator, true);

			array[1] = dlSync;

			array[2] = getByC_M_R_PrevAndNext(session, dlSync, companyId,
					modifiedDate, repositoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLSync getByC_M_R_PrevAndNext(Session session, DLSync dlSync,
		long companyId, Date modifiedDate, long repositoryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLSYNC_WHERE);

		query.append(_FINDER_COLUMN_C_M_R_COMPANYID_2);

		if (modifiedDate == null) {
			query.append(_FINDER_COLUMN_C_M_R_MODIFIEDDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_C_M_R_MODIFIEDDATE_2);
		}

		query.append(_FINDER_COLUMN_C_M_R_REPOSITORYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(DLSyncModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (modifiedDate != null) {
			qPos.add(CalendarUtil.getTimestamp(modifiedDate));
		}

		qPos.add(repositoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlSync);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLSync> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d l syncs.
	 *
	 * @return the d l syncs
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLSync> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d l syncs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l syncs
	 * @param end the upper bound of the range of d l syncs (not inclusive)
	 * @return the range of d l syncs
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLSync> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d l syncs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d l syncs
	 * @param end the upper bound of the range of d l syncs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d l syncs
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLSync> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<DLSync> list = (List<DLSync>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLSYNC);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLSYNC.concat(DLSyncModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLSync>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLSync>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes the d l sync where fileId = &#63; from the database.
	 *
	 * @param fileId the file ID
	 * @return the d l sync that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLSync removeByFileId(long fileId)
		throws NoSuchSyncException, SystemException {
		DLSync dlSync = findByFileId(fileId);

		return remove(dlSync);
	}

	/**
	 * Removes all the d l syncs where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_M_R(long companyId, Date modifiedDate,
		long repositoryId) throws SystemException {
		for (DLSync dlSync : findByC_M_R(companyId, modifiedDate, repositoryId)) {
			remove(dlSync);
		}
	}

	/**
	 * Removes all the d l syncs from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLSync dlSync : findAll()) {
			remove(dlSync);
		}
	}

	/**
	 * Returns the number of d l syncs where fileId = &#63;.
	 *
	 * @param fileId the file ID
	 * @return the number of matching d l syncs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFileId(long fileId) throws SystemException {
		Object[] finderArgs = new Object[] { fileId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FILEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLSYNC_WHERE);

			query.append(_FINDER_COLUMN_FILEID_FILEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FILEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d l syncs where companyId = &#63; and modifiedDate &ge; &#63; and repositoryId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param repositoryId the repository ID
	 * @return the number of matching d l syncs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_M_R(long companyId, Date modifiedDate, long repositoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, modifiedDate, repositoryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_M_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLSYNC_WHERE);

			query.append(_FINDER_COLUMN_C_M_R_COMPANYID_2);

			if (modifiedDate == null) {
				query.append(_FINDER_COLUMN_C_M_R_MODIFIEDDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_C_M_R_MODIFIEDDATE_2);
			}

			query.append(_FINDER_COLUMN_C_M_R_REPOSITORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (modifiedDate != null) {
					qPos.add(CalendarUtil.getTimestamp(modifiedDate));
				}

				qPos.add(repositoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_M_R,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d l syncs.
	 *
	 * @return the number of d l syncs
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLSYNC);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the d l sync persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLSync")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLSync>> listenersList = new ArrayList<ModelListener<DLSync>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLSync>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DLSyncImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = DLContentPersistence.class)
	protected DLContentPersistence dlContentPersistence;
	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFileEntryMetadataPersistence.class)
	protected DLFileEntryMetadataPersistence dlFileEntryMetadataPersistence;
	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	@BeanReference(type = DLFileRankPersistence.class)
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(type = DLFileShortcutPersistence.class)
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = DLSyncPersistence.class)
	protected DLSyncPersistence dlSyncPersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DLSYNC = "SELECT dlSync FROM DLSync dlSync";
	private static final String _SQL_SELECT_DLSYNC_WHERE = "SELECT dlSync FROM DLSync dlSync WHERE ";
	private static final String _SQL_COUNT_DLSYNC = "SELECT COUNT(dlSync) FROM DLSync dlSync";
	private static final String _SQL_COUNT_DLSYNC_WHERE = "SELECT COUNT(dlSync) FROM DLSync dlSync WHERE ";
	private static final String _FINDER_COLUMN_FILEID_FILEID_2 = "dlSync.fileId = ?";
	private static final String _FINDER_COLUMN_C_M_R_COMPANYID_2 = "dlSync.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_M_R_MODIFIEDDATE_1 = "dlSync.modifiedDate >= NULL AND ";
	private static final String _FINDER_COLUMN_C_M_R_MODIFIEDDATE_2 = "dlSync.modifiedDate >= ? AND ";
	private static final String _FINDER_COLUMN_C_M_R_REPOSITORYID_2 = "dlSync.repositoryId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlSync.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLSync exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLSync exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLSyncPersistenceImpl.class);
	private static DLSync _nullDLSync = new DLSyncImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLSync> toCacheModel() {
				return _nullDLSyncCacheModel;
			}
		};

	private static CacheModel<DLSync> _nullDLSyncCacheModel = new CacheModel<DLSync>() {
			public DLSync toEntityModel() {
				return _nullDLSync;
			}
		};
}