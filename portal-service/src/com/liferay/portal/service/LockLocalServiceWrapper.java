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

/**
 * Provides a wrapper for {@link LockLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LockLocalService
 * @generated
 */
@ProviderType
public class LockLocalServiceWrapper implements LockLocalService,
	ServiceWrapper<LockLocalService> {
	public LockLocalServiceWrapper(LockLocalService lockLocalService) {
		_lockLocalService = lockLocalService;
	}

	/**
	* Adds the lock to the database. Also notifies the appropriate model listeners.
	*
	* @param lock the lock
	* @return the lock that was added
	*/
	@Override
	public com.liferay.portal.model.Lock addLock(
		com.liferay.portal.model.Lock lock) {
		return _lockLocalService.addLock(lock);
	}

	/**
	* Creates a new lock with the primary key. Does not add the lock to the database.
	*
	* @param lockId the primary key for the new lock
	* @return the new lock
	*/
	@Override
	public com.liferay.portal.model.Lock createLock(long lockId) {
		return _lockLocalService.createLock(lockId);
	}

	/**
	* Deletes the lock with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lockId the primary key of the lock
	* @return the lock that was removed
	* @throws PortalException if a lock with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Lock deleteLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.deleteLock(lockId);
	}

	/**
	* Deletes the lock from the database. Also notifies the appropriate model listeners.
	*
	* @param lock the lock
	* @return the lock that was removed
	*/
	@Override
	public com.liferay.portal.model.Lock deleteLock(
		com.liferay.portal.model.Lock lock) {
		return _lockLocalService.deleteLock(lock);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _lockLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _lockLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _lockLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _lockLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _lockLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _lockLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.Lock fetchLock(long lockId) {
		return _lockLocalService.fetchLock(lockId);
	}

	/**
	* Returns the lock with the matching UUID and company.
	*
	* @param uuid the lock's UUID
	* @param companyId the primary key of the company
	* @return the matching lock, or <code>null</code> if a matching lock could not be found
	*/
	@Override
	public com.liferay.portal.model.Lock fetchLockByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _lockLocalService.fetchLockByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the lock with the primary key.
	*
	* @param lockId the primary key of the lock
	* @return the lock
	* @throws PortalException if a lock with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Lock getLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.getLock(lockId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _lockLocalService.getActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the lock with the matching UUID and company.
	*
	* @param uuid the lock's UUID
	* @param companyId the primary key of the company
	* @return the matching lock
	* @throws PortalException if a matching lock could not be found
	*/
	@Override
	public com.liferay.portal.model.Lock getLockByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.getLockByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.portal.model.Lock> getLocks(int start,
		int end) {
		return _lockLocalService.getLocks(start, end);
	}

	/**
	* Returns the number of locks.
	*
	* @return the number of locks
	*/
	@Override
	public int getLocksCount() {
		return _lockLocalService.getLocksCount();
	}

	/**
	* Updates the lock in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param lock the lock
	* @return the lock that was updated
	*/
	@Override
	public com.liferay.portal.model.Lock updateLock(
		com.liferay.portal.model.Lock lock) {
		return _lockLocalService.updateLock(lock);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _lockLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_lockLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void clear() {
		_lockLocalService.clear();
	}

	@Override
	public com.liferay.portal.model.Lock getLock(java.lang.String className,
		long key) throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.getLock(className, key);
	}

	@Override
	public com.liferay.portal.model.Lock getLock(java.lang.String className,
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.getLock(className, key);
	}

	@Override
	public boolean hasLock(long userId, java.lang.String className, long key) {
		return _lockLocalService.hasLock(userId, className, key);
	}

	@Override
	public boolean hasLock(long userId, java.lang.String className,
		java.lang.String key) {
		return _lockLocalService.hasLock(userId, className, key);
	}

	@Override
	public boolean isLocked(java.lang.String className, long key) {
		return _lockLocalService.isLocked(className, key);
	}

	@Override
	public boolean isLocked(java.lang.String className, java.lang.String key) {
		return _lockLocalService.isLocked(className, key);
	}

	@Override
	public com.liferay.portal.model.Lock lock(long userId,
		java.lang.String className, long key, java.lang.String owner,
		boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.lock(userId, className, key, owner,
			inheritable, expirationTime);
	}

	@Override
	public com.liferay.portal.model.Lock lock(long userId,
		java.lang.String className, java.lang.String key,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.lock(userId, className, key, owner,
			inheritable, expirationTime);
	}

	@Override
	public com.liferay.portal.model.Lock lock(java.lang.String className,
		java.lang.String key, java.lang.String owner) {
		return _lockLocalService.lock(className, key, owner);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #lock(String, String,
	String)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.Lock lock(java.lang.String className,
		java.lang.String key, java.lang.String owner, boolean retrieveFromCache) {
		return _lockLocalService.lock(className, key, owner, retrieveFromCache);
	}

	@Override
	public com.liferay.portal.model.Lock lock(java.lang.String className,
		java.lang.String key, java.lang.String expectedOwner,
		java.lang.String updatedOwner) {
		return _lockLocalService.lock(className, key, expectedOwner,
			updatedOwner);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #lock(String, String, String,
	String)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.Lock lock(java.lang.String className,
		java.lang.String key, java.lang.String expectedOwner,
		java.lang.String updatedOwner, boolean retrieveFromCache) {
		return _lockLocalService.lock(className, key, expectedOwner,
			updatedOwner, retrieveFromCache);
	}

	@Override
	public com.liferay.portal.model.Lock refresh(java.lang.String uuid,
		long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lockLocalService.refresh(uuid, companyId, expirationTime);
	}

	@Override
	public void unlock(java.lang.String className, long key) {
		_lockLocalService.unlock(className, key);
	}

	@Override
	public void unlock(java.lang.String className, java.lang.String key) {
		_lockLocalService.unlock(className, key);
	}

	@Override
	public void unlock(java.lang.String className, java.lang.String key,
		java.lang.String owner) {
		_lockLocalService.unlock(className, key, owner);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #unlock(String, String,
	String)}
	*/
	@Deprecated
	@Override
	public void unlock(java.lang.String className, java.lang.String key,
		java.lang.String owner, boolean retrieveFromCache) {
		_lockLocalService.unlock(className, key, owner, retrieveFromCache);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public LockLocalService getWrappedLockLocalService() {
		return _lockLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedLockLocalService(LockLocalService lockLocalService) {
		_lockLocalService = lockLocalService;
	}

	@Override
	public LockLocalService getWrappedService() {
		return _lockLocalService;
	}

	@Override
	public void setWrappedService(LockLocalService lockLocalService) {
		_lockLocalService = lockLocalService;
	}

	private LockLocalService _lockLocalService;
}