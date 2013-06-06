/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * Provides a wrapper for {@link SCFrameworkVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionLocalService
 * @generated
 */
public class SCFrameworkVersionLocalServiceWrapper
	implements SCFrameworkVersionLocalService,
		ServiceWrapper<SCFrameworkVersionLocalService> {
	public SCFrameworkVersionLocalServiceWrapper(
		SCFrameworkVersionLocalService scFrameworkVersionLocalService) {
		_scFrameworkVersionLocalService = scFrameworkVersionLocalService;
	}

	/**
	* Adds the s c framework version to the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.addSCFrameworkVersion(scFrameworkVersion);
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

	/**
	* Deletes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version that was removed
	* @throws PortalException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.deleteSCFrameworkVersion(frameworkVersionId);
	}

	/**
	* Deletes the s c framework version from the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.deleteSCFrameworkVersion(scFrameworkVersion);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.fetchSCFrameworkVersion(frameworkVersionId);
	}

	/**
	* Returns the s c framework version with the primary key.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version
	* @throws PortalException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCFrameworkVersion(frameworkVersionId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCFrameworkVersions(start, end);
	}

	/**
	* Returns the number of s c framework versions.
	*
	* @return the number of s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getSCFrameworkVersionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCFrameworkVersionsCount();
	}

	/**
	* Updates the s c framework version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.updateSCFrameworkVersion(scFrameworkVersion);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addSCProductVersionSCFrameworkVersion(long productVersionId,
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addSCProductVersionSCFrameworkVersion(long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addSCProductVersionSCFrameworkVersion(productVersionId,
			scFrameworkVersion);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addSCProductVersionSCFrameworkVersions(long productVersionId,
		long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addSCProductVersionSCFrameworkVersions(long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addSCProductVersionSCFrameworkVersions(productVersionId,
			SCFrameworkVersions);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void clearSCProductVersionSCFrameworkVersions(long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.clearSCProductVersionSCFrameworkVersions(productVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteSCProductVersionSCFrameworkVersion(productVersionId,
			scFrameworkVersion);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId, long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteSCProductVersionSCFrameworkVersions(productVersionId,
			SCFrameworkVersions);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCProductVersionSCFrameworkVersions(productVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCProductVersionSCFrameworkVersions(productVersionId,
			start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCProductVersionSCFrameworkVersions(productVersionId,
			start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getSCProductVersionSCFrameworkVersionsCount(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getSCProductVersionSCFrameworkVersionsCount(productVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.hasSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasSCProductVersionSCFrameworkVersions(long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.hasSCProductVersionSCFrameworkVersions(productVersionId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setSCProductVersionSCFrameworkVersions(long productVersionId,
		long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.setSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _scFrameworkVersionLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_scFrameworkVersionLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String name, java.lang.String url,
		boolean active, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.addFrameworkVersion(userId,
			name, url, active, priority, serviceContext);
	}

	@Override
	public void addFrameworkVersionResources(long frameworkVersionId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersionId,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFrameworkVersionResources(long frameworkVersionId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersionId,
			groupPermissions, guestPermissions);
	}

	@Override
	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersion,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.addFrameworkVersionResources(frameworkVersion,
			groupPermissions, guestPermissions);
	}

	@Override
	public void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteFrameworkVersion(frameworkVersionId);
	}

	@Override
	public void deleteFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteFrameworkVersion(frameworkVersion);
	}

	@Override
	public void deleteFrameworkVersions(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionLocalService.deleteFrameworkVersions(groupId);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersion(frameworkVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersions(groupId,
			active);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersions(groupId,
			active, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersions(groupId,
			start, end);
	}

	@Override
	public int getFrameworkVersionsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersionsCount(groupId);
	}

	@Override
	public int getFrameworkVersionsCount(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getFrameworkVersionsCount(groupId,
			active);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getProductVersionFrameworkVersions(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.getProductVersionFrameworkVersions(productVersionId);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionLocalService.updateFrameworkVersion(frameworkVersionId,
			name, url, active, priority);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public SCFrameworkVersionLocalService getWrappedSCFrameworkVersionLocalService() {
		return _scFrameworkVersionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
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