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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Lock. This utility wraps
 * {@link com.liferay.portal.service.impl.LockLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LockLocalService
 * @see com.liferay.portal.service.base.LockLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LockLocalServiceImpl
 * @generated
 */
@ProviderType
public class LockLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LockLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the lock to the database. Also notifies the appropriate model listeners.
	*
	* @param lock the lock
	* @return the lock that was added
	*/
	public static com.liferay.portal.model.Lock addLock(
		com.liferay.portal.model.Lock lock) {
		return getService().addLock(lock);
	}

	/**
	* Creates a new lock with the primary key. Does not add the lock to the database.
	*
	* @param lockId the primary key for the new lock
	* @return the new lock
	*/
	public static com.liferay.portal.model.Lock createLock(long lockId) {
		return getService().createLock(lockId);
	}

	/**
	* Deletes the lock with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lockId the primary key of the lock
	* @return the lock that was removed
	* @throws PortalException if a lock with the primary key could not be found
	*/
	public static com.liferay.portal.model.Lock deleteLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteLock(lockId);
	}

	/**
	* Deletes the lock from the database. Also notifies the appropriate model listeners.
	*
	* @param lock the lock
	* @return the lock that was removed
	*/
	public static com.liferay.portal.model.Lock deleteLock(
		com.liferay.portal.model.Lock lock) {
		return getService().deleteLock(lock);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.model.Lock fetchLock(long lockId) {
		return getService().fetchLock(lockId);
	}

	/**
	* Returns the lock with the matching UUID and company.
	*
	* @param uuid the lock's UUID
	* @param companyId the primary key of the company
	* @return the matching lock, or <code>null</code> if a matching lock could not be found
	*/
	public static com.liferay.portal.model.Lock fetchLockByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().fetchLockByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the lock with the primary key.
	*
	* @param lockId the primary key of the lock
	* @return the lock
	* @throws PortalException if a lock with the primary key could not be found
	*/
	public static com.liferay.portal.model.Lock getLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLock(lockId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the lock with the matching UUID and company.
	*
	* @param uuid the lock's UUID
	* @param companyId the primary key of the company
	* @return the matching lock
	* @throws PortalException if a matching lock could not be found
	*/
	public static com.liferay.portal.model.Lock getLockByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLockByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of all the locks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of locks
	* @param end the upper bound of the range of locks (not inclusive)
	* @return the range of locks
	*/
	public static java.util.List<com.liferay.portal.model.Lock> getLocks(
		int start, int end) {
		return getService().getLocks(start, end);
	}

	/**
	* Returns the number of locks.
	*
	* @return the number of locks
	*/
	public static int getLocksCount() {
		return getService().getLocksCount();
	}

	/**
	* Updates the lock in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param lock the lock
	* @return the lock that was updated
	*/
	public static com.liferay.portal.model.Lock updateLock(
		com.liferay.portal.model.Lock lock) {
		return getService().updateLock(lock);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void clear() {
		getService().clear();
	}

	public static com.liferay.portal.model.Lock getLock(
		java.lang.String className, long key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLock(className, key);
	}

	public static com.liferay.portal.model.Lock getLock(
		java.lang.String className, java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLock(className, key);
	}

	public static boolean hasLock(long userId, java.lang.String className,
		long key) {
		return getService().hasLock(userId, className, key);
	}

	public static boolean hasLock(long userId, java.lang.String className,
		java.lang.String key) {
		return getService().hasLock(userId, className, key);
	}

	public static boolean isLocked(java.lang.String className, long key) {
		return getService().isLocked(className, key);
	}

	public static boolean isLocked(java.lang.String className,
		java.lang.String key) {
		return getService().isLocked(className, key);
	}

	public static com.liferay.portal.model.Lock lock(long userId,
		java.lang.String className, long key, java.lang.String owner,
		boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .lock(userId, className, key, owner, inheritable,
			expirationTime);
	}

	public static com.liferay.portal.model.Lock lock(long userId,
		java.lang.String className, java.lang.String key,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .lock(userId, className, key, owner, inheritable,
			expirationTime);
	}

	public static com.liferay.portal.model.Lock lock(
		java.lang.String className, java.lang.String key, java.lang.String owner) {
		return getService().lock(className, key, owner);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #lock(String, String,
	String)}
	*/
	@Deprecated
	public static com.liferay.portal.model.Lock lock(
		java.lang.String className, java.lang.String key,
		java.lang.String owner, boolean retrieveFromCache) {
		return getService().lock(className, key, owner, retrieveFromCache);
	}

	public static com.liferay.portal.model.Lock lock(
		java.lang.String className, java.lang.String key,
		java.lang.String expectedOwner, java.lang.String updatedOwner) {
		return getService().lock(className, key, expectedOwner, updatedOwner);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #lock(String, String, String,
	String)}
	*/
	@Deprecated
	public static com.liferay.portal.model.Lock lock(
		java.lang.String className, java.lang.String key,
		java.lang.String expectedOwner, java.lang.String updatedOwner,
		boolean retrieveFromCache) {
		return getService()
				   .lock(className, key, expectedOwner, updatedOwner,
			retrieveFromCache);
	}

	public static com.liferay.portal.model.Lock refresh(java.lang.String uuid,
		long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().refresh(uuid, companyId, expirationTime);
	}

	public static void unlock(java.lang.String className, long key) {
		getService().unlock(className, key);
	}

	public static void unlock(java.lang.String className, java.lang.String key) {
		getService().unlock(className, key);
	}

	public static void unlock(java.lang.String className, java.lang.String key,
		java.lang.String owner) {
		getService().unlock(className, key, owner);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #unlock(String, String,
	String)}
	*/
	@Deprecated
	public static void unlock(java.lang.String className, java.lang.String key,
		java.lang.String owner, boolean retrieveFromCache) {
		getService().unlock(className, key, owner, retrieveFromCache);
	}

	public static LockLocalService getService() {
		if (_service == null) {
			_service = (LockLocalService)PortalBeanLocatorUtil.locate(LockLocalService.class.getName());

			ReferenceRegistry.registerReference(LockLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(LockLocalService service) {
	}

	private static LockLocalService _service;
}