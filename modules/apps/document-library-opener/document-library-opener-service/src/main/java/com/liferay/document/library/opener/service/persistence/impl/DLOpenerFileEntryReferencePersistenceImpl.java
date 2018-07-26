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

package com.liferay.document.library.opener.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.opener.exception.NoSuchFileEntryReferenceException;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceImpl;
import com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceModelImpl;
import com.liferay.document.library.opener.service.persistence.DLOpenerFileEntryReferencePersistence;

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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the dl opener file entry reference service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLOpenerFileEntryReferencePersistence
 * @see com.liferay.document.library.opener.service.persistence.DLOpenerFileEntryReferenceUtil
 * @generated
 */
@ProviderType
public class DLOpenerFileEntryReferencePersistenceImpl
	extends BasePersistenceImpl<DLOpenerFileEntryReference>
	implements DLOpenerFileEntryReferencePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLOpenerFileEntryReferenceUtil} to access the dl opener file entry reference persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLOpenerFileEntryReferenceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
			DLOpenerFileEntryReferenceModelImpl.FINDER_CACHE_ENABLED,
			DLOpenerFileEntryReferenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
			DLOpenerFileEntryReferenceModelImpl.FINDER_CACHE_ENABLED,
			DLOpenerFileEntryReferenceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
			DLOpenerFileEntryReferenceModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_FILEENTRYID = new FinderPath(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
			DLOpenerFileEntryReferenceModelImpl.FINDER_CACHE_ENABLED,
			DLOpenerFileEntryReferenceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByFileEntryId", new String[] { Long.class.getName() },
			DLOpenerFileEntryReferenceModelImpl.FILEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEENTRYID = new FinderPath(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
			DLOpenerFileEntryReferenceModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFileEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or throws a {@link NoSuchFileEntryReferenceException} if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference
	 * @throws NoSuchFileEntryReferenceException if a matching dl opener file entry reference could not be found
	 */
	@Override
	public DLOpenerFileEntryReference findByFileEntryId(long fileEntryId)
		throws NoSuchFileEntryReferenceException {
		DLOpenerFileEntryReference dlOpenerFileEntryReference = fetchByFileEntryId(fileEntryId);

		if (dlOpenerFileEntryReference == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileEntryId=");
			msg.append(fileEntryId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFileEntryReferenceException(msg.toString());
		}

		return dlOpenerFileEntryReference;
	}

	/**
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	@Override
	public DLOpenerFileEntryReference fetchByFileEntryId(long fileEntryId) {
		return fetchByFileEntryId(fileEntryId, true);
	}

	/**
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	@Override
	public DLOpenerFileEntryReference fetchByFileEntryId(long fileEntryId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { fileEntryId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_FILEENTRYID,
					finderArgs, this);
		}

		if (result instanceof DLOpenerFileEntryReference) {
			DLOpenerFileEntryReference dlOpenerFileEntryReference = (DLOpenerFileEntryReference)result;

			if ((fileEntryId != dlOpenerFileEntryReference.getFileEntryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DLOPENERFILEENTRYREFERENCE_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				List<DLOpenerFileEntryReference> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_FILEENTRYID,
						finderArgs, list);
				}
				else {
					DLOpenerFileEntryReference dlOpenerFileEntryReference = list.get(0);

					result = dlOpenerFileEntryReference;

					cacheResult(dlOpenerFileEntryReference);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_FILEENTRYID,
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
			return (DLOpenerFileEntryReference)result;
		}
	}

	/**
	 * Removes the dl opener file entry reference where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the dl opener file entry reference that was removed
	 */
	@Override
	public DLOpenerFileEntryReference removeByFileEntryId(long fileEntryId)
		throws NoSuchFileEntryReferenceException {
		DLOpenerFileEntryReference dlOpenerFileEntryReference = findByFileEntryId(fileEntryId);

		return remove(dlOpenerFileEntryReference);
	}

	/**
	 * Returns the number of dl opener file entry references where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching dl opener file entry references
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEENTRYID;

		Object[] finderArgs = new Object[] { fileEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLOPENERFILEENTRYREFERENCE_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

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

	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 = "dlOpenerFileEntryReference.fileEntryId = ?";

	public DLOpenerFileEntryReferencePersistenceImpl() {
		setModelClass(DLOpenerFileEntryReference.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the dl opener file entry reference in the entity cache if it is enabled.
	 *
	 * @param dlOpenerFileEntryReference the dl opener file entry reference
	 */
	@Override
	public void cacheResult(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		entityCache.putResult(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
			DLOpenerFileEntryReferenceImpl.class,
			dlOpenerFileEntryReference.getPrimaryKey(),
			dlOpenerFileEntryReference);

		finderCache.putResult(FINDER_PATH_FETCH_BY_FILEENTRYID,
			new Object[] { dlOpenerFileEntryReference.getFileEntryId() },
			dlOpenerFileEntryReference);

		dlOpenerFileEntryReference.resetOriginalValues();
	}

	/**
	 * Caches the dl opener file entry references in the entity cache if it is enabled.
	 *
	 * @param dlOpenerFileEntryReferences the dl opener file entry references
	 */
	@Override
	public void cacheResult(
		List<DLOpenerFileEntryReference> dlOpenerFileEntryReferences) {
		for (DLOpenerFileEntryReference dlOpenerFileEntryReference : dlOpenerFileEntryReferences) {
			if (entityCache.getResult(
						DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
						DLOpenerFileEntryReferenceImpl.class,
						dlOpenerFileEntryReference.getPrimaryKey()) == null) {
				cacheResult(dlOpenerFileEntryReference);
			}
			else {
				dlOpenerFileEntryReference.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all dl opener file entry references.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLOpenerFileEntryReferenceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the dl opener file entry reference.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		entityCache.removeResult(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
			DLOpenerFileEntryReferenceImpl.class,
			dlOpenerFileEntryReference.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLOpenerFileEntryReferenceModelImpl)dlOpenerFileEntryReference,
			true);
	}

	@Override
	public void clearCache(
		List<DLOpenerFileEntryReference> dlOpenerFileEntryReferences) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLOpenerFileEntryReference dlOpenerFileEntryReference : dlOpenerFileEntryReferences) {
			entityCache.removeResult(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
				DLOpenerFileEntryReferenceImpl.class,
				dlOpenerFileEntryReference.getPrimaryKey());

			clearUniqueFindersCache((DLOpenerFileEntryReferenceModelImpl)dlOpenerFileEntryReference,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		DLOpenerFileEntryReferenceModelImpl dlOpenerFileEntryReferenceModelImpl) {
		Object[] args = new Object[] {
				dlOpenerFileEntryReferenceModelImpl.getFileEntryId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_FILEENTRYID, args,
			dlOpenerFileEntryReferenceModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DLOpenerFileEntryReferenceModelImpl dlOpenerFileEntryReferenceModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					dlOpenerFileEntryReferenceModelImpl.getFileEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_FILEENTRYID, args);
		}

		if ((dlOpenerFileEntryReferenceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_FILEENTRYID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					dlOpenerFileEntryReferenceModelImpl.getOriginalFileEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_FILEENTRYID, args);
		}
	}

	/**
	 * Creates a new dl opener file entry reference with the primary key. Does not add the dl opener file entry reference to the database.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key for the new dl opener file entry reference
	 * @return the new dl opener file entry reference
	 */
	@Override
	public DLOpenerFileEntryReference create(long dlOpenerFileEntryReferenceId) {
		DLOpenerFileEntryReference dlOpenerFileEntryReference = new DLOpenerFileEntryReferenceImpl();

		dlOpenerFileEntryReference.setNew(true);
		dlOpenerFileEntryReference.setPrimaryKey(dlOpenerFileEntryReferenceId);

		dlOpenerFileEntryReference.setCompanyId(companyProvider.getCompanyId());

		return dlOpenerFileEntryReference;
	}

	/**
	 * Removes the dl opener file entry reference with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference that was removed
	 * @throws NoSuchFileEntryReferenceException if a dl opener file entry reference with the primary key could not be found
	 */
	@Override
	public DLOpenerFileEntryReference remove(long dlOpenerFileEntryReferenceId)
		throws NoSuchFileEntryReferenceException {
		return remove((Serializable)dlOpenerFileEntryReferenceId);
	}

	/**
	 * Removes the dl opener file entry reference with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference that was removed
	 * @throws NoSuchFileEntryReferenceException if a dl opener file entry reference with the primary key could not be found
	 */
	@Override
	public DLOpenerFileEntryReference remove(Serializable primaryKey)
		throws NoSuchFileEntryReferenceException {
		Session session = null;

		try {
			session = openSession();

			DLOpenerFileEntryReference dlOpenerFileEntryReference = (DLOpenerFileEntryReference)session.get(DLOpenerFileEntryReferenceImpl.class,
					primaryKey);

			if (dlOpenerFileEntryReference == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileEntryReferenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlOpenerFileEntryReference);
		}
		catch (NoSuchFileEntryReferenceException nsee) {
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
	protected DLOpenerFileEntryReference removeImpl(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlOpenerFileEntryReference)) {
				dlOpenerFileEntryReference = (DLOpenerFileEntryReference)session.get(DLOpenerFileEntryReferenceImpl.class,
						dlOpenerFileEntryReference.getPrimaryKeyObj());
			}

			if (dlOpenerFileEntryReference != null) {
				session.delete(dlOpenerFileEntryReference);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlOpenerFileEntryReference != null) {
			clearCache(dlOpenerFileEntryReference);
		}

		return dlOpenerFileEntryReference;
	}

	@Override
	public DLOpenerFileEntryReference updateImpl(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		boolean isNew = dlOpenerFileEntryReference.isNew();

		if (!(dlOpenerFileEntryReference instanceof DLOpenerFileEntryReferenceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlOpenerFileEntryReference.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(dlOpenerFileEntryReference);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlOpenerFileEntryReference proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLOpenerFileEntryReference implementation " +
				dlOpenerFileEntryReference.getClass());
		}

		DLOpenerFileEntryReferenceModelImpl dlOpenerFileEntryReferenceModelImpl = (DLOpenerFileEntryReferenceModelImpl)dlOpenerFileEntryReference;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (dlOpenerFileEntryReference.getCreateDate() == null)) {
			if (serviceContext == null) {
				dlOpenerFileEntryReference.setCreateDate(now);
			}
			else {
				dlOpenerFileEntryReference.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!dlOpenerFileEntryReferenceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dlOpenerFileEntryReference.setModifiedDate(now);
			}
			else {
				dlOpenerFileEntryReference.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (dlOpenerFileEntryReference.isNew()) {
				session.save(dlOpenerFileEntryReference);

				dlOpenerFileEntryReference.setNew(false);
			}
			else {
				dlOpenerFileEntryReference = (DLOpenerFileEntryReference)session.merge(dlOpenerFileEntryReference);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DLOpenerFileEntryReferenceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
			DLOpenerFileEntryReferenceImpl.class,
			dlOpenerFileEntryReference.getPrimaryKey(),
			dlOpenerFileEntryReference, false);

		clearUniqueFindersCache(dlOpenerFileEntryReferenceModelImpl, false);
		cacheUniqueFindersCache(dlOpenerFileEntryReferenceModelImpl);

		dlOpenerFileEntryReference.resetOriginalValues();

		return dlOpenerFileEntryReference;
	}

	/**
	 * Returns the dl opener file entry reference with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference
	 * @throws NoSuchFileEntryReferenceException if a dl opener file entry reference with the primary key could not be found
	 */
	@Override
	public DLOpenerFileEntryReference findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileEntryReferenceException {
		DLOpenerFileEntryReference dlOpenerFileEntryReference = fetchByPrimaryKey(primaryKey);

		if (dlOpenerFileEntryReference == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileEntryReferenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dlOpenerFileEntryReference;
	}

	/**
	 * Returns the dl opener file entry reference with the primary key or throws a {@link NoSuchFileEntryReferenceException} if it could not be found.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference
	 * @throws NoSuchFileEntryReferenceException if a dl opener file entry reference with the primary key could not be found
	 */
	@Override
	public DLOpenerFileEntryReference findByPrimaryKey(
		long dlOpenerFileEntryReferenceId)
		throws NoSuchFileEntryReferenceException {
		return findByPrimaryKey((Serializable)dlOpenerFileEntryReferenceId);
	}

	/**
	 * Returns the dl opener file entry reference with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference, or <code>null</code> if a dl opener file entry reference with the primary key could not be found
	 */
	@Override
	public DLOpenerFileEntryReference fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
				DLOpenerFileEntryReferenceImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		DLOpenerFileEntryReference dlOpenerFileEntryReference = (DLOpenerFileEntryReference)serializable;

		if (dlOpenerFileEntryReference == null) {
			Session session = null;

			try {
				session = openSession();

				dlOpenerFileEntryReference = (DLOpenerFileEntryReference)session.get(DLOpenerFileEntryReferenceImpl.class,
						primaryKey);

				if (dlOpenerFileEntryReference != null) {
					cacheResult(dlOpenerFileEntryReference);
				}
				else {
					entityCache.putResult(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
						DLOpenerFileEntryReferenceImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
					DLOpenerFileEntryReferenceImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return dlOpenerFileEntryReference;
	}

	/**
	 * Returns the dl opener file entry reference with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference, or <code>null</code> if a dl opener file entry reference with the primary key could not be found
	 */
	@Override
	public DLOpenerFileEntryReference fetchByPrimaryKey(
		long dlOpenerFileEntryReferenceId) {
		return fetchByPrimaryKey((Serializable)dlOpenerFileEntryReferenceId);
	}

	@Override
	public Map<Serializable, DLOpenerFileEntryReference> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLOpenerFileEntryReference> map = new HashMap<Serializable, DLOpenerFileEntryReference>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLOpenerFileEntryReference dlOpenerFileEntryReference = fetchByPrimaryKey(primaryKey);

			if (dlOpenerFileEntryReference != null) {
				map.put(primaryKey, dlOpenerFileEntryReference);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
					DLOpenerFileEntryReferenceImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (DLOpenerFileEntryReference)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DLOPENERFILEENTRYREFERENCE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (DLOpenerFileEntryReference dlOpenerFileEntryReference : (List<DLOpenerFileEntryReference>)q.list()) {
				map.put(dlOpenerFileEntryReference.getPrimaryKeyObj(),
					dlOpenerFileEntryReference);

				cacheResult(dlOpenerFileEntryReference);

				uncachedPrimaryKeys.remove(dlOpenerFileEntryReference.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DLOpenerFileEntryReferenceModelImpl.ENTITY_CACHE_ENABLED,
					DLOpenerFileEntryReferenceImpl.class, primaryKey, nullModel);
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
	 * Returns all the dl opener file entry references.
	 *
	 * @return the dl opener file entry references
	 */
	@Override
	public List<DLOpenerFileEntryReference> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl opener file entry references.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLOpenerFileEntryReferenceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl opener file entry references
	 * @param end the upper bound of the range of dl opener file entry references (not inclusive)
	 * @return the range of dl opener file entry references
	 */
	@Override
	public List<DLOpenerFileEntryReference> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl opener file entry references.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLOpenerFileEntryReferenceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl opener file entry references
	 * @param end the upper bound of the range of dl opener file entry references (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl opener file entry references
	 */
	@Override
	public List<DLOpenerFileEntryReference> findAll(int start, int end,
		OrderByComparator<DLOpenerFileEntryReference> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dl opener file entry references.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLOpenerFileEntryReferenceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl opener file entry references
	 * @param end the upper bound of the range of dl opener file entry references (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of dl opener file entry references
	 */
	@Override
	public List<DLOpenerFileEntryReference> findAll(int start, int end,
		OrderByComparator<DLOpenerFileEntryReference> orderByComparator,
		boolean retrieveFromCache) {
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

		List<DLOpenerFileEntryReference> list = null;

		if (retrieveFromCache) {
			list = (List<DLOpenerFileEntryReference>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DLOPENERFILEENTRYREFERENCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLOPENERFILEENTRYREFERENCE;

				if (pagination) {
					sql = sql.concat(DLOpenerFileEntryReferenceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DLOpenerFileEntryReference>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLOpenerFileEntryReference>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the dl opener file entry references from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLOpenerFileEntryReference dlOpenerFileEntryReference : findAll()) {
			remove(dlOpenerFileEntryReference);
		}
	}

	/**
	 * Returns the number of dl opener file entry references.
	 *
	 * @return the number of dl opener file entry references
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLOPENERFILEENTRYREFERENCE);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DLOpenerFileEntryReferenceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dl opener file entry reference persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DLOpenerFileEntryReferenceImpl.class.getName());
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
	private static final String _SQL_SELECT_DLOPENERFILEENTRYREFERENCE = "SELECT dlOpenerFileEntryReference FROM DLOpenerFileEntryReference dlOpenerFileEntryReference";
	private static final String _SQL_SELECT_DLOPENERFILEENTRYREFERENCE_WHERE_PKS_IN =
		"SELECT dlOpenerFileEntryReference FROM DLOpenerFileEntryReference dlOpenerFileEntryReference WHERE dlOpenerFileEntryReferenceId IN (";
	private static final String _SQL_SELECT_DLOPENERFILEENTRYREFERENCE_WHERE = "SELECT dlOpenerFileEntryReference FROM DLOpenerFileEntryReference dlOpenerFileEntryReference WHERE ";
	private static final String _SQL_COUNT_DLOPENERFILEENTRYREFERENCE = "SELECT COUNT(dlOpenerFileEntryReference) FROM DLOpenerFileEntryReference dlOpenerFileEntryReference";
	private static final String _SQL_COUNT_DLOPENERFILEENTRYREFERENCE_WHERE = "SELECT COUNT(dlOpenerFileEntryReference) FROM DLOpenerFileEntryReference dlOpenerFileEntryReference WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlOpenerFileEntryReference.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLOpenerFileEntryReference exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLOpenerFileEntryReference exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DLOpenerFileEntryReferencePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}