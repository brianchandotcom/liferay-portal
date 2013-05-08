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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFileVersionLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileVersionLocalService
 * @generated
 */
public class DLFileVersionLocalServiceWrapper
	implements DLFileVersionLocalService,
		ServiceWrapper<DLFileVersionLocalService> {
	public DLFileVersionLocalServiceWrapper(
		DLFileVersionLocalService dlFileVersionLocalService) {
		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	/**
	* Adds the document library file version to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the document library file version
	* @return the document library file version that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion addDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.addDLFileVersion(dlFileVersion);
	}

	/**
	* Creates a new document library file version with the primary key. Does not add the document library file version to the database.
	*
	* @param fileVersionId the primary key for the new document library file version
	* @return the new document library file version
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion createDLFileVersion(
		long fileVersionId) {
		return _dlFileVersionLocalService.createDLFileVersion(fileVersionId);
	}

	/**
	* Deletes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version that was removed
	* @throws PortalException if a document library file version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion deleteDLFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.deleteDLFileVersion(fileVersionId);
	}

	/**
	* Deletes the document library file version from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the document library file version
	* @return the document library file version that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion deleteDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.deleteDLFileVersion(dlFileVersion);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlFileVersionLocalService.dynamicQuery();
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
		return _dlFileVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _dlFileVersionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _dlFileVersionLocalService.dynamicQuery(dynamicQuery, start,
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
		return _dlFileVersionLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion fetchDLFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);
	}

	/**
	* Returns the document library file version with the primary key.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version
	* @throws PortalException if a document library file version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion getDLFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getDLFileVersion(fileVersionId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the document library file version matching the UUID and group.
	*
	* @param uuid the document library file version's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file version
	* @throws PortalException if a matching document library file version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion getDLFileVersionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getDLFileVersionByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the document library file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of document library file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getDLFileVersions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getDLFileVersions(start, end);
	}

	/**
	* Returns the number of document library file versions.
	*
	* @return the number of document library file versions
	* @throws SystemException if a system exception occurred
	*/
	public int getDLFileVersionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getDLFileVersionsCount();
	}

	/**
	* Updates the document library file version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the document library file version
	* @return the document library file version that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion updateDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.updateDLFileVersion(dlFileVersion);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _dlFileVersionLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlFileVersionLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getFileVersion(fileVersionId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getFileVersion(fileEntryId, version);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getFileVersionByUuidAndGroupId(uuid,
			groupId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		long fileEntryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getFileVersions(fileEntryId, status);
	}

	public int getFileVersionsCount(long fileEntryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getFileVersionsCount(fileEntryId,
			status);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		long fileEntryId, boolean excludeWorkingCopy)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getLatestFileVersion(fileEntryId,
			excludeWorkingCopy);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getLatestFileVersion(userId,
			fileEntryId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public DLFileVersionLocalService getWrappedDLFileVersionLocalService() {
		return _dlFileVersionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedDLFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {
		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	public DLFileVersionLocalService getWrappedService() {
		return _dlFileVersionLocalService;
	}

	public void setWrappedService(
		DLFileVersionLocalService dlFileVersionLocalService) {
		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	private DLFileVersionLocalService _dlFileVersionLocalService;
}