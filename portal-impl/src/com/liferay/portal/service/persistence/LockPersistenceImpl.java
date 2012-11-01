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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLockException;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LockImpl;
import com.liferay.portal.model.impl.LockModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The persistence implementation for the lock service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LockPersistence
 * @see LockUtil
 * @generated
 */
public class LockPersistenceImpl extends BasePersistenceImpl<Lock>
	implements LockPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LockUtil} to access the lock persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LockImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LockModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			LockModelImpl.UUID_COLUMN_BITMASK |
			LockModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LTEXPIRATIONDATE =
		new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtExpirationDate",
			new String[] {
				Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTEXPIRATIONDATE =
		new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtExpirationDate",
			new String[] { Date.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_K = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_K",
			new String[] { String.class.getName(), String.class.getName() },
			LockModelImpl.CLASSNAME_COLUMN_BITMASK |
			LockModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_K_O = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_K_O",
			new String[] {
				String.class.getName(), String.class.getName(),
				String.class.getName()
			},
			LockModelImpl.CLASSNAME_COLUMN_BITMASK |
			LockModelImpl.KEY_COLUMN_BITMASK |
			LockModelImpl.OWNER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K_O = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K_O",
			new String[] {
				String.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the lock in the entity cache if it is enabled.
	 *
	 * @param lock the lock
	 */
	public void cacheResult(Lock lock) {
		EntityCacheUtil.putResult(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockImpl.class, lock.getPrimaryKey(), lock);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
			new Object[] { lock.getClassName(), lock.getKey() }, lock);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K_O,
			new Object[] { lock.getClassName(), lock.getKey(), lock.getOwner() },
			lock);

		lock.resetOriginalValues();
	}

	/**
	 * Caches the locks in the entity cache if it is enabled.
	 *
	 * @param locks the locks
	 */
	public void cacheResult(List<Lock> locks) {
		for (Lock lock : locks) {
			if (EntityCacheUtil.getResult(LockModelImpl.ENTITY_CACHE_ENABLED,
						LockImpl.class, lock.getPrimaryKey()) == null) {
				cacheResult(lock);
			}
			else {
				lock.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all locks.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LockImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LockImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lock.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Lock lock) {
		EntityCacheUtil.removeResult(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockImpl.class, lock.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(lock);
	}

	@Override
	public void clearCache(List<Lock> locks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Lock lock : locks) {
			EntityCacheUtil.removeResult(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockImpl.class, lock.getPrimaryKey());

			clearUniqueFindersCache(lock);
		}
	}

	protected void clearUniqueFindersCache(Lock lock) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_K,
			new Object[] { lock.getClassName(), lock.getKey() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_K_O,
			new Object[] { lock.getClassName(), lock.getKey(), lock.getOwner() });
	}

	/**
	 * Creates a new lock with the primary key. Does not add the lock to the database.
	 *
	 * @param lockId the primary key for the new lock
	 * @return the new lock
	 */
	public Lock create(long lockId) {
		Lock lock = new LockImpl();

		lock.setNew(true);
		lock.setPrimaryKey(lockId);

		String uuid = PortalUUIDUtil.generate();

		lock.setUuid(uuid);

		return lock;
	}

	/**
	 * Removes the lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lockId the primary key of the lock
	 * @return the lock that was removed
	 * @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock remove(long lockId) throws NoSuchLockException, SystemException {
		return remove(Long.valueOf(lockId));
	}

	/**
	 * Removes the lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lock
	 * @return the lock that was removed
	 * @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Lock remove(Serializable primaryKey)
		throws NoSuchLockException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Lock lock = (Lock)session.get(LockImpl.class, primaryKey);

			if (lock == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLockException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(lock);
		}
		catch (NoSuchLockException nsee) {
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
	protected Lock removeImpl(Lock lock) throws SystemException {
		lock = toUnwrappedModel(lock);

		Session session = null;

		try {
			session = openSession();

			if (lock.isCachedModel()) {
				lock = (Lock)session.get(LockImpl.class, lock.getPrimaryKeyObj());
			}

			if (lock != null) {
				session.delete(lock);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (lock != null) {
			clearCache(lock);
		}

		return lock;
	}

	@Override
	public Lock updateImpl(com.liferay.portal.model.Lock lock)
		throws SystemException {
		lock = toUnwrappedModel(lock);

		boolean isNew = lock.isNew();

		LockModelImpl lockModelImpl = (LockModelImpl)lock;

		if (Validator.isNull(lock.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			lock.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (lock.isNew()) {
				session.save(lock);

				lock.setNew(false);
			}
			else {
				session.merge(lock);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LockModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((lockModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { lockModelImpl.getOriginalUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { lockModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((lockModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lockModelImpl.getOriginalUuid(),
						Long.valueOf(lockModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						lockModelImpl.getUuid(),
						Long.valueOf(lockModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}
		}

		EntityCacheUtil.putResult(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockImpl.class, lock.getPrimaryKey(), lock);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
				new Object[] { lock.getClassName(), lock.getKey() }, lock);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K_O,
				new Object[] { lock.getClassName(), lock.getKey(), lock.getOwner() },
				lock);
		}
		else {
			if ((lockModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_K.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lockModelImpl.getOriginalClassName(),
						
						lockModelImpl.getOriginalKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_K, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_K, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
					new Object[] { lock.getClassName(), lock.getKey() }, lock);
			}

			if ((lockModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_K_O.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lockModelImpl.getOriginalClassName(),
						
						lockModelImpl.getOriginalKey(),
						
						lockModelImpl.getOriginalOwner()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_K_O, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_K_O, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K_O,
					new Object[] {
						lock.getClassName(),
						
					lock.getKey(),
						
					lock.getOwner()
					}, lock);
			}
		}

		return lock;
	}

	protected Lock toUnwrappedModel(Lock lock) {
		if (lock instanceof LockImpl) {
			return lock;
		}

		LockImpl lockImpl = new LockImpl();

		lockImpl.setNew(lock.isNew());
		lockImpl.setPrimaryKey(lock.getPrimaryKey());

		lockImpl.setUuid(lock.getUuid());
		lockImpl.setLockId(lock.getLockId());
		lockImpl.setCompanyId(lock.getCompanyId());
		lockImpl.setUserId(lock.getUserId());
		lockImpl.setUserName(lock.getUserName());
		lockImpl.setCreateDate(lock.getCreateDate());
		lockImpl.setClassName(lock.getClassName());
		lockImpl.setKey(lock.getKey());
		lockImpl.setOwner(lock.getOwner());
		lockImpl.setInheritable(lock.isInheritable());
		lockImpl.setExpirationDate(lock.getExpirationDate());

		return lockImpl;
	}

	/**
	 * Returns the lock with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the lock
	 * @return the lock
	 * @throws com.liferay.portal.NoSuchModelException if a lock with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Lock findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the lock with the primary key or throws a {@link com.liferay.portal.NoSuchLockException} if it could not be found.
	 *
	 * @param lockId the primary key of the lock
	 * @return the lock
	 * @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock findByPrimaryKey(long lockId)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByPrimaryKey(lockId);

		if (lock == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + lockId);
			}

			throw new NoSuchLockException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				lockId);
		}

		return lock;
	}

	/**
	 * Returns the lock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lock
	 * @return the lock, or <code>null</code> if a lock with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Lock fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the lock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lockId the primary key of the lock
	 * @return the lock, or <code>null</code> if a lock with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByPrimaryKey(long lockId) throws SystemException {
		Lock lock = (Lock)EntityCacheUtil.getResult(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockImpl.class, lockId);

		if (lock == _nullLock) {
			return null;
		}

		if (lock == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				lock = (Lock)session.get(LockImpl.class, Long.valueOf(lockId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (lock != null) {
					cacheResult(lock);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LockModelImpl.ENTITY_CACHE_ENABLED,
						LockImpl.class, lockId, _nullLock);
				}

				closeSession(session);
			}
		}

		return lock;
	}

	/**
	 * Returns all the locks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the locks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @return the range of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the locks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Lock lock : list) {
				if (!Validator.equals(uuid, lock.getUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_LOCK_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<Lock>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first lock in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock
	 * @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByUuid_First(uuid, orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the first lock in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<Lock> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lock in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock
	 * @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock findByUuid_Last(String uuid, OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByUuid_Last(uuid, orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the last lock in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<Lock> list = findByUuid(uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the locks before and after the current lock in the ordered set where uuid = &#63;.
	 *
	 * @param lockId the primary key of the current lock
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lock
	 * @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock[] findByUuid_PrevAndNext(long lockId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = findByPrimaryKey(lockId);

		Session session = null;

		try {
			session = openSession();

			Lock[] array = new LockImpl[3];

			array[0] = getByUuid_PrevAndNext(session, lock, uuid,
					orderByComparator, true);

			array[1] = lock;

			array[2] = getByUuid_PrevAndNext(session, lock, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Lock getByUuid_PrevAndNext(Session session, Lock lock,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LOCK_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lock);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Lock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the locks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the locks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @return the range of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findByUuid_C(String uuid, long companyId, int start,
		int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the locks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Lock lock : list) {
				if (!Validator.equals(uuid, lock.getUuid()) ||
						(companyId != lock.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LOCK_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<Lock>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock
	 * @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the first lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Lock> list = findByUuid_C(uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock
	 * @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the last lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<Lock> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the locks before and after the current lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param lockId the primary key of the current lock
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lock
	 * @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock[] findByUuid_C_PrevAndNext(long lockId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = findByPrimaryKey(lockId);

		Session session = null;

		try {
			session = openSession();

			Lock[] array = new LockImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, lock, uuid, companyId,
					orderByComparator, true);

			array[1] = lock;

			array[2] = getByUuid_C_PrevAndNext(session, lock, uuid, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Lock getByUuid_C_PrevAndNext(Session session, Lock lock,
		String uuid, long companyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LOCK_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lock);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Lock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the locks where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findByLtExpirationDate(Date expirationDate)
		throws SystemException {
		return findByLtExpirationDate(expirationDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the locks where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @return the range of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findByLtExpirationDate(Date expirationDate, int start,
		int end) throws SystemException {
		return findByLtExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the locks where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findByLtExpirationDate(Date expirationDate, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LTEXPIRATIONDATE;
		finderArgs = new Object[] { expirationDate, start, end, orderByComparator };

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Lock lock : list) {
				if (!Validator.equals(expirationDate, lock.getExpirationDate())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_LOCK_WHERE);

			if (expirationDate == null) {
				query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (expirationDate != null) {
					qPos.add(CalendarUtil.getTimestamp(expirationDate));
				}

				list = (List<Lock>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock
	 * @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock findByLtExpirationDate_First(Date expirationDate,
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByLtExpirationDate_First(expirationDate,
				orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("expirationDate=");
		msg.append(expirationDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the first lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByLtExpirationDate_First(Date expirationDate,
		OrderByComparator orderByComparator) throws SystemException {
		List<Lock> list = findByLtExpirationDate(expirationDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock
	 * @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock findByLtExpirationDate_Last(Date expirationDate,
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByLtExpirationDate_Last(expirationDate,
				orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("expirationDate=");
		msg.append(expirationDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the last lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByLtExpirationDate_Last(Date expirationDate,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByLtExpirationDate(expirationDate);

		List<Lock> list = findByLtExpirationDate(expirationDate, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the locks before and after the current lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param lockId the primary key of the current lock
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lock
	 * @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock[] findByLtExpirationDate_PrevAndNext(long lockId,
		Date expirationDate, OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = findByPrimaryKey(lockId);

		Session session = null;

		try {
			session = openSession();

			Lock[] array = new LockImpl[3];

			array[0] = getByLtExpirationDate_PrevAndNext(session, lock,
					expirationDate, orderByComparator, true);

			array[1] = lock;

			array[2] = getByLtExpirationDate_PrevAndNext(session, lock,
					expirationDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Lock getByLtExpirationDate_PrevAndNext(Session session,
		Lock lock, Date expirationDate, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LOCK_WHERE);

		if (expirationDate == null) {
			query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
		}

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (expirationDate != null) {
			qPos.add(CalendarUtil.getTimestamp(expirationDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lock);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Lock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the lock where className = &#63; and key = &#63; or throws a {@link com.liferay.portal.NoSuchLockException} if it could not be found.
	 *
	 * @param className the class name
	 * @param key the key
	 * @return the matching lock
	 * @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock findByC_K(String className, String key)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByC_K(className, key);

		if (lock == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("className=");
			msg.append(className);

			msg.append(", key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLockException(msg.toString());
		}

		return lock;
	}

	/**
	 * Returns the lock where className = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param className the class name
	 * @param key the key
	 * @return the matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByC_K(String className, String key)
		throws SystemException {
		return fetchByC_K(className, key, true);
	}

	/**
	 * Returns the lock where className = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param className the class name
	 * @param key the key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByC_K(String className, String key,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { className, key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_K,
					finderArgs, this);
		}

		if (result instanceof Lock) {
			Lock lock = (Lock)result;

			if (!Validator.equals(className, lock.getClassName()) ||
					!Validator.equals(key, lock.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_LOCK_WHERE);

			if (className == null) {
				query.append(_FINDER_COLUMN_C_K_CLASSNAME_1);
			}
			else {
				if (className.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_CLASSNAME_2);
				}
			}

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_KEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (className != null) {
					qPos.add(className);
				}

				if (key != null) {
					qPos.add(key);
				}

				List<Lock> list = q.list();

				result = list;

				Lock lock = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
						finderArgs, list);
				}
				else {
					lock = list.get(0);

					cacheResult(lock);

					if ((lock.getClassName() == null) ||
							!lock.getClassName().equals(className) ||
							(lock.getKey() == null) ||
							!lock.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
							finderArgs, lock);
					}
				}

				return lock;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_K,
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
				return (Lock)result;
			}
		}
	}

	/**
	 * Returns the lock where className = &#63; and key = &#63; and owner = &#63; or throws a {@link com.liferay.portal.NoSuchLockException} if it could not be found.
	 *
	 * @param className the class name
	 * @param key the key
	 * @param owner the owner
	 * @return the matching lock
	 * @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock findByC_K_O(String className, String key, String owner)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByC_K_O(className, key, owner);

		if (lock == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("className=");
			msg.append(className);

			msg.append(", key=");
			msg.append(key);

			msg.append(", owner=");
			msg.append(owner);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLockException(msg.toString());
		}

		return lock;
	}

	/**
	 * Returns the lock where className = &#63; and key = &#63; and owner = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param className the class name
	 * @param key the key
	 * @param owner the owner
	 * @return the matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByC_K_O(String className, String key, String owner)
		throws SystemException {
		return fetchByC_K_O(className, key, owner, true);
	}

	/**
	 * Returns the lock where className = &#63; and key = &#63; and owner = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param className the class name
	 * @param key the key
	 * @param owner the owner
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching lock, or <code>null</code> if a matching lock could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Lock fetchByC_K_O(String className, String key, String owner,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { className, key, owner };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_K_O,
					finderArgs, this);
		}

		if (result instanceof Lock) {
			Lock lock = (Lock)result;

			if (!Validator.equals(className, lock.getClassName()) ||
					!Validator.equals(key, lock.getKey()) ||
					!Validator.equals(owner, lock.getOwner())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LOCK_WHERE);

			if (className == null) {
				query.append(_FINDER_COLUMN_C_K_O_CLASSNAME_1);
			}
			else {
				if (className.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_O_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_O_CLASSNAME_2);
				}
			}

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_O_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_O_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_O_KEY_2);
				}
			}

			if (owner == null) {
				query.append(_FINDER_COLUMN_C_K_O_OWNER_1);
			}
			else {
				if (owner.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_O_OWNER_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_O_OWNER_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (className != null) {
					qPos.add(className);
				}

				if (key != null) {
					qPos.add(key);
				}

				if (owner != null) {
					qPos.add(owner);
				}

				List<Lock> list = q.list();

				result = list;

				Lock lock = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K_O,
						finderArgs, list);
				}
				else {
					lock = list.get(0);

					cacheResult(lock);

					if ((lock.getClassName() == null) ||
							!lock.getClassName().equals(className) ||
							(lock.getKey() == null) ||
							!lock.getKey().equals(key) ||
							(lock.getOwner() == null) ||
							!lock.getOwner().equals(owner)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K_O,
							finderArgs, lock);
					}
				}

				return lock;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_K_O,
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
				return (Lock)result;
			}
		}
	}

	/**
	 * Returns all the locks.
	 *
	 * @return the locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @return the range of locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of locks
	 * @throws SystemException if a system exception occurred
	 */
	public List<Lock> findAll(int start, int end,
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

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LOCK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LOCK;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
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
	 * Removes all the locks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (Lock lock : findByUuid(uuid)) {
			remove(lock);
		}
	}

	/**
	 * Removes all the locks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (Lock lock : findByUuid_C(uuid, companyId)) {
			remove(lock);
		}
	}

	/**
	 * Removes all the locks where expirationDate &lt; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByLtExpirationDate(Date expirationDate)
		throws SystemException {
		for (Lock lock : findByLtExpirationDate(expirationDate)) {
			remove(lock);
		}
	}

	/**
	 * Removes the lock where className = &#63; and key = &#63; from the database.
	 *
	 * @param className the class name
	 * @param key the key
	 * @return the lock that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Lock removeByC_K(String className, String key)
		throws NoSuchLockException, SystemException {
		Lock lock = findByC_K(className, key);

		return remove(lock);
	}

	/**
	 * Removes the lock where className = &#63; and key = &#63; and owner = &#63; from the database.
	 *
	 * @param className the class name
	 * @param key the key
	 * @param owner the owner
	 * @return the lock that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Lock removeByC_K_O(String className, String key, String owner)
		throws NoSuchLockException, SystemException {
		Lock lock = findByC_K_O(className, key, owner);

		return remove(lock);
	}

	/**
	 * Removes all the locks from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Lock lock : findAll()) {
			remove(lock);
		}
	}

	/**
	 * Returns the number of locks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LOCK_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of locks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LOCK_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of locks where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByLtExpirationDate(Date expirationDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { expirationDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTEXPIRATIONDATE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LOCK_WHERE);

			if (expirationDate == null) {
				query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (expirationDate != null) {
					qPos.add(CalendarUtil.getTimestamp(expirationDate));
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTEXPIRATIONDATE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of locks where className = &#63; and key = &#63;.
	 *
	 * @param className the class name
	 * @param key the key
	 * @return the number of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_K(String className, String key)
		throws SystemException {
		Object[] finderArgs = new Object[] { className, key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_K,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LOCK_WHERE);

			if (className == null) {
				query.append(_FINDER_COLUMN_C_K_CLASSNAME_1);
			}
			else {
				if (className.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_CLASSNAME_2);
				}
			}

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_KEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (className != null) {
					qPos.add(className);
				}

				if (key != null) {
					qPos.add(key);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_K, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of locks where className = &#63; and key = &#63; and owner = &#63;.
	 *
	 * @param className the class name
	 * @param key the key
	 * @param owner the owner
	 * @return the number of matching locks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_K_O(String className, String key, String owner)
		throws SystemException {
		Object[] finderArgs = new Object[] { className, key, owner };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_K_O,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LOCK_WHERE);

			if (className == null) {
				query.append(_FINDER_COLUMN_C_K_O_CLASSNAME_1);
			}
			else {
				if (className.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_O_CLASSNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_O_CLASSNAME_2);
				}
			}

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_O_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_O_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_O_KEY_2);
				}
			}

			if (owner == null) {
				query.append(_FINDER_COLUMN_C_K_O_OWNER_1);
			}
			else {
				if (owner.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_O_OWNER_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_O_OWNER_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (className != null) {
					qPos.add(className);
				}

				if (key != null) {
					qPos.add(key);
				}

				if (owner != null) {
					qPos.add(owner);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_K_O,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of locks.
	 *
	 * @return the number of locks
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LOCK);

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
	 * Initializes the lock persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Lock")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Lock>> listenersList = new ArrayList<ModelListener<Lock>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Lock>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LockImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_LOCK = "SELECT lock FROM Lock lock";
	private static final String _SQL_SELECT_LOCK_WHERE = "SELECT lock FROM Lock lock WHERE ";
	private static final String _SQL_COUNT_LOCK = "SELECT COUNT(lock) FROM Lock lock";
	private static final String _SQL_COUNT_LOCK_WHERE = "SELECT COUNT(lock) FROM Lock lock WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "lock.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "lock.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(lock.uuid IS NULL OR lock.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "lock.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "lock.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(lock.uuid IS NULL OR lock.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "lock.companyId = ?";
	private static final String _FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1 =
		"lock.expirationDate < NULL";
	private static final String _FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2 =
		"lock.expirationDate < ?";
	private static final String _FINDER_COLUMN_C_K_CLASSNAME_1 = "lock.className IS NULL AND ";
	private static final String _FINDER_COLUMN_C_K_CLASSNAME_2 = "lock.className = ? AND ";
	private static final String _FINDER_COLUMN_C_K_CLASSNAME_3 = "(lock.className IS NULL OR lock.className = ?) AND ";
	private static final String _FINDER_COLUMN_C_K_KEY_1 = "lock.key IS NULL";
	private static final String _FINDER_COLUMN_C_K_KEY_2 = "lock.key = ?";
	private static final String _FINDER_COLUMN_C_K_KEY_3 = "(lock.key IS NULL OR lock.key = ?)";
	private static final String _FINDER_COLUMN_C_K_O_CLASSNAME_1 = "lock.className IS NULL AND ";
	private static final String _FINDER_COLUMN_C_K_O_CLASSNAME_2 = "lock.className = ? AND ";
	private static final String _FINDER_COLUMN_C_K_O_CLASSNAME_3 = "(lock.className IS NULL OR lock.className = ?) AND ";
	private static final String _FINDER_COLUMN_C_K_O_KEY_1 = "lock.key IS NULL AND ";
	private static final String _FINDER_COLUMN_C_K_O_KEY_2 = "lock.key = ? AND ";
	private static final String _FINDER_COLUMN_C_K_O_KEY_3 = "(lock.key IS NULL OR lock.key = ?) AND ";
	private static final String _FINDER_COLUMN_C_K_O_OWNER_1 = "lock.owner IS NULL";
	private static final String _FINDER_COLUMN_C_K_O_OWNER_2 = "lock.owner = ?";
	private static final String _FINDER_COLUMN_C_K_O_OWNER_3 = "(lock.owner IS NULL OR lock.owner = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "lock.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Lock exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Lock exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(LockPersistenceImpl.class);
	private static Lock _nullLock = new LockImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Lock> toCacheModel() {
				return _nullLockCacheModel;
			}
		};

	private static CacheModel<Lock> _nullLockCacheModel = new CacheModel<Lock>() {
			public Lock toEntityModel() {
				return _nullLock;
			}
		};
}