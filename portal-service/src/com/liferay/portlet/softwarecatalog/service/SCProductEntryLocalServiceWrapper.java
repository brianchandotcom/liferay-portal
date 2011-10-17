/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SCProductEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductEntryLocalService
 * @generated
 */
public class SCProductEntryLocalServiceWrapper
	implements SCProductEntryLocalService,
		ServiceWrapper<SCProductEntryLocalService> {
	public SCProductEntryLocalServiceWrapper(
		SCProductEntryLocalService scProductEntryLocalService) {
		_scProductEntryLocalService = scProductEntryLocalService;
	}

	/**
	* Adds the s c product entry to the database. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @return the s c product entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.addSCProductEntry(scProductEntry);
	}

	/**
	* Creates a new s c product entry with the primary key. Does not add the s c product entry to the database.
	*
	* @param productEntryId the primary key for the new s c product entry
	* @return the new s c product entry
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry createSCProductEntry(
		long productEntryId) {
		return _scProductEntryLocalService.createSCProductEntry(productEntryId);
	}

	/**
	* Deletes the s c product entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productEntryId the primary key of the s c product entry
	* @throws PortalException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSCProductEntry(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteSCProductEntry(productEntryId);
	}

	/**
	* Deletes the s c product entry from the database. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteSCProductEntry(scProductEntry);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the s c product entry with the primary key.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry
	* @throws PortalException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getSCProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getSCProductEntry(productEntryId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the s c product entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getSCProductEntries(start, end);
	}

	/**
	* Returns the number of s c product entries.
	*
	* @return the number of s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public int getSCProductEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getSCProductEntriesCount();
	}

	/**
	* Updates the s c product entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @return the s c product entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.updateSCProductEntry(scProductEntry);
	}

	/**
	* Updates the s c product entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @param merge whether to merge the s c product entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the s c product entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.updateSCProductEntry(scProductEntry,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _scProductEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_scProductEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.addProductEntry(userId, name, type,
			tags, shortDescription, longDescription, pageURL, author,
			repoGroupId, repoArtifactId, licenseIds, thumbnails, fullImages,
			serviceContext);
	}

	public void addProductEntryResources(long productEntryId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.addProductEntryResources(productEntryId,
			addGroupPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(long productEntryId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.addProductEntryResources(productEntryId,
			groupPermissions, guestPermissions);
	}

	public void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.addProductEntryResources(productEntry,
			addGroupPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.addProductEntryResources(productEntry,
			groupPermissions, guestPermissions);
	}

	public void deleteProductEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteProductEntries(groupId);
	}

	public void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteProductEntry(productEntryId);
	}

	public void deleteProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scProductEntryLocalService.deleteProductEntry(productEntry);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getCompanyProductEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getCompanyProductEntries(companyId,
			start, end);
	}

	public int getCompanyProductEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getCompanyProductEntriesCount(companyId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntries(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntries(groupId, start,
			end, obc);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntries(groupId, userId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntries(groupId, userId,
			start, end, obc);
	}

	public int getProductEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntriesCount(groupId);
	}

	public int getProductEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntriesCount(groupId,
			userId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getProductEntry(productEntryId);
	}

	public java.lang.String getRepositoryXML(long groupId,
		java.lang.String baseImageURL, java.util.Date oldestDate,
		int maxNumOfVersions, java.util.Properties repoSettings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getRepositoryXML(groupId,
			baseImageURL, oldestDate, maxNumOfVersions, repoSettings);
	}

	public java.lang.String getRepositoryXML(long groupId,
		java.lang.String version, java.lang.String baseImageURL,
		java.util.Date oldestDate, int maxNumOfVersions,
		java.util.Properties repoSettings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.getRepositoryXML(groupId, version,
			baseImageURL, oldestDate, maxNumOfVersions, repoSettings);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntryLocalService.updateProductEntry(productEntryId,
			name, type, tags, shortDescription, longDescription, pageURL,
			author, repoGroupId, repoArtifactId, licenseIds, thumbnails,
			fullImages);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SCProductEntryLocalService getWrappedSCProductEntryLocalService() {
		return _scProductEntryLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSCProductEntryLocalService(
		SCProductEntryLocalService scProductEntryLocalService) {
		_scProductEntryLocalService = scProductEntryLocalService;
	}

	public SCProductEntryLocalService getWrappedService() {
		return _scProductEntryLocalService;
	}

	public void setWrappedService(
		SCProductEntryLocalService scProductEntryLocalService) {
		_scProductEntryLocalService = scProductEntryLocalService;
	}

	private SCProductEntryLocalService _scProductEntryLocalService;
}