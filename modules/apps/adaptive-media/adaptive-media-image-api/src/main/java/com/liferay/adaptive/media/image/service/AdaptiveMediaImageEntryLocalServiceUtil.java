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

package com.liferay.adaptive.media.image.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AdaptiveMediaImageEntry. This utility wraps
 * {@link com.liferay.adaptive.media.image.service.impl.AdaptiveMediaImageEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImageEntryLocalService
 * @see com.liferay.adaptive.media.image.service.base.AdaptiveMediaImageEntryLocalServiceBaseImpl
 * @see com.liferay.adaptive.media.image.service.impl.AdaptiveMediaImageEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.adaptive.media.image.service.impl.AdaptiveMediaImageEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds an adaptive media image entry in the database and stores the image
	* bytes in the file store.
	*
	* @param configurationEntry the configuration used to create the adaptive
	media image
	* @param fileVersion the file version used to create the adaptive media
	image
	* @param width the adaptive media image's width
	* @param height the adaptive media image's height
	* @param inputStream the adaptive media image's input stream to store in
	the file store
	* @param size the adaptive media image's size
	* @return the adaptive media image
	* @throws PortalException if an adaptive media image already exists for the
	file version and configuration
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry addAdaptiveMediaImageEntry(
		com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry configurationEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		int width, int height, java.io.InputStream inputStream, int size)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addAdaptiveMediaImageEntry(configurationEntry, fileVersion,
			width, height, inputStream, size);
	}

	/**
	* Adds the adaptive media image entry to the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	* @return the adaptive media image entry that was added
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry addAdaptiveMediaImageEntry(
		com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		return getService().addAdaptiveMediaImageEntry(adaptiveMediaImageEntry);
	}

	/**
	* Creates a new adaptive media image entry with the primary key. Does not add the adaptive media image entry to the database.
	*
	* @param adaptiveMediaImageEntryId the primary key for the new adaptive media image entry
	* @return the new adaptive media image entry
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry createAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId) {
		return getService()
				   .createAdaptiveMediaImageEntry(adaptiveMediaImageEntryId);
	}

	/**
	* Deletes the adaptive media image entry from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	* @return the adaptive media image entry that was removed
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry deleteAdaptiveMediaImageEntry(
		com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		return getService()
				   .deleteAdaptiveMediaImageEntry(adaptiveMediaImageEntry);
	}

	/**
	* Deletes the adaptive media image entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry that was removed
	* @throws PortalException if a adaptive media image entry with the primary key could not be found
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry deleteAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteAdaptiveMediaImageEntry(adaptiveMediaImageEntryId);
	}

	/**
	* Returns the adaptive media image entry generated for the configuration
	* and file version.
	*
	* @param configurationUuid the UUID of the configuration used to create
	the adaptive media image
	* @param fileVersionId the primary key of the file version
	* @return the matching adaptive media image entry, or <code>null</code> if
	a matching adaptive media image entry could not be found
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntry(
		java.lang.String configurationUuid, long fileVersionId) {
		return getService()
				   .fetchAdaptiveMediaImageEntry(configurationUuid,
			fileVersionId);
	}

	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId) {
		return getService()
				   .fetchAdaptiveMediaImageEntry(adaptiveMediaImageEntryId);
	}

	/**
	* Returns the adaptive media image entry matching the UUID and group.
	*
	* @param uuid the adaptive media image entry's UUID
	* @param groupId the primary key of the group
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchAdaptiveMediaImageEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the adaptive media image entry with the primary key.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry
	* @throws PortalException if a adaptive media image entry with the primary key could not be found
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry getAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAdaptiveMediaImageEntry(adaptiveMediaImageEntryId);
	}

	/**
	* Returns the adaptive media image entry matching the UUID and group.
	*
	* @param uuid the adaptive media image entry's UUID
	* @param groupId the primary key of the group
	* @return the matching adaptive media image entry
	* @throws PortalException if a matching adaptive media image entry could not be found
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry getAdaptiveMediaImageEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getAdaptiveMediaImageEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the adaptive media image entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	* @return the adaptive media image entry that was updated
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry updateAdaptiveMediaImageEntry(
		com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		return getService()
				   .updateAdaptiveMediaImageEntry(adaptiveMediaImageEntry);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of adaptive media image entries.
	*
	* @return the number of adaptive media image entries
	*/
	public static int getAdaptiveMediaImageEntriesCount() {
		return getService().getAdaptiveMediaImageEntriesCount();
	}

	/**
	* Returns the number of adaptive media image entries generated for the
	* configuration in the company.
	*
	* @param companyId the primary key of the company
	* @param configurationUuid the UUID of the configuration used to create
	the adaptive media image
	* @return the number of adaptive media image entries in the company for the
	configuration
	*/
	public static int getAdaptiveMediaImageEntriesCount(long companyId,
		java.lang.String configurationUuid) {
		return getService()
				   .getAdaptiveMediaImageEntriesCount(companyId,
			configurationUuid);
	}

	/**
	* Returns the total number of adaptive media images that are expected to be
	* in a company once they are generated. The number of adaptive media images
	* could be less if there are images that haven't generated the adaptive
	* media image yet.
	*
	* @param companyId the primary key of the company
	* @return the number of expected adaptive media images for a company
	*/
	public static int getExpectedAdaptiveMediaImageEntriesCount(long companyId) {
		return getService().getExpectedAdaptiveMediaImageEntriesCount(companyId);
	}

	/**
	* Returns the percentage of images that have an adaptive media image
	* generated based on the expected number of adaptive media images for a
	* configuration in a company.
	*
	* @param companyId the primary key of the company
	* @param configurationUuid the UUID of the configuration used to create
	the adaptive media image
	* @return the percentage of images that have an adaptive media image out of
	the expected adaptive media images
	*/
	public static int getPercentage(long companyId,
		java.lang.String configurationUuid) {
		return getService().getPercentage(companyId, configurationUuid);
	}

	/**
	* Returns the input stream of the adaptive media image generated for a file
	* version and configuration.
	*
	* @param configurationEntry the configuration used to create the adaptive
	media image
	* @param fileVersion the file version used to create the adaptive media
	image
	* @return the input stream of the adaptive media image generated for a file
	version and configuration
	*/
	public static java.io.InputStream getAdaptiveMediaImageEntryContentStream(
		com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry configurationEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion) {
		return getService()
				   .getAdaptiveMediaImageEntryContentStream(configurationEntry,
			fileVersion);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns a range of all the adaptive media image entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @return the range of adaptive media image entries
	*/
	public static java.util.List<com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry> getAdaptiveMediaImageEntries(
		int start, int end) {
		return getService().getAdaptiveMediaImageEntries(start, end);
	}

	/**
	* Returns all the adaptive media image entries matching the UUID and company.
	*
	* @param uuid the UUID of the adaptive media image entries
	* @param companyId the primary key of the company
	* @return the matching adaptive media image entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry> getAdaptiveMediaImageEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getAdaptiveMediaImageEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of adaptive media image entries matching the UUID and company.
	*
	* @param uuid the UUID of the adaptive media image entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching adaptive media image entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry> getAdaptiveMediaImageEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry> orderByComparator) {
		return getService()
				   .getAdaptiveMediaImageEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
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

	/**
	* Deletes all the adaptive media images generated for the configuration in
	* the company. This method deletes both the adaptive media image entry from
	* the database and the bytes from the file store.
	*
	* @param companyId the primary key of the company
	* @param configurationEntry the configuration used to create the adaptive
	media image
	*/
	public static void deleteAdaptiveMediaImageEntries(long companyId,
		com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry configurationEntry) {
		getService()
			.deleteAdaptiveMediaImageEntries(companyId, configurationEntry);
	}

	/**
	* Deletes all the adaptive media images generated for a file version. This
	* method deletes both the adaptive media image entry from the database and
	* the bytes from the file store.
	*
	* @param fileVersion the file version
	* @throws PortalException if the file version was not found
	*/
	public static void deleteAdaptiveMediaImageEntryFileVersion(
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteAdaptiveMediaImageEntryFileVersion(fileVersion);
	}

	/**
	* Deletes adaptive media images generated for a file version under a given
	* configuration. This method deletes both the adaptive media image entry
	* from the database and the bytes from the file store.
	*
	* @param configurationUuid the configuration UUID
	* @param fileVersionId the primary key of the file version
	* @throws PortalException if the file version was not found
	*/
	public static void deleteAdaptiveMediaImageEntryFileVersion(
		java.lang.String configurationUuid, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteAdaptiveMediaImageEntryFileVersion(configurationUuid,
			fileVersionId);
	}

	public static AdaptiveMediaImageEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AdaptiveMediaImageEntryLocalService, AdaptiveMediaImageEntryLocalService> _serviceTracker =
		ServiceTrackerFactory.open(AdaptiveMediaImageEntryLocalService.class);
}