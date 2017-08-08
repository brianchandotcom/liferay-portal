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

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for AdaptiveMediaImageEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImageEntryLocalServiceUtil
 * @see com.liferay.adaptive.media.image.service.base.AdaptiveMediaImageEntryLocalServiceBaseImpl
 * @see com.liferay.adaptive.media.image.service.impl.AdaptiveMediaImageEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AdaptiveMediaImageEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AdaptiveMediaImageEntryLocalServiceUtil} to access the adaptive media image entry local service. Add custom service methods to {@link com.liferay.adaptive.media.image.service.impl.AdaptiveMediaImageEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
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
	public AdaptiveMediaImageEntry addAdaptiveMediaImageEntry(
		AdaptiveMediaImageConfigurationEntry configurationEntry,
		FileVersion fileVersion, int width, int height,
		InputStream inputStream, int size) throws PortalException;

	/**
	* Adds the adaptive media image entry to the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	* @return the adaptive media image entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public AdaptiveMediaImageEntry addAdaptiveMediaImageEntry(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry);

	/**
	* Creates a new adaptive media image entry with the primary key. Does not add the adaptive media image entry to the database.
	*
	* @param adaptiveMediaImageEntryId the primary key for the new adaptive media image entry
	* @return the new adaptive media image entry
	*/
	public AdaptiveMediaImageEntry createAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId);

	/**
	* Deletes the adaptive media image entry from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	* @return the adaptive media image entry that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public AdaptiveMediaImageEntry deleteAdaptiveMediaImageEntry(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry);

	/**
	* Deletes the adaptive media image entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry that was removed
	* @throws PortalException if a adaptive media image entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public AdaptiveMediaImageEntry deleteAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId) throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntry(
		java.lang.String configurationUuid, long fileVersionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId);

	/**
	* Returns the adaptive media image entry matching the UUID and group.
	*
	* @param uuid the adaptive media image entry's UUID
	* @param groupId the primary key of the group
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AdaptiveMediaImageEntry fetchAdaptiveMediaImageEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	/**
	* Returns the adaptive media image entry with the primary key.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry
	* @throws PortalException if a adaptive media image entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AdaptiveMediaImageEntry getAdaptiveMediaImageEntry(
		long adaptiveMediaImageEntryId) throws PortalException;

	/**
	* Returns the adaptive media image entry matching the UUID and group.
	*
	* @param uuid the adaptive media image entry's UUID
	* @param groupId the primary key of the group
	* @return the matching adaptive media image entry
	* @throws PortalException if a matching adaptive media image entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AdaptiveMediaImageEntry getAdaptiveMediaImageEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Updates the adaptive media image entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	* @return the adaptive media image entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public AdaptiveMediaImageEntry updateAdaptiveMediaImageEntry(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Returns the number of adaptive media image entries.
	*
	* @return the number of adaptive media image entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAdaptiveMediaImageEntriesCount();

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAdaptiveMediaImageEntriesCount(long companyId,
		java.lang.String configurationUuid);

	/**
	* Returns the total number of adaptive media images that are expected to be
	* in a company once they are generated. The number of adaptive media images
	* could be less if there are images that haven't generated the adaptive
	* media image yet.
	*
	* @param companyId the primary key of the company
	* @return the number of expected adaptive media images for a company
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getExpectedAdaptiveMediaImageEntriesCount(long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPercentage(long companyId, java.lang.String configurationUuid);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InputStream getAdaptiveMediaImageEntryContentStream(
		AdaptiveMediaImageConfigurationEntry configurationEntry,
		FileVersion fileVersion);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AdaptiveMediaImageEntry> getAdaptiveMediaImageEntries(
		int start, int end);

	/**
	* Returns all the adaptive media image entries matching the UUID and company.
	*
	* @param uuid the UUID of the adaptive media image entries
	* @param companyId the primary key of the company
	* @return the matching adaptive media image entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AdaptiveMediaImageEntry> getAdaptiveMediaImageEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AdaptiveMediaImageEntry> getAdaptiveMediaImageEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	/**
	* Deletes all the adaptive media images generated for the configuration in
	* the company. This method deletes both the adaptive media image entry from
	* the database and the bytes from the file store.
	*
	* @param companyId the primary key of the company
	* @param configurationEntry the configuration used to create the adaptive
	media image
	*/
	public void deleteAdaptiveMediaImageEntries(long companyId,
		AdaptiveMediaImageConfigurationEntry configurationEntry);

	/**
	* Deletes all the adaptive media images generated for a file version. This
	* method deletes both the adaptive media image entry from the database and
	* the bytes from the file store.
	*
	* @param fileVersion the file version
	* @throws PortalException if the file version was not found
	*/
	public void deleteAdaptiveMediaImageEntryFileVersion(
		FileVersion fileVersion) throws PortalException;

	/**
	* Deletes adaptive media images generated for a file version under a given
	* configuration. This method deletes both the adaptive media image entry
	* from the database and the bytes from the file store.
	*
	* @param configurationUuid the configuration UUID
	* @param fileVersionId the primary key of the file version
	* @throws PortalException if the file version was not found
	*/
	public void deleteAdaptiveMediaImageEntryFileVersion(
		java.lang.String configurationUuid, long fileVersionId)
		throws PortalException;
}