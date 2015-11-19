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

package com.liferay.portlet.softwarecatalog.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SCProductEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductEntryLocalService
 * @generated
 */
@ProviderType
public class SCProductEntryLocalServiceWrapper
	implements SCProductEntryLocalService,
		ServiceWrapper<SCProductEntryLocalService> {
	public SCProductEntryLocalServiceWrapper(
		SCProductEntryLocalService scProductEntryLocalService) {
		_scProductEntryLocalService = scProductEntryLocalService;
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductEntryLocalService.addProductEntry(userId, name, type,
			tags, shortDescription, longDescription, pageURL, author,
			repoGroupId, repoArtifactId, licenseIds, thumbnails, fullImages,
			serviceContext);
	}

	@Override
	public void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scProductEntryLocalService.addProductEntryResources(productEntry,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scProductEntryLocalService.addProductEntryResources(productEntry,
			modelPermissions);
	}

	@Override
	public void addProductEntryResources(long productEntryId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scProductEntryLocalService.addProductEntryResources(productEntryId,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addProductEntryResources(long productEntryId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scProductEntryLocalService.addProductEntryResources(productEntryId,
			modelPermissions);
	}

	@Override
	public void addSCLicenseSCProductEntries(long licenseId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> SCProductEntries) {
		_scProductEntryLocalService.addSCLicenseSCProductEntries(licenseId,
			SCProductEntries);
	}

	@Override
	public void addSCLicenseSCProductEntries(long licenseId,
		long[] productEntryIds) {
		_scProductEntryLocalService.addSCLicenseSCProductEntries(licenseId,
			productEntryIds);
	}

	@Override
	public void addSCLicenseSCProductEntry(long licenseId, long productEntryId) {
		_scProductEntryLocalService.addSCLicenseSCProductEntry(licenseId,
			productEntryId);
	}

	@Override
	public void addSCLicenseSCProductEntry(long licenseId,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		_scProductEntryLocalService.addSCLicenseSCProductEntry(licenseId,
			scProductEntry);
	}

	/**
	* Adds the s c product entry to the database. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @return the s c product entry that was added
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry addSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		return _scProductEntryLocalService.addSCProductEntry(scProductEntry);
	}

	@Override
	public void clearSCLicenseSCProductEntries(long licenseId) {
		_scProductEntryLocalService.clearSCLicenseSCProductEntries(licenseId);
	}

	/**
	* Creates a new s c product entry with the primary key. Does not add the s c product entry to the database.
	*
	* @param productEntryId the primary key for the new s c product entry
	* @return the new s c product entry
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry createSCProductEntry(
		long productEntryId) {
		return _scProductEntryLocalService.createSCProductEntry(productEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteProductEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scProductEntryLocalService.deleteProductEntries(groupId);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry deleteProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductEntryLocalService.deleteProductEntry(productEntry);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry deleteProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductEntryLocalService.deleteProductEntry(productEntryId);
	}

	@Override
	public void deleteSCLicenseSCProductEntries(long licenseId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> SCProductEntries) {
		_scProductEntryLocalService.deleteSCLicenseSCProductEntries(licenseId,
			SCProductEntries);
	}

	@Override
	public void deleteSCLicenseSCProductEntries(long licenseId,
		long[] productEntryIds) {
		_scProductEntryLocalService.deleteSCLicenseSCProductEntries(licenseId,
			productEntryIds);
	}

	@Override
	public void deleteSCLicenseSCProductEntry(long licenseId,
		long productEntryId) {
		_scProductEntryLocalService.deleteSCLicenseSCProductEntry(licenseId,
			productEntryId);
	}

	@Override
	public void deleteSCLicenseSCProductEntry(long licenseId,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		_scProductEntryLocalService.deleteSCLicenseSCProductEntry(licenseId,
			scProductEntry);
	}

	/**
	* Deletes the s c product entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry that was removed
	* @throws PortalException if a s c product entry with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry deleteSCProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductEntryLocalService.deleteSCProductEntry(productEntryId);
	}

	/**
	* Deletes the s c product entry from the database. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @return the s c product entry that was removed
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry deleteSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		return _scProductEntryLocalService.deleteSCProductEntry(scProductEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _scProductEntryLocalService.dynamicQuery();
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
		return _scProductEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _scProductEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _scProductEntryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _scProductEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _scProductEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchSCProductEntry(
		long productEntryId) {
		return _scProductEntryLocalService.fetchSCProductEntry(productEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _scProductEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getCompanyProductEntries(
		long companyId, int start, int end) {
		return _scProductEntryLocalService.getCompanyProductEntries(companyId,
			start, end);
	}

	@Override
	public int getCompanyProductEntriesCount(long companyId) {
		return _scProductEntryLocalService.getCompanyProductEntriesCount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _scProductEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _scProductEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end) {
		return _scProductEntryLocalService.getProductEntries(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCProductEntry> obc) {
		return _scProductEntryLocalService.getProductEntries(groupId, start,
			end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end) {
		return _scProductEntryLocalService.getProductEntries(groupId, userId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCProductEntry> obc) {
		return _scProductEntryLocalService.getProductEntries(groupId, userId,
			start, end, obc);
	}

	@Override
	public int getProductEntriesCount(long groupId) {
		return _scProductEntryLocalService.getProductEntriesCount(groupId);
	}

	@Override
	public int getProductEntriesCount(long groupId, long userId) {
		return _scProductEntryLocalService.getProductEntriesCount(groupId,
			userId);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductEntryLocalService.getProductEntry(productEntryId);
	}

	@Override
	public java.lang.String getRepositoryXML(long groupId,
		java.lang.String baseImageURL, java.util.Date oldestDate,
		int maxNumOfVersions, java.util.Properties repoSettings) {
		return _scProductEntryLocalService.getRepositoryXML(groupId,
			baseImageURL, oldestDate, maxNumOfVersions, repoSettings);
	}

	@Override
	public java.lang.String getRepositoryXML(long groupId,
		java.lang.String version, java.lang.String baseImageURL,
		java.util.Date oldestDate, int maxNumOfVersions,
		java.util.Properties repoSettings) {
		return _scProductEntryLocalService.getRepositoryXML(groupId, version,
			baseImageURL, oldestDate, maxNumOfVersions, repoSettings);
	}

	/**
	* Returns the licenseIds of the s c licenses associated with the s c product entry.
	*
	* @param productEntryId the productEntryId of the s c product entry
	* @return long[] the licenseIds of s c licenses associated with the s c product entry
	*/
	@Override
	public long[] getSCLicensePrimaryKeys(long productEntryId) {
		return _scProductEntryLocalService.getSCLicensePrimaryKeys(productEntryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCLicenseSCProductEntries(
		long licenseId) {
		return _scProductEntryLocalService.getSCLicenseSCProductEntries(licenseId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCLicenseSCProductEntries(
		long licenseId, int start, int end) {
		return _scProductEntryLocalService.getSCLicenseSCProductEntries(licenseId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCLicenseSCProductEntries(
		long licenseId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCProductEntry> orderByComparator) {
		return _scProductEntryLocalService.getSCLicenseSCProductEntries(licenseId,
			start, end, orderByComparator);
	}

	@Override
	public int getSCLicenseSCProductEntriesCount(long licenseId) {
		return _scProductEntryLocalService.getSCLicenseSCProductEntriesCount(licenseId);
	}

	/**
	* Returns a range of all the s c product entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of s c product entries
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		int start, int end) {
		return _scProductEntryLocalService.getSCProductEntries(start, end);
	}

	/**
	* Returns the number of s c product entries.
	*
	* @return the number of s c product entries
	*/
	@Override
	public int getSCProductEntriesCount() {
		return _scProductEntryLocalService.getSCProductEntriesCount();
	}

	/**
	* Returns the s c product entry with the primary key.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry
	* @throws PortalException if a s c product entry with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getSCProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductEntryLocalService.getSCProductEntry(productEntryId);
	}

	@Override
	public boolean hasSCLicenseSCProductEntries(long licenseId) {
		return _scProductEntryLocalService.hasSCLicenseSCProductEntries(licenseId);
	}

	@Override
	public boolean hasSCLicenseSCProductEntry(long licenseId,
		long productEntryId) {
		return _scProductEntryLocalService.hasSCLicenseSCProductEntry(licenseId,
			productEntryId);
	}

	@Override
	public void setSCLicenseSCProductEntries(long licenseId,
		long[] productEntryIds) {
		_scProductEntryLocalService.setSCLicenseSCProductEntries(licenseId,
			productEntryIds);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductEntryLocalService.updateProductEntry(productEntryId,
			name, type, tags, shortDescription, longDescription, pageURL,
			author, repoGroupId, repoArtifactId, licenseIds, thumbnails,
			fullImages);
	}

	/**
	* Updates the s c product entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @return the s c product entry that was updated
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry updateSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		return _scProductEntryLocalService.updateSCProductEntry(scProductEntry);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SCProductEntryLocalService getWrappedSCProductEntryLocalService() {
		return _scProductEntryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSCProductEntryLocalService(
		SCProductEntryLocalService scProductEntryLocalService) {
		_scProductEntryLocalService = scProductEntryLocalService;
	}

	@Override
	public SCProductEntryLocalService getWrappedService() {
		return _scProductEntryLocalService;
	}

	@Override
	public void setWrappedService(
		SCProductEntryLocalService scProductEntryLocalService) {
		_scProductEntryLocalService = scProductEntryLocalService;
	}

	private SCProductEntryLocalService _scProductEntryLocalService;
}