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

package com.liferay.portal.lock.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Lock. This utility wraps
 * <code>com.liferay.portal.lock.service.impl.LockLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LockLocalService
 * @generated
 */
public class LockLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.lock.service.impl.LockLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the lock to the database. Also notifies the appropriate model listeners.
	 *
	 * @param lock the lock
	 * @return the lock that was added
	 */
	public static com.liferay.portal.lock.model.Lock addLock(
		com.liferay.portal.lock.model.Lock lock) {

		return getService().addLock(lock);
	}

	public static void clear() {
		getService().clear();
	}

	/**
	 * Creates a new lock with the primary key. Does not add the lock to the database.
	 *
	 * @param lockId the primary key for the new lock
	 * @return the new lock
	 */
	public static com.liferay.portal.lock.model.Lock createLock(long lockId) {
		return getService().createLock(lockId);
	}

	/**
	 * Deletes the lock from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lock the lock
	 * @return the lock that was removed
	 */
	public static com.liferay.portal.lock.model.Lock deleteLock(
		com.liferay.portal.lock.model.Lock lock) {

		return getService().deleteLock(lock);
	}

	/**
	 * Deletes the lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lockId the primary key of the lock
	 * @return the lock that was removed
	 * @throws PortalException if a lock with the primary key could not be found
	 */
	public static com.liferay.portal.lock.model.Lock deleteLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLock(lockId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.lock.model.impl.LockModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.lock.model.impl.LockModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.lock.model.Lock fetchLock(long lockId) {
		return getService().fetchLock(lockId);
	}

	public static com.liferay.portal.lock.model.Lock fetchLock(
		String className, long key) {

		return getService().fetchLock(className, key);
	}

	public static com.liferay.portal.lock.model.Lock fetchLock(
		String className, String key) {

		return getService().fetchLock(className, key);
	}

	/**
	 * Returns the lock with the matching UUID and company.
	 *
	 * @param uuid the lock's UUID
	 * @param companyId the primary key of the company
	 * @return the matching lock, or <code>null</code> if a matching lock could not be found
	 */
	public static com.liferay.portal.lock.model.Lock
		fetchLockByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchLockByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the lock with the primary key.
	 *
	 * @param lockId the primary key of the lock
	 * @return the lock
	 * @throws PortalException if a lock with the primary key could not be found
	 */
	public static com.liferay.portal.lock.model.Lock getLock(long lockId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLock(lockId);
	}

	public static com.liferay.portal.lock.model.Lock getLock(
			String className, long key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLock(className, key);
	}

	public static com.liferay.portal.lock.model.Lock getLock(
			String className, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLock(className, key);
	}

	/**
	 * Returns the lock with the matching UUID and company.
	 *
	 * @param uuid the lock's UUID
	 * @param companyId the primary key of the company
	 * @return the matching lock
	 * @throws PortalException if a matching lock could not be found
	 */
	public static com.liferay.portal.lock.model.Lock getLockByUuidAndCompanyId(
			String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLockByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.lock.model.impl.LockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @return the range of locks
	 */
	public static java.util.List<com.liferay.portal.lock.model.Lock> getLocks(
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
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static boolean hasLock(long userId, String className, long key) {
		return getService().hasLock(userId, className, key);
	}

	public static boolean hasLock(long userId, String className, String key) {
		return getService().hasLock(userId, className, key);
	}

	public static boolean isLocked(String className, long key) {
		return getService().isLocked(className, key);
	}

	public static boolean isLocked(String className, String key) {
		return getService().isLocked(className, key);
	}

	public static com.liferay.portal.lock.model.Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static com.liferay.portal.lock.model.Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime, boolean renew)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().lock(
			userId, className, key, owner, inheritable, expirationTime, renew);
	}

	public static com.liferay.portal.lock.model.Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().lock(
			userId, className, key, owner, inheritable, expirationTime);
	}

	public static com.liferay.portal.lock.model.Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime, boolean renew)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().lock(
			userId, className, key, owner, inheritable, expirationTime, renew);
	}

	public static com.liferay.portal.lock.model.Lock lock(
		String className, String key, String owner) {

		return getService().lock(className, key, owner);
	}

	public static com.liferay.portal.lock.model.Lock lock(
		String className, String key, String expectedOwner,
		String updatedOwner) {

		return getService().lock(className, key, expectedOwner, updatedOwner);
	}

	public static com.liferay.portal.lock.model.Lock refresh(
			String uuid, long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().refresh(uuid, companyId, expirationTime);
	}

	public static void unlock(String className, long key) {
		getService().unlock(className, key);
	}

	public static void unlock(String className, String key) {
		getService().unlock(className, key);
	}

	public static void unlock(String className, String key, String owner) {
		getService().unlock(className, key, owner);
	}

	/**
	 * Updates the lock in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param lock the lock
	 * @return the lock that was updated
	 */
	public static com.liferay.portal.lock.model.Lock updateLock(
		com.liferay.portal.lock.model.Lock lock) {

		return getService().updateLock(lock);
	}

	public static LockLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LockLocalService, LockLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(LockLocalService.class);

		ServiceTracker<LockLocalService, LockLocalService> serviceTracker =
			new ServiceTracker<LockLocalService, LockLocalService>(
				bundle.getBundleContext(), LockLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}