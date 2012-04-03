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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the lock service. This utility wraps {@link LockPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LockPersistence
 * @see LockPersistenceImpl
 * @generated
 */
public class LockUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Lock lock) {
		getPersistence().clearCache(lock);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Lock> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Lock> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Lock> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Lock update(Lock lock, boolean merge)
		throws SystemException {
		return getPersistence().update(lock, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Lock update(Lock lock, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(lock, merge, serviceContext);
	}

	/**
	* Caches the lock in the entity cache if it is enabled.
	*
	* @param lock the lock
	*/
	public static void cacheResult(com.liferay.portal.model.Lock lock) {
		getPersistence().cacheResult(lock);
	}

	/**
	* Caches the locks in the entity cache if it is enabled.
	*
	* @param locks the locks
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Lock> locks) {
		getPersistence().cacheResult(locks);
	}

	/**
	* Creates a new lock with the primary key. Does not add the lock to the database.
	*
	* @param lockId the primary key for the new lock
	* @return the new lock
	*/
	public static com.liferay.portal.model.Lock create(long lockId) {
		return getPersistence().create(lockId);
	}

	/**
	* Removes the lock with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lockId the primary key of the lock
	* @return the lock that was removed
	* @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock remove(long lockId)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(lockId);
	}

	public static com.liferay.portal.model.Lock updateImpl(
		com.liferay.portal.model.Lock lock, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(lock, merge);
	}

	/**
	* Returns the lock with the primary key or throws a {@link com.liferay.portal.NoSuchLockException} if it could not be found.
	*
	* @param lockId the primary key of the lock
	* @return the lock
	* @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock findByPrimaryKey(long lockId)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(lockId);
	}

	/**
	* Returns the lock with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param lockId the primary key of the lock
	* @return the lock, or <code>null</code> if a lock with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock fetchByPrimaryKey(long lockId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(lockId);
	}

	/**
	* Returns all the locks where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching locks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Lock> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
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
	public static java.util.List<com.liferay.portal.model.Lock> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
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
	public static java.util.List<com.liferay.portal.model.Lock> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first lock in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lock
	* @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last lock in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lock
	* @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the locks before and after the current lock in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param lockId the primary key of the current lock
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lock
	* @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock[] findByUuid_PrevAndNext(
		long lockId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(lockId, uuid, orderByComparator);
	}

	/**
	* Returns all the locks where expirationDate &lt; &#63;.
	*
	* @param expirationDate the expiration date
	* @return the matching locks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Lock> findByLtExpirationDate(
		java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLtExpirationDate(expirationDate);
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
	public static java.util.List<com.liferay.portal.model.Lock> findByLtExpirationDate(
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLtExpirationDate(expirationDate, start, end);
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
	public static java.util.List<com.liferay.portal.model.Lock> findByLtExpirationDate(
		java.util.Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLtExpirationDate(expirationDate, start, end,
			orderByComparator);
	}

	/**
	* Returns the first lock in the ordered set where expirationDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lock
	* @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock findByLtExpirationDate_First(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLtExpirationDate_First(expirationDate,
			orderByComparator);
	}

	/**
	* Returns the last lock in the ordered set where expirationDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lock
	* @throws com.liferay.portal.NoSuchLockException if a matching lock could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock findByLtExpirationDate_Last(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLtExpirationDate_Last(expirationDate,
			orderByComparator);
	}

	/**
	* Returns the locks before and after the current lock in the ordered set where expirationDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param lockId the primary key of the current lock
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lock
	* @throws com.liferay.portal.NoSuchLockException if a lock with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock[] findByLtExpirationDate_PrevAndNext(
		long lockId, java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLtExpirationDate_PrevAndNext(lockId, expirationDate,
			orderByComparator);
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
	public static com.liferay.portal.model.Lock findByC_K(
		java.lang.String className, java.lang.String key)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_K(className, key);
	}

	/**
	* Returns the lock where className = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param className the class name
	* @param key the key
	* @return the matching lock, or <code>null</code> if a matching lock could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock fetchByC_K(
		java.lang.String className, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_K(className, key);
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
	public static com.liferay.portal.model.Lock fetchByC_K(
		java.lang.String className, java.lang.String key,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_K(className, key, retrieveFromCache);
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
	public static com.liferay.portal.model.Lock findByC_K_O(
		java.lang.String className, java.lang.String key, java.lang.String owner)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_K_O(className, key, owner);
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
	public static com.liferay.portal.model.Lock fetchByC_K_O(
		java.lang.String className, java.lang.String key, java.lang.String owner)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_K_O(className, key, owner);
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
	public static com.liferay.portal.model.Lock fetchByC_K_O(
		java.lang.String className, java.lang.String key,
		java.lang.String owner, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_K_O(className, key, owner, retrieveFromCache);
	}

	/**
	* Returns all the locks.
	*
	* @return the locks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Lock> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<com.liferay.portal.model.Lock> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
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
	public static java.util.List<com.liferay.portal.model.Lock> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the locks where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the locks where expirationDate &lt; &#63; from the database.
	*
	* @param expirationDate the expiration date
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByLtExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByLtExpirationDate(expirationDate);
	}

	/**
	* Removes the lock where className = &#63; and key = &#63; from the database.
	*
	* @param className the class name
	* @param key the key
	* @return the lock that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Lock removeByC_K(
		java.lang.String className, java.lang.String key)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByC_K(className, key);
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
	public static com.liferay.portal.model.Lock removeByC_K_O(
		java.lang.String className, java.lang.String key, java.lang.String owner)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByC_K_O(className, key, owner);
	}

	/**
	* Removes all the locks from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of locks where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching locks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of locks where expirationDate &lt; &#63;.
	*
	* @param expirationDate the expiration date
	* @return the number of matching locks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByLtExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByLtExpirationDate(expirationDate);
	}

	/**
	* Returns the number of locks where className = &#63; and key = &#63;.
	*
	* @param className the class name
	* @param key the key
	* @return the number of matching locks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_K(java.lang.String className,
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_K(className, key);
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
	public static int countByC_K_O(java.lang.String className,
		java.lang.String key, java.lang.String owner)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_K_O(className, key, owner);
	}

	/**
	* Returns the number of locks.
	*
	* @return the number of locks
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LockPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LockPersistence)PortalBeanLocatorUtil.locate(LockPersistence.class.getName());

			ReferenceRegistry.registerReference(LockUtil.class, "_persistence");
		}

		return _persistence;
	}

	public void setPersistence(LockPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(LockUtil.class, "_persistence");
	}

	private static LockPersistence _persistence;
}