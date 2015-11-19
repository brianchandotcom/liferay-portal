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
 * Provides a wrapper for {@link SCFrameworkVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionLocalService
 * @generated
 */
@ProviderType
public class SCFrameworkVersionLocalServiceWrapper
	implements SCFrameworkVersionLocalService,
		ServiceWrapper<SCFrameworkVersionLocalService> {
	public SCFrameworkVersionLocalServiceWrapper(
		SCFrameworkVersionLocalService scFrameworkVersionLocalService) {
		_scFrameworkVersionLocalService = scFrameworkVersionLocalService;
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String name, java.lang.String url,
		boolean active, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionLocalService.addFrameworkVersion(userId,
			name, url, active, priority, serviceContext);
	}

	@Override
	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersion,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersion,
			modelPermissions);
	}

	@Override
	public void addFrameworkVersionResources(long frameworkVersionId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersionId,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFrameworkVersionResources(long frameworkVersionId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersionId,
			modelPermissions);
	}

	/**
	* Adds the s c framework version to the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was added
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		return _scFrameworkVersionLocalService.addSCFrameworkVersion(scFrameworkVersion);
	}

	@Override
	public void addSCProductVersionSCFrameworkVersion(long productVersionId,
		long frameworkVersionId) {
		_scFrameworkVersionLocalService.addSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	@Override
	public void addSCProductVersionSCFrameworkVersion(long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		_scFrameworkVersionLocalService.addSCProductVersionSCFrameworkVersion(productVersionId,
			scFrameworkVersion);
	}

	@Override
	public void addSCProductVersionSCFrameworkVersions(long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions) {
		_scFrameworkVersionLocalService.addSCProductVersionSCFrameworkVersions(productVersionId,
			SCFrameworkVersions);
	}

	@Override
	public void addSCProductVersionSCFrameworkVersions(long productVersionId,
		long[] frameworkVersionIds) {
		_scFrameworkVersionLocalService.addSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	@Override
	public void clearSCProductVersionSCFrameworkVersions(long productVersionId) {
		_scFrameworkVersionLocalService.clearSCProductVersionSCFrameworkVersions(productVersionId);
	}

	/**
	* Creates a new s c framework version with the primary key. Does not add the s c framework version to the database.
	*
	* @param frameworkVersionId the primary key for the new s c framework version
	* @return the new s c framework version
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion createSCFrameworkVersion(
		long frameworkVersionId) {
		return _scFrameworkVersionLocalService.createSCFrameworkVersion(frameworkVersionId);
	}

	@Override
	public void deleteFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion) {
		_scFrameworkVersionLocalService.deleteFrameworkVersion(frameworkVersion);
	}

	@Override
	public void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scFrameworkVersionLocalService.deleteFrameworkVersion(frameworkVersionId);
	}

	@Override
	public void deleteFrameworkVersions(long groupId) {
		_scFrameworkVersionLocalService.deleteFrameworkVersions(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version that was removed
	* @throws PortalException if a s c framework version with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionLocalService.deleteSCFrameworkVersion(frameworkVersionId);
	}

	/**
	* Deletes the s c framework version from the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was removed
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		return _scFrameworkVersionLocalService.deleteSCFrameworkVersion(scFrameworkVersion);
	}

	@Override
	public void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId) {
		_scFrameworkVersionLocalService.deleteSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	@Override
	public void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		_scFrameworkVersionLocalService.deleteSCProductVersionSCFrameworkVersion(productVersionId,
			scFrameworkVersion);
	}

	@Override
	public void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions) {
		_scFrameworkVersionLocalService.deleteSCProductVersionSCFrameworkVersions(productVersionId,
			SCFrameworkVersions);
	}

	@Override
	public void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId, long[] frameworkVersionIds) {
		_scFrameworkVersionLocalService.deleteSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _scFrameworkVersionLocalService.dynamicQuery();
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
		return _scFrameworkVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _scFrameworkVersionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _scFrameworkVersionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _scFrameworkVersionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _scFrameworkVersionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchSCFrameworkVersion(
		long frameworkVersionId) {
		return _scFrameworkVersionLocalService.fetchSCFrameworkVersion(frameworkVersionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _scFrameworkVersionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionLocalService.getFrameworkVersion(frameworkVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active) {
		return _scFrameworkVersionLocalService.getFrameworkVersions(groupId,
			active);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end) {
		return _scFrameworkVersionLocalService.getFrameworkVersions(groupId,
			active, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, int start, int end) {
		return _scFrameworkVersionLocalService.getFrameworkVersions(groupId,
			start, end);
	}

	@Override
	public int getFrameworkVersionsCount(long groupId) {
		return _scFrameworkVersionLocalService.getFrameworkVersionsCount(groupId);
	}

	@Override
	public int getFrameworkVersionsCount(long groupId, boolean active) {
		return _scFrameworkVersionLocalService.getFrameworkVersionsCount(groupId,
			active);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _scFrameworkVersionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _scFrameworkVersionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getProductVersionFrameworkVersions(
		long productVersionId) {
		return _scFrameworkVersionLocalService.getProductVersionFrameworkVersions(productVersionId);
	}

	/**
	* Returns the s c framework version with the primary key.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version
	* @throws PortalException if a s c framework version with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionLocalService.getSCFrameworkVersion(frameworkVersionId);
	}

	/**
	* Returns a range of all the s c framework versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of s c framework versions
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		int start, int end) {
		return _scFrameworkVersionLocalService.getSCFrameworkVersions(start, end);
	}

	/**
	* Returns the number of s c framework versions.
	*
	* @return the number of s c framework versions
	*/
	@Override
	public int getSCFrameworkVersionsCount() {
		return _scFrameworkVersionLocalService.getSCFrameworkVersionsCount();
	}

	/**
	* Returns the productVersionIds of the s c product versions associated with the s c framework version.
	*
	* @param frameworkVersionId the frameworkVersionId of the s c framework version
	* @return long[] the productVersionIds of s c product versions associated with the s c framework version
	*/
	@Override
	public long[] getSCProductVersionPrimaryKeys(long frameworkVersionId) {
		return _scFrameworkVersionLocalService.getSCProductVersionPrimaryKeys(frameworkVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId) {
		return _scFrameworkVersionLocalService.getSCProductVersionSCFrameworkVersions(productVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end) {
		return _scFrameworkVersionLocalService.getSCProductVersionSCFrameworkVersions(productVersionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> orderByComparator) {
		return _scFrameworkVersionLocalService.getSCProductVersionSCFrameworkVersions(productVersionId,
			start, end, orderByComparator);
	}

	@Override
	public int getSCProductVersionSCFrameworkVersionsCount(
		long productVersionId) {
		return _scFrameworkVersionLocalService.getSCProductVersionSCFrameworkVersionsCount(productVersionId);
	}

	@Override
	public boolean hasSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId) {
		return _scFrameworkVersionLocalService.hasSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	@Override
	public boolean hasSCProductVersionSCFrameworkVersions(long productVersionId) {
		return _scFrameworkVersionLocalService.hasSCProductVersionSCFrameworkVersions(productVersionId);
	}

	@Override
	public void setSCProductVersionSCFrameworkVersions(long productVersionId,
		long[] frameworkVersionIds) {
		_scFrameworkVersionLocalService.setSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionLocalService.updateFrameworkVersion(frameworkVersionId,
			name, url, active, priority);
	}

	/**
	* Updates the s c framework version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was updated
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		return _scFrameworkVersionLocalService.updateSCFrameworkVersion(scFrameworkVersion);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SCFrameworkVersionLocalService getWrappedSCFrameworkVersionLocalService() {
		return _scFrameworkVersionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSCFrameworkVersionLocalService(
		SCFrameworkVersionLocalService scFrameworkVersionLocalService) {
		_scFrameworkVersionLocalService = scFrameworkVersionLocalService;
	}

	@Override
	public SCFrameworkVersionLocalService getWrappedService() {
		return _scFrameworkVersionLocalService;
	}

	@Override
	public void setWrappedService(
		SCFrameworkVersionLocalService scFrameworkVersionLocalService) {
		_scFrameworkVersionLocalService = scFrameworkVersionLocalService;
	}

	private SCFrameworkVersionLocalService _scFrameworkVersionLocalService;
}