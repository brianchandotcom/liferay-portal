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

package com.liferay.change.tracking.store.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CTSContent. This utility wraps
 * <code>com.liferay.change.tracking.store.service.impl.CTSContentLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Shuyang Zhou
 * @see CTSContentLocalService
 * @generated
 */
public class CTSContentLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.store.service.impl.CTSContentLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the cts content to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctsContent the cts content
	 * @return the cts content that was added
	 */
	public static com.liferay.change.tracking.store.model.CTSContent
		addCTSContent(
			com.liferay.change.tracking.store.model.CTSContent ctsContent) {

		return getService().addCTSContent(ctsContent);
	}

	public static com.liferay.change.tracking.store.model.CTSContent
		addCTSContent(
			long companyId, long repositoryId, String path, String version,
			String storeType, java.io.InputStream inputStream) {

		return getService().addCTSContent(
			companyId, repositoryId, path, version, storeType, inputStream);
	}

	/**
	 * Creates a new cts content with the primary key. Does not add the cts content to the database.
	 *
	 * @param ctsContentId the primary key for the new cts content
	 * @return the new cts content
	 */
	public static com.liferay.change.tracking.store.model.CTSContent
		createCTSContent(long ctsContentId) {

		return getService().createCTSContent(ctsContentId);
	}

	/**
	 * Deletes the cts content from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctsContent the cts content
	 * @return the cts content that was removed
	 */
	public static com.liferay.change.tracking.store.model.CTSContent
		deleteCTSContent(
			com.liferay.change.tracking.store.model.CTSContent ctsContent) {

		return getService().deleteCTSContent(ctsContent);
	}

	/**
	 * Deletes the cts content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content that was removed
	 * @throws PortalException if a cts content with the primary key could not be found
	 */
	public static com.liferay.change.tracking.store.model.CTSContent
			deleteCTSContent(long ctsContentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCTSContent(ctsContentId);
	}

	public static void deleteCTSContent(
		long companyId, long repositoryId, String path, String version,
		String storeType) {

		getService().deleteCTSContent(
			companyId, repositoryId, path, version, storeType);
	}

	public static void deleteCTSContentsByDirectory(
		long companyId, long repositoryId, String dirName, String storeType) {

		getService().deleteCTSContentsByDirectory(
			companyId, repositoryId, dirName, storeType);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.store.model.impl.CTSContentModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.store.model.impl.CTSContentModelImpl</code>.
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

	public static com.liferay.change.tracking.store.model.CTSContent
		fetchCTSContent(long ctsContentId) {

		return getService().fetchCTSContent(ctsContentId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cts content with the primary key.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content
	 * @throws PortalException if a cts content with the primary key could not be found
	 */
	public static com.liferay.change.tracking.store.model.CTSContent
			getCTSContent(long ctsContentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCTSContent(ctsContentId);
	}

	public static com.liferay.change.tracking.store.model.CTSContent
			getCTSContent(
				long companyId, long repositoryId, String path, String version,
				String storeType)
		throws com.liferay.change.tracking.store.exception.
			NoSuchContentException {

		return getService().getCTSContent(
			companyId, repositoryId, path, version, storeType);
	}

	/**
	 * Returns a range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.store.model.impl.CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of cts contents
	 */
	public static java.util.List
		<com.liferay.change.tracking.store.model.CTSContent> getCTSContents(
			int start, int end) {

		return getService().getCTSContents(start, end);
	}

	public static java.util.List
		<com.liferay.change.tracking.store.model.CTSContent> getCTSContents(
			long companyId, long repositoryId, String path, String storeType) {

		return getService().getCTSContents(
			companyId, repositoryId, path, storeType);
	}

	public static java.util.List
		<com.liferay.change.tracking.store.model.CTSContent>
			getCTSContentsByDirectory(
				long companyId, long repositoryId, String dirName,
				String storeType) {

		return getService().getCTSContentsByDirectory(
			companyId, repositoryId, dirName, storeType);
	}

	/**
	 * Returns the number of cts contents.
	 *
	 * @return the number of cts contents
	 */
	public static int getCTSContentsCount() {
		return getService().getCTSContentsCount();
	}

	public static
		com.liferay.change.tracking.store.model.CTSContentDataBlobModel
			getDataBlobModel(java.io.Serializable primaryKey) {

		return getService().getDataBlobModel(primaryKey);
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

	public static boolean hasCTSContent(
		long companyId, long repositoryId, String path, String version,
		String storeType) {

		return getService().hasCTSContent(
			companyId, repositoryId, path, version, storeType);
	}

	public static java.io.InputStream openCTSContentInputStream(
		long contentId) {

		return getService().openCTSContentInputStream(contentId);
	}

	/**
	 * Updates the cts content in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ctsContent the cts content
	 * @return the cts content that was updated
	 */
	public static com.liferay.change.tracking.store.model.CTSContent
		updateCTSContent(
			com.liferay.change.tracking.store.model.CTSContent ctsContent) {

		return getService().updateCTSContent(ctsContent);
	}

	public static CTSContentLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CTSContentLocalService, CTSContentLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTSContentLocalService.class);

		ServiceTracker<CTSContentLocalService, CTSContentLocalService>
			serviceTracker =
				new ServiceTracker
					<CTSContentLocalService, CTSContentLocalService>(
						bundle.getBundleContext(), CTSContentLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}