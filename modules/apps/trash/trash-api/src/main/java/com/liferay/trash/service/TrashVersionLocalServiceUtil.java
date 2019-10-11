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

package com.liferay.trash.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for TrashVersion. This utility wraps
 * <code>com.liferay.trash.service.impl.TrashVersionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see TrashVersionLocalService
 * @generated
 */
public class TrashVersionLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.trash.service.impl.TrashVersionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TrashVersionLocalServiceUtil} to access the trash version local service. Add custom service methods to <code>com.liferay.trash.service.impl.TrashVersionLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.trash.model.TrashVersion addTrashVersion(
		long trashEntryId, String className, long classPK, int status,
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsProperties) {

		return getService().addTrashVersion(
			trashEntryId, className, classPK, status, typeSettingsProperties);
	}

	/**
	 * Adds the trash version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashVersion the trash version
	 * @return the trash version that was added
	 */
	public static com.liferay.trash.model.TrashVersion addTrashVersion(
		com.liferay.trash.model.TrashVersion trashVersion) {

		return getService().addTrashVersion(trashVersion);
	}

	/**
	 * Creates a new trash version with the primary key. Does not add the trash version to the database.
	 *
	 * @param versionId the primary key for the new trash version
	 * @return the new trash version
	 */
	public static com.liferay.trash.model.TrashVersion createTrashVersion(
		long versionId) {

		return getService().createTrashVersion(versionId);
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
	 * Deletes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version that was removed
	 * @throws PortalException if a trash version with the primary key could not be found
	 */
	public static com.liferay.trash.model.TrashVersion deleteTrashVersion(
			long versionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTrashVersion(versionId);
	}

	public static com.liferay.trash.model.TrashVersion deleteTrashVersion(
		String className, long classPK) {

		return getService().deleteTrashVersion(className, classPK);
	}

	/**
	 * Deletes the trash version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashVersion the trash version
	 * @return the trash version that was removed
	 */
	public static com.liferay.trash.model.TrashVersion deleteTrashVersion(
		com.liferay.trash.model.TrashVersion trashVersion) {

		return getService().deleteTrashVersion(trashVersion);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.trash.model.impl.TrashVersionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.trash.model.impl.TrashVersionModelImpl</code>.
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

	public static com.liferay.trash.model.TrashVersion fetchTrashVersion(
		long versionId) {

		return getService().fetchTrashVersion(versionId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #fetchVersion(String, long)}
	 */
	@Deprecated
	public static com.liferay.trash.model.TrashVersion fetchVersion(
		long entryId, String className, long classPK) {

		return getService().fetchVersion(entryId, className, classPK);
	}

	public static com.liferay.trash.model.TrashVersion fetchVersion(
		String className, long classPK) {

		return getService().fetchVersion(className, classPK);
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
	 * Returns the trash version with the primary key.
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version
	 * @throws PortalException if a trash version with the primary key could not be found
	 */
	public static com.liferay.trash.model.TrashVersion getTrashVersion(
			long versionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTrashVersion(versionId);
	}

	/**
	 * Returns a range of all the trash versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.trash.model.impl.TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @return the range of trash versions
	 */
	public static java.util.List<com.liferay.trash.model.TrashVersion>
		getTrashVersions(int start, int end) {

		return getService().getTrashVersions(start, end);
	}

	/**
	 * Returns the number of trash versions.
	 *
	 * @return the number of trash versions
	 */
	public static int getTrashVersionsCount() {
		return getService().getTrashVersionsCount();
	}

	public static java.util.List<com.liferay.trash.model.TrashVersion>
		getVersions(long entryId) {

		return getService().getVersions(entryId);
	}

	public static java.util.List<com.liferay.trash.model.TrashVersion>
		getVersions(long entryId, String className) {

		return getService().getVersions(entryId, className);
	}

	/**
	 * Updates the trash version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param trashVersion the trash version
	 * @return the trash version that was updated
	 */
	public static com.liferay.trash.model.TrashVersion updateTrashVersion(
		com.liferay.trash.model.TrashVersion trashVersion) {

		return getService().updateTrashVersion(trashVersion);
	}

	public static TrashVersionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<TrashVersionLocalService, TrashVersionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(TrashVersionLocalService.class);

		ServiceTracker<TrashVersionLocalService, TrashVersionLocalService>
			serviceTracker =
				new ServiceTracker
					<TrashVersionLocalService, TrashVersionLocalService>(
						bundle.getBundleContext(),
						TrashVersionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}