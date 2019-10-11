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

package com.liferay.document.library.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DLFileVersionPreview. This utility wraps
 * <code>com.liferay.document.library.service.impl.DLFileVersionPreviewLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPreviewLocalService
 * @generated
 */
public class DLFileVersionPreviewLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.document.library.service.impl.DLFileVersionPreviewLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the dl file version preview to the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileVersionPreview the dl file version preview
	 * @return the dl file version preview that was added
	 */
	public static com.liferay.document.library.model.DLFileVersionPreview
		addDLFileVersionPreview(
			com.liferay.document.library.model.DLFileVersionPreview
				dlFileVersionPreview) {

		return getService().addDLFileVersionPreview(dlFileVersionPreview);
	}

	public static void addDLFileVersionPreview(
			long fileEntryId, long fileVersionId, int previewStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addDLFileVersionPreview(
			fileEntryId, fileVersionId, previewStatus);
	}

	/**
	 * Creates a new dl file version preview with the primary key. Does not add the dl file version preview to the database.
	 *
	 * @param dlFileVersionPreviewId the primary key for the new dl file version preview
	 * @return the new dl file version preview
	 */
	public static com.liferay.document.library.model.DLFileVersionPreview
		createDLFileVersionPreview(long dlFileVersionPreviewId) {

		return getService().createDLFileVersionPreview(dlFileVersionPreviewId);
	}

	public static void deleteDLFileEntryFileVersionPreviews(long fileEntryId) {
		getService().deleteDLFileEntryFileVersionPreviews(fileEntryId);
	}

	/**
	 * Deletes the dl file version preview from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileVersionPreview the dl file version preview
	 * @return the dl file version preview that was removed
	 */
	public static com.liferay.document.library.model.DLFileVersionPreview
		deleteDLFileVersionPreview(
			com.liferay.document.library.model.DLFileVersionPreview
				dlFileVersionPreview) {

		return getService().deleteDLFileVersionPreview(dlFileVersionPreview);
	}

	/**
	 * Deletes the dl file version preview with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileVersionPreviewId the primary key of the dl file version preview
	 * @return the dl file version preview that was removed
	 * @throws PortalException if a dl file version preview with the primary key could not be found
	 */
	public static com.liferay.document.library.model.DLFileVersionPreview
			deleteDLFileVersionPreview(long dlFileVersionPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDLFileVersionPreview(dlFileVersionPreviewId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.model.impl.DLFileVersionPreviewModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.model.impl.DLFileVersionPreviewModelImpl</code>.
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

	public static com.liferay.document.library.model.DLFileVersionPreview
		fetchDLFileVersionPreview(long dlFileVersionPreviewId) {

		return getService().fetchDLFileVersionPreview(dlFileVersionPreviewId);
	}

	public static com.liferay.document.library.model.DLFileVersionPreview
		fetchDLFileVersionPreview(long fileEntryId, long fileVersionId) {

		return getService().fetchDLFileVersionPreview(
			fileEntryId, fileVersionId);
	}

	public static com.liferay.document.library.model.DLFileVersionPreview
		fetchDLFileVersionPreview(
			long fileEntryId, long fileVersionId, int previewStatus) {

		return getService().fetchDLFileVersionPreview(
			fileEntryId, fileVersionId, previewStatus);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the dl file version preview with the primary key.
	 *
	 * @param dlFileVersionPreviewId the primary key of the dl file version preview
	 * @return the dl file version preview
	 * @throws PortalException if a dl file version preview with the primary key could not be found
	 */
	public static com.liferay.document.library.model.DLFileVersionPreview
			getDLFileVersionPreview(long dlFileVersionPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDLFileVersionPreview(dlFileVersionPreviewId);
	}

	public static com.liferay.document.library.model.DLFileVersionPreview
			getDLFileVersionPreview(long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDLFileVersionPreview(fileEntryId, fileVersionId);
	}

	public static com.liferay.document.library.model.DLFileVersionPreview
			getDLFileVersionPreview(
				long fileEntryId, long fileVersionId, int previewStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDLFileVersionPreview(
			fileEntryId, fileVersionId, previewStatus);
	}

	/**
	 * Returns a range of all the dl file version previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.model.impl.DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @return the range of dl file version previews
	 */
	public static java.util.List
		<com.liferay.document.library.model.DLFileVersionPreview>
			getDLFileVersionPreviews(int start, int end) {

		return getService().getDLFileVersionPreviews(start, end);
	}

	/**
	 * Returns the number of dl file version previews.
	 *
	 * @return the number of dl file version previews
	 */
	public static int getDLFileVersionPreviewsCount() {
		return getService().getDLFileVersionPreviewsCount();
	}

	public static java.util.List
		<com.liferay.document.library.model.DLFileVersionPreview>
			getFileEntryDLFileVersionPreviews(long fileEntryId) {

		return getService().getFileEntryDLFileVersionPreviews(fileEntryId);
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

	public static boolean hasDLFileVersionPreview(
		long fileEntryId, long fileVersionId, int previewStatus) {

		return getService().hasDLFileVersionPreview(
			fileEntryId, fileVersionId, previewStatus);
	}

	/**
	 * Updates the dl file version preview in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileVersionPreview the dl file version preview
	 * @return the dl file version preview that was updated
	 */
	public static com.liferay.document.library.model.DLFileVersionPreview
		updateDLFileVersionPreview(
			com.liferay.document.library.model.DLFileVersionPreview
				dlFileVersionPreview) {

		return getService().updateDLFileVersionPreview(dlFileVersionPreview);
	}

	public static void updateDLFileVersionPreview(
			long dlFileVersionPreviewId, int previewStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateDLFileVersionPreview(
			dlFileVersionPreviewId, previewStatus);
	}

	public static DLFileVersionPreviewLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DLFileVersionPreviewLocalService, DLFileVersionPreviewLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DLFileVersionPreviewLocalService.class);

		ServiceTracker
			<DLFileVersionPreviewLocalService, DLFileVersionPreviewLocalService>
				serviceTracker =
					new ServiceTracker
						<DLFileVersionPreviewLocalService,
						 DLFileVersionPreviewLocalService>(
							 bundle.getBundleContext(),
							 DLFileVersionPreviewLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}