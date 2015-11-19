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
 * Provides a wrapper for {@link SCProductVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductVersionLocalService
 * @generated
 */
@ProviderType
public class SCProductVersionLocalServiceWrapper
	implements SCProductVersionLocalService,
		ServiceWrapper<SCProductVersionLocalService> {
	public SCProductVersionLocalServiceWrapper(
		SCProductVersionLocalService scProductVersionLocalService) {
		_scProductVersionLocalService = scProductVersionLocalService;
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		long userId, long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionLocalService.addProductVersion(userId,
			productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, testDirectDownloadURL, repoStoreArtifact,
			frameworkVersionIds, serviceContext);
	}

	@Override
	public void addSCFrameworkVersionSCProductVersion(long frameworkVersionId,
		long productVersionId) {
		_scProductVersionLocalService.addSCFrameworkVersionSCProductVersion(frameworkVersionId,
			productVersionId);
	}

	@Override
	public void addSCFrameworkVersionSCProductVersion(long frameworkVersionId,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		_scProductVersionLocalService.addSCFrameworkVersionSCProductVersion(frameworkVersionId,
			scProductVersion);
	}

	@Override
	public void addSCFrameworkVersionSCProductVersions(
		long frameworkVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> SCProductVersions) {
		_scProductVersionLocalService.addSCFrameworkVersionSCProductVersions(frameworkVersionId,
			SCProductVersions);
	}

	@Override
	public void addSCFrameworkVersionSCProductVersions(
		long frameworkVersionId, long[] productVersionIds) {
		_scProductVersionLocalService.addSCFrameworkVersionSCProductVersions(frameworkVersionId,
			productVersionIds);
	}

	/**
	* Adds the s c product version to the database. Also notifies the appropriate model listeners.
	*
	* @param scProductVersion the s c product version
	* @return the s c product version that was added
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion addSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		return _scProductVersionLocalService.addSCProductVersion(scProductVersion);
	}

	@Override
	public void clearSCFrameworkVersionSCProductVersions(
		long frameworkVersionId) {
		_scProductVersionLocalService.clearSCFrameworkVersionSCProductVersions(frameworkVersionId);
	}

	/**
	* Creates a new s c product version with the primary key. Does not add the s c product version to the database.
	*
	* @param productVersionId the primary key for the new s c product version
	* @return the new s c product version
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion createSCProductVersion(
		long productVersionId) {
		return _scProductVersionLocalService.createSCProductVersion(productVersionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion productVersion) {
		_scProductVersionLocalService.deleteProductVersion(productVersion);
	}

	@Override
	public void deleteProductVersion(long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scProductVersionLocalService.deleteProductVersion(productVersionId);
	}

	@Override
	public void deleteProductVersions(long productEntryId) {
		_scProductVersionLocalService.deleteProductVersions(productEntryId);
	}

	@Override
	public void deleteSCFrameworkVersionSCProductVersion(
		long frameworkVersionId, long productVersionId) {
		_scProductVersionLocalService.deleteSCFrameworkVersionSCProductVersion(frameworkVersionId,
			productVersionId);
	}

	@Override
	public void deleteSCFrameworkVersionSCProductVersion(
		long frameworkVersionId,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		_scProductVersionLocalService.deleteSCFrameworkVersionSCProductVersion(frameworkVersionId,
			scProductVersion);
	}

	@Override
	public void deleteSCFrameworkVersionSCProductVersions(
		long frameworkVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> SCProductVersions) {
		_scProductVersionLocalService.deleteSCFrameworkVersionSCProductVersions(frameworkVersionId,
			SCProductVersions);
	}

	@Override
	public void deleteSCFrameworkVersionSCProductVersions(
		long frameworkVersionId, long[] productVersionIds) {
		_scProductVersionLocalService.deleteSCFrameworkVersionSCProductVersions(frameworkVersionId,
			productVersionIds);
	}

	/**
	* Deletes the s c product version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version that was removed
	* @throws PortalException if a s c product version with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion deleteSCProductVersion(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionLocalService.deleteSCProductVersion(productVersionId);
	}

	/**
	* Deletes the s c product version from the database. Also notifies the appropriate model listeners.
	*
	* @param scProductVersion the s c product version
	* @return the s c product version that was removed
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion deleteSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		return _scProductVersionLocalService.deleteSCProductVersion(scProductVersion);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _scProductVersionLocalService.dynamicQuery();
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
		return _scProductVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _scProductVersionLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _scProductVersionLocalService.dynamicQuery(dynamicQuery, start,
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
		return _scProductVersionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _scProductVersionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion fetchSCProductVersion(
		long productVersionId) {
		return _scProductVersionLocalService.fetchSCProductVersion(productVersionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _scProductVersionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _scProductVersionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _scProductVersionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getProductVersion(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionLocalService.getProductVersion(productVersionId);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getProductVersionByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionLocalService.getProductVersionByDirectDownloadURL(directDownloadURL);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getProductVersions(
		long productEntryId, int start, int end) {
		return _scProductVersionLocalService.getProductVersions(productEntryId,
			start, end);
	}

	@Override
	public int getProductVersionsCount(long productEntryId) {
		return _scProductVersionLocalService.getProductVersionsCount(productEntryId);
	}

	/**
	* Returns the frameworkVersionIds of the s c framework versions associated with the s c product version.
	*
	* @param productVersionId the productVersionId of the s c product version
	* @return long[] the frameworkVersionIds of s c framework versions associated with the s c product version
	*/
	@Override
	public long[] getSCFrameworkVersionPrimaryKeys(long productVersionId) {
		return _scProductVersionLocalService.getSCFrameworkVersionPrimaryKeys(productVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCFrameworkVersionSCProductVersions(
		long frameworkVersionId) {
		return _scProductVersionLocalService.getSCFrameworkVersionSCProductVersions(frameworkVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCFrameworkVersionSCProductVersions(
		long frameworkVersionId, int start, int end) {
		return _scProductVersionLocalService.getSCFrameworkVersionSCProductVersions(frameworkVersionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCFrameworkVersionSCProductVersions(
		long frameworkVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCProductVersion> orderByComparator) {
		return _scProductVersionLocalService.getSCFrameworkVersionSCProductVersions(frameworkVersionId,
			start, end, orderByComparator);
	}

	@Override
	public int getSCFrameworkVersionSCProductVersionsCount(
		long frameworkVersionId) {
		return _scProductVersionLocalService.getSCFrameworkVersionSCProductVersionsCount(frameworkVersionId);
	}

	/**
	* Returns the s c product version with the primary key.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version
	* @throws PortalException if a s c product version with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getSCProductVersion(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionLocalService.getSCProductVersion(productVersionId);
	}

	/**
	* Returns a range of all the s c product versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of s c product versions
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		int start, int end) {
		return _scProductVersionLocalService.getSCProductVersions(start, end);
	}

	/**
	* Returns the number of s c product versions.
	*
	* @return the number of s c product versions
	*/
	@Override
	public int getSCProductVersionsCount() {
		return _scProductVersionLocalService.getSCProductVersionsCount();
	}

	@Override
	public boolean hasSCFrameworkVersionSCProductVersion(
		long frameworkVersionId, long productVersionId) {
		return _scProductVersionLocalService.hasSCFrameworkVersionSCProductVersion(frameworkVersionId,
			productVersionId);
	}

	@Override
	public boolean hasSCFrameworkVersionSCProductVersions(
		long frameworkVersionId) {
		return _scProductVersionLocalService.hasSCFrameworkVersionSCProductVersions(frameworkVersionId);
	}

	@Override
	public void setSCFrameworkVersionSCProductVersions(
		long frameworkVersionId, long[] productVersionIds) {
		_scProductVersionLocalService.setSCFrameworkVersionSCProductVersions(frameworkVersionId,
			productVersionIds);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionLocalService.updateProductVersion(productVersionId,
			version, changeLog, downloadPageURL, directDownloadURL,
			testDirectDownloadURL, repoStoreArtifact, frameworkVersionIds);
	}

	/**
	* Updates the s c product version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scProductVersion the s c product version
	* @return the s c product version that was updated
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion updateSCProductVersion(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		return _scProductVersionLocalService.updateSCProductVersion(scProductVersion);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SCProductVersionLocalService getWrappedSCProductVersionLocalService() {
		return _scProductVersionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSCProductVersionLocalService(
		SCProductVersionLocalService scProductVersionLocalService) {
		_scProductVersionLocalService = scProductVersionLocalService;
	}

	@Override
	public SCProductVersionLocalService getWrappedService() {
		return _scProductVersionLocalService;
	}

	@Override
	public void setWrappedService(
		SCProductVersionLocalService scProductVersionLocalService) {
		_scProductVersionLocalService = scProductVersionLocalService;
	}

	private SCProductVersionLocalService _scProductVersionLocalService;
}