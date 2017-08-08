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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AdaptiveMediaImageEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImageEntryLocalService
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageEntryLocalServiceWrapper
	implements AdaptiveMediaImageEntryLocalService,
		ServiceWrapper<AdaptiveMediaImageEntryLocalService> {
	public AdaptiveMediaImageEntryLocalServiceWrapper(
		AdaptiveMediaImageEntryLocalService adaptiveMediaImageEntryLocalService) {
		_adaptiveMediaImageEntryLocalService = adaptiveMediaImageEntryLocalService;
	}

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
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry addAdaptiveMediaImageEntry(
		com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry configurationEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		int width, int height, java.io.InputStream inputStream, int size)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _adaptiveMediaImageEntryLocalService.addAdaptiveMediaImageEntry(configurationEntry,
			fileVersion, width, height, inputStream, size);
	}

	/**
	* Adds the adaptive media image entry to the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	* @return the adaptive media image entry that was added
	*/
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry addAdaptiveMediaImageEntry(
		com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		return _adaptiveMediaImageEntryLocalService.addAdaptiveMediaImageEntry(adaptiveMediaImageEntry);
	}

	/**
	* Creates a new adaptive media image entry with the primary key. Does not add the adaptive media image entry to the database.
	*
	* @param adaptiveMediaImageEntryId the primary key for the new adaptive media image entry
	* @return the new adaptive media image entry
	*/
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry createAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId) {
		return _adaptiveMediaImageEntryLocalService.createAdaptiveMediaImageEntry(adaptiveMediaImageEntryId);
	}

	/**
	* Deletes the adaptive media image entry from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	* @return the adaptive media image entry that was removed
	*/
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry deleteAdaptiveMediaImageEntry(
		com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		return _adaptiveMediaImageEntryLocalService.deleteAdaptiveMediaImageEntry(adaptiveMediaImageEntry);
	}

	/**
	* Deletes the adaptive media image entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry that was removed
	* @throws PortalException if a adaptive media image entry with the primary key could not be found
	*/
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry deleteAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _adaptiveMediaImageEntryLocalService.deleteAdaptiveMediaImageEntry(adaptiveMediaImageEntryId);
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
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntry(
		java.lang.String configurationUuid, long fileVersionId) {
		return _adaptiveMediaImageEntryLocalService.fetchAdaptiveMediaImageEntry(configurationUuid,
			fileVersionId);
	}

	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId) {
		return _adaptiveMediaImageEntryLocalService.fetchAdaptiveMediaImageEntry(adaptiveMediaImageEntryId);
	}

	/**
	* Returns the adaptive media image entry matching the UUID and group.
	*
	* @param uuid the adaptive media image entry's UUID
	* @param groupId the primary key of the group
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _adaptiveMediaImageEntryLocalService.fetchAdaptiveMediaImageEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the adaptive media image entry with the primary key.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry
	* @throws PortalException if a adaptive media image entry with the primary key could not be found
	*/
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry getAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _adaptiveMediaImageEntryLocalService.getAdaptiveMediaImageEntry(adaptiveMediaImageEntryId);
	}

	/**
	* Returns the adaptive media image entry matching the UUID and group.
	*
	* @param uuid the adaptive media image entry's UUID
	* @param groupId the primary key of the group
	* @return the matching adaptive media image entry
	* @throws PortalException if a matching adaptive media image entry could not be found
	*/
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry getAdaptiveMediaImageEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _adaptiveMediaImageEntryLocalService.getAdaptiveMediaImageEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the adaptive media image entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	* @return the adaptive media image entry that was updated
	*/
	@Override
	public com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry updateAdaptiveMediaImageEntry(
		com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		return _adaptiveMediaImageEntryLocalService.updateAdaptiveMediaImageEntry(adaptiveMediaImageEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _adaptiveMediaImageEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _adaptiveMediaImageEntryLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _adaptiveMediaImageEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _adaptiveMediaImageEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _adaptiveMediaImageEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of adaptive media image entries.
	*
	* @return the number of adaptive media image entries
	*/
	@Override
	public int getAdaptiveMediaImageEntriesCount() {
		return _adaptiveMediaImageEntryLocalService.getAdaptiveMediaImageEntriesCount();
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
	@Override
	public int getAdaptiveMediaImageEntriesCount(long companyId,
		java.lang.String configurationUuid) {
		return _adaptiveMediaImageEntryLocalService.getAdaptiveMediaImageEntriesCount(companyId,
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
	@Override
	public int getExpectedAdaptiveMediaImageEntriesCount(long companyId) {
		return _adaptiveMediaImageEntryLocalService.getExpectedAdaptiveMediaImageEntriesCount(companyId);
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
	@Override
	public int getPercentage(long companyId, java.lang.String configurationUuid) {
		return _adaptiveMediaImageEntryLocalService.getPercentage(companyId,
			configurationUuid);
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
	@Override
	public java.io.InputStream getAdaptiveMediaImageEntryContentStream(
		com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry configurationEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion) {
		return _adaptiveMediaImageEntryLocalService.getAdaptiveMediaImageEntryContentStream(configurationEntry,
			fileVersion);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _adaptiveMediaImageEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _adaptiveMediaImageEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _adaptiveMediaImageEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _adaptiveMediaImageEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
	@Override
	public java.util.List<com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry> getAdaptiveMediaImageEntries(
		int start, int end) {
		return _adaptiveMediaImageEntryLocalService.getAdaptiveMediaImageEntries(start,
			end);
	}

	/**
	* Returns all the adaptive media image entries matching the UUID and company.
	*
	* @param uuid the UUID of the adaptive media image entries
	* @param companyId the primary key of the company
	* @return the matching adaptive media image entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry> getAdaptiveMediaImageEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _adaptiveMediaImageEntryLocalService.getAdaptiveMediaImageEntriesByUuidAndCompanyId(uuid,
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
	@Override
	public java.util.List<com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry> getAdaptiveMediaImageEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry> orderByComparator) {
		return _adaptiveMediaImageEntryLocalService.getAdaptiveMediaImageEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _adaptiveMediaImageEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _adaptiveMediaImageEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
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
	@Override
	public void deleteAdaptiveMediaImageEntries(long companyId,
		com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry configurationEntry) {
		_adaptiveMediaImageEntryLocalService.deleteAdaptiveMediaImageEntries(companyId,
			configurationEntry);
	}

	/**
	* Deletes all the adaptive media images generated for a file version. This
	* method deletes both the adaptive media image entry from the database and
	* the bytes from the file store.
	*
	* @param fileVersion the file version
	* @throws PortalException if the file version was not found
	*/
	@Override
	public void deleteAdaptiveMediaImageEntryFileVersion(
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		_adaptiveMediaImageEntryLocalService.deleteAdaptiveMediaImageEntryFileVersion(fileVersion);
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
	@Override
	public void deleteAdaptiveMediaImageEntryFileVersion(
		java.lang.String configurationUuid, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_adaptiveMediaImageEntryLocalService.deleteAdaptiveMediaImageEntryFileVersion(configurationUuid,
			fileVersionId);
	}

	@Override
	public AdaptiveMediaImageEntryLocalService getWrappedService() {
		return _adaptiveMediaImageEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AdaptiveMediaImageEntryLocalService adaptiveMediaImageEntryLocalService) {
		_adaptiveMediaImageEntryLocalService = adaptiveMediaImageEntryLocalService;
	}

	private AdaptiveMediaImageEntryLocalService _adaptiveMediaImageEntryLocalService;
}