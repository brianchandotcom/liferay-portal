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

package com.liferay.portal.lock.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.lock.exception.NoSuchLockException;
import com.liferay.portal.lock.model.Lock;
import com.liferay.portal.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the lock service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.lock.service.persistence.impl.LockPersistenceImpl
 * @see LockUtil
 * @generated
 */
@ProviderType
public interface LockPersistence extends BasePersistence<Lock> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LockUtil} to access the lock persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the locks where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching locks
	*/
	public java.util.List<Lock> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the locks where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @return the range of matching locks
	*/
	public java.util.List<Lock> findByUuid(java.lang.String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the locks where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching locks
	*/
	public java.util.List<Lock> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns an ordered range of all the locks where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching locks
	*/
	public java.util.List<Lock> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first lock in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lock
	* @throws NoSuchLockException if a matching lock could not be found
	*/
	public Lock findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException;

	/**
	* Returns the first lock in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lock, or <code>null</code> if a matching lock could not be found
	*/
	public Lock fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns the last lock in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lock
	* @throws NoSuchLockException if a matching lock could not be found
	*/
	public Lock findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException;

	/**
	* Returns the last lock in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lock, or <code>null</code> if a matching lock could not be found
	*/
	public Lock fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns the locks before and after the current lock in the ordered set where uuid = &#63;.
	*
	* @param lockId the primary key of the current lock
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lock
	* @throws NoSuchLockException if a lock with the primary key could not be found
	*/
	public Lock[] findByUuid_PrevAndNext(long lockId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException;

	/**
	* Removes all the locks where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of locks where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching locks
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the locks where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching locks
	*/
	public java.util.List<Lock> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the locks where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @return the range of matching locks
	*/
	public java.util.List<Lock> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the locks where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching locks
	*/
	public java.util.List<Lock> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns an ordered range of all the locks where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching locks
	*/
	public java.util.List<Lock> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first lock in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lock
	* @throws NoSuchLockException if a matching lock could not be found
	*/
	public Lock findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException;

	/**
	* Returns the first lock in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lock, or <code>null</code> if a matching lock could not be found
	*/
	public Lock fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns the last lock in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lock
	* @throws NoSuchLockException if a matching lock could not be found
	*/
	public Lock findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException;

	/**
	* Returns the last lock in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lock, or <code>null</code> if a matching lock could not be found
	*/
	public Lock fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns the locks before and after the current lock in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param lockId the primary key of the current lock
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lock
	* @throws NoSuchLockException if a lock with the primary key could not be found
	*/
	public Lock[] findByUuid_C_PrevAndNext(long lockId, java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException;

	/**
	* Removes all the locks where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of locks where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching locks
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the locks where expirationDate &lt; &#63;.
	*
	* @param expirationDate the expiration date
	* @return the matching locks
	*/
	public java.util.List<Lock> findByLtExpirationDate(Date expirationDate);

	/**
	* Returns a range of all the locks where expirationDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @return the range of matching locks
	*/
	public java.util.List<Lock> findByLtExpirationDate(Date expirationDate,
		int start, int end);

	/**
	* Returns an ordered range of all the locks where expirationDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching locks
	*/
	public java.util.List<Lock> findByLtExpirationDate(Date expirationDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns an ordered range of all the locks where expirationDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching locks
	*/
	public java.util.List<Lock> findByLtExpirationDate(Date expirationDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first lock in the ordered set where expirationDate &lt; &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lock
	* @throws NoSuchLockException if a matching lock could not be found
	*/
	public Lock findByLtExpirationDate_First(Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException;

	/**
	* Returns the first lock in the ordered set where expirationDate &lt; &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lock, or <code>null</code> if a matching lock could not be found
	*/
	public Lock fetchByLtExpirationDate_First(Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns the last lock in the ordered set where expirationDate &lt; &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lock
	* @throws NoSuchLockException if a matching lock could not be found
	*/
	public Lock findByLtExpirationDate_Last(Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException;

	/**
	* Returns the last lock in the ordered set where expirationDate &lt; &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lock, or <code>null</code> if a matching lock could not be found
	*/
	public Lock fetchByLtExpirationDate_Last(Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns the locks before and after the current lock in the ordered set where expirationDate &lt; &#63;.
	*
	* @param lockId the primary key of the current lock
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lock
	* @throws NoSuchLockException if a lock with the primary key could not be found
	*/
	public Lock[] findByLtExpirationDate_PrevAndNext(long lockId,
		Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException;

	/**
	* Removes all the locks where expirationDate &lt; &#63; from the database.
	*
	* @param expirationDate the expiration date
	*/
	public void removeByLtExpirationDate(Date expirationDate);

	/**
	* Returns the number of locks where expirationDate &lt; &#63;.
	*
	* @param expirationDate the expiration date
	* @return the number of matching locks
	*/
	public int countByLtExpirationDate(Date expirationDate);

	/**
	* Returns the lock where className = &#63; and key = &#63; or throws a {@link NoSuchLockException} if it could not be found.
	*
	* @param className the class name
	* @param key the key
	* @return the matching lock
	* @throws NoSuchLockException if a matching lock could not be found
	*/
	public Lock findByC_K(java.lang.String className, java.lang.String key)
		throws NoSuchLockException;

	/**
	* Returns the lock where className = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param className the class name
	* @param key the key
	* @return the matching lock, or <code>null</code> if a matching lock could not be found
	*/
	public Lock fetchByC_K(java.lang.String className, java.lang.String key);

	/**
	* Returns the lock where className = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param className the class name
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching lock, or <code>null</code> if a matching lock could not be found
	*/
	public Lock fetchByC_K(java.lang.String className, java.lang.String key,
		boolean retrieveFromCache);

	/**
	* Removes the lock where className = &#63; and key = &#63; from the database.
	*
	* @param className the class name
	* @param key the key
	* @return the lock that was removed
	*/
	public Lock removeByC_K(java.lang.String className, java.lang.String key)
		throws NoSuchLockException;

	/**
	* Returns the number of locks where className = &#63; and key = &#63;.
	*
	* @param className the class name
	* @param key the key
	* @return the number of matching locks
	*/
	public int countByC_K(java.lang.String className, java.lang.String key);

	/**
	* Caches the lock in the entity cache if it is enabled.
	*
	* @param lock the lock
	*/
	public void cacheResult(Lock lock);

	/**
	* Caches the locks in the entity cache if it is enabled.
	*
	* @param locks the locks
	*/
	public void cacheResult(java.util.List<Lock> locks);

	/**
	* Creates a new lock with the primary key. Does not add the lock to the database.
	*
	* @param lockId the primary key for the new lock
	* @return the new lock
	*/
	public Lock create(long lockId);

	/**
	* Removes the lock with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lockId the primary key of the lock
	* @return the lock that was removed
	* @throws NoSuchLockException if a lock with the primary key could not be found
	*/
	public Lock remove(long lockId) throws NoSuchLockException;

	public Lock updateImpl(Lock lock);

	/**
	* Returns the lock with the primary key or throws a {@link NoSuchLockException} if it could not be found.
	*
	* @param lockId the primary key of the lock
	* @return the lock
	* @throws NoSuchLockException if a lock with the primary key could not be found
	*/
	public Lock findByPrimaryKey(long lockId) throws NoSuchLockException;

	/**
	* Returns the lock with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param lockId the primary key of the lock
	* @return the lock, or <code>null</code> if a lock with the primary key could not be found
	*/
	public Lock fetchByPrimaryKey(long lockId);

	@Override
	public java.util.Map<java.io.Serializable, Lock> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the locks.
	*
	* @return the locks
	*/
	public java.util.List<Lock> findAll();

	/**
	* Returns a range of all the locks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @return the range of locks
	*/
	public java.util.List<Lock> findAll(int start, int end);

	/**
	* Returns an ordered range of all the locks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of locks
	*/
	public java.util.List<Lock> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator);

	/**
	* Returns an ordered range of all the locks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of locks
	*/
	public java.util.List<Lock> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Lock> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the locks from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of locks.
	*
	* @return the number of locks
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}