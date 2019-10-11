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

package com.liferay.sync.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SyncDLObject. This utility wraps
 * <code>com.liferay.sync.service.impl.SyncDLObjectLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLObjectLocalService
 * @generated
 */
public class SyncDLObjectLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.sync.service.impl.SyncDLObjectLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #addSyncDLObject(long, long, String, long, long, long,
	 String, String, String, String, String, String, String,
	 String, long, long, String, String, String, Date, long,
	 String, String, long, String)}
	 */
	@Deprecated
	public static com.liferay.sync.model.SyncDLObject addSyncDLObject(
			long companyId, long userId, String userName, long modifiedTime,
			long repositoryId, long parentFolderId, String treePath,
			String name, String extension, String mimeType, String description,
			String changeLog, String extraSettings, String version,
			long versionId, long size, String checksum, String event,
			java.util.Date lockExpirationDate, long lockUserId,
			String lockUserName, String type, long typePK, String typeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSyncDLObject(
			companyId, userId, userName, modifiedTime, repositoryId,
			parentFolderId, treePath, name, extension, mimeType, description,
			changeLog, extraSettings, version, versionId, size, checksum, event,
			lockExpirationDate, lockUserId, lockUserName, type, typePK,
			typeUuid);
	}

	public static com.liferay.sync.model.SyncDLObject addSyncDLObject(
			long companyId, long userId, String userName, long modifiedTime,
			long repositoryId, long parentFolderId, String treePath,
			String name, String extension, String mimeType, String description,
			String changeLog, String extraSettings, String version,
			long versionId, long size, String checksum, String event,
			String lanTokenKey, java.util.Date lockExpirationDate,
			long lockUserId, String lockUserName, String type, long typePK,
			String typeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSyncDLObject(
			companyId, userId, userName, modifiedTime, repositoryId,
			parentFolderId, treePath, name, extension, mimeType, description,
			changeLog, extraSettings, version, versionId, size, checksum, event,
			lanTokenKey, lockExpirationDate, lockUserId, lockUserName, type,
			typePK, typeUuid);
	}

	/**
	 * Adds the sync dl object to the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObject the sync dl object
	 * @return the sync dl object that was added
	 */
	public static com.liferay.sync.model.SyncDLObject addSyncDLObject(
		com.liferay.sync.model.SyncDLObject syncDLObject) {

		return getService().addSyncDLObject(syncDLObject);
	}

	/**
	 * Creates a new sync dl object with the primary key. Does not add the sync dl object to the database.
	 *
	 * @param syncDLObjectId the primary key for the new sync dl object
	 * @return the new sync dl object
	 */
	public static com.liferay.sync.model.SyncDLObject createSyncDLObject(
		long syncDLObjectId) {

		return getService().createSyncDLObject(syncDLObjectId);
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

	/**
	 * Deletes the sync dl object with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object that was removed
	 * @throws PortalException if a sync dl object with the primary key could not be found
	 */
	public static com.liferay.sync.model.SyncDLObject deleteSyncDLObject(
			long syncDLObjectId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSyncDLObject(syncDLObjectId);
	}

	/**
	 * Deletes the sync dl object from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObject the sync dl object
	 * @return the sync dl object that was removed
	 */
	public static com.liferay.sync.model.SyncDLObject deleteSyncDLObject(
		com.liferay.sync.model.SyncDLObject syncDLObject) {

		return getService().deleteSyncDLObject(syncDLObject);
	}

	public static void deleteSyncDLObjects(String version, String type) {
		getService().deleteSyncDLObjects(version, type);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDLObjectModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDLObjectModelImpl</code>.
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

	public static com.liferay.sync.model.SyncDLObject fetchSyncDLObject(
		long syncDLObjectId) {

		return getService().fetchSyncDLObject(syncDLObjectId);
	}

	public static com.liferay.sync.model.SyncDLObject fetchSyncDLObject(
		String type, long typePK) {

		return getService().fetchSyncDLObject(type, typePK);
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

	public static long getLatestModifiedTime() {
		return getService().getLatestModifiedTime();
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

	/**
	 * Returns the sync dl object with the primary key.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object
	 * @throws PortalException if a sync dl object with the primary key could not be found
	 */
	public static com.liferay.sync.model.SyncDLObject getSyncDLObject(
			long syncDLObjectId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSyncDLObject(syncDLObjectId);
	}

	/**
	 * Returns a range of all the sync dl objects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of sync dl objects
	 */
	public static java.util.List<com.liferay.sync.model.SyncDLObject>
		getSyncDLObjects(int start, int end) {

		return getService().getSyncDLObjects(start, end);
	}

	public static java.util.List<com.liferay.sync.model.SyncDLObject>
		getSyncDLObjects(long repositoryId, long parentFolderId) {

		return getService().getSyncDLObjects(repositoryId, parentFolderId);
	}

	/**
	 * Returns the number of sync dl objects.
	 *
	 * @return the number of sync dl objects
	 */
	public static int getSyncDLObjectsCount() {
		return getService().getSyncDLObjectsCount();
	}

	public static void moveSyncDLObjects(
			com.liferay.sync.model.SyncDLObject parentSyncDLObject)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().moveSyncDLObjects(parentSyncDLObject);
	}

	public static void restoreSyncDLObjects(
			com.liferay.sync.model.SyncDLObject parentSyncDLObject)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().restoreSyncDLObjects(parentSyncDLObject);
	}

	public static void trashSyncDLObjects(
			com.liferay.sync.model.SyncDLObject parentSyncDLObject)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().trashSyncDLObjects(parentSyncDLObject);
	}

	/**
	 * Updates the sync dl object in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObject the sync dl object
	 * @return the sync dl object that was updated
	 */
	public static com.liferay.sync.model.SyncDLObject updateSyncDLObject(
		com.liferay.sync.model.SyncDLObject syncDLObject) {

		return getService().updateSyncDLObject(syncDLObject);
	}

	public static SyncDLObjectLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SyncDLObjectLocalService, SyncDLObjectLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SyncDLObjectLocalService.class);

		ServiceTracker<SyncDLObjectLocalService, SyncDLObjectLocalService>
			serviceTracker =
				new ServiceTracker
					<SyncDLObjectLocalService, SyncDLObjectLocalService>(
						bundle.getBundleContext(),
						SyncDLObjectLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}