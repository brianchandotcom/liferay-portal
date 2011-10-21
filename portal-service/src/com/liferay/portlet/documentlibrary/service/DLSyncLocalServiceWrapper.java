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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link DLSyncLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLSyncLocalService
 * @generated
 */
public class DLSyncLocalServiceWrapper implements DLSyncLocalService,
	ServiceWrapper<DLSyncLocalService> {
	public DLSyncLocalServiceWrapper(DLSyncLocalService dlSyncLocalService) {
		_dlSyncLocalService = dlSyncLocalService;
	}

	/**
	* Adds the d l sync to the database. Also notifies the appropriate model listeners.
	*
	* @param dlSync the d l sync
	* @return the d l sync that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSync addDLSync(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlSyncLocalService.addDLSync(dlSync);
	}

	/**
	* Creates a new d l sync with the primary key. Does not add the d l sync to the database.
	*
	* @param syncId the primary key for the new d l sync
	* @return the new d l sync
	*/
	public com.liferay.portlet.documentlibrary.model.DLSync createDLSync(
		long syncId) {
		return _dlSyncLocalService.createDLSync(syncId);
	}

	/**
	* Deletes the d l sync with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param syncId the primary key of the d l sync
	* @throws PortalException if a d l sync with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLSync(long syncId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlSyncLocalService.deleteDLSync(syncId);
	}

	/**
	* Deletes the d l sync from the database. Also notifies the appropriate model listeners.
	*
	* @param dlSync the d l sync
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLSync(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlSyncLocalService.deleteDLSync(dlSync);
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
		return _dlSyncLocalService.dynamicQuery(dynamicQuery);
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
		return _dlSyncLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _dlSyncLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _dlSyncLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the d l sync with the primary key.
	*
	* @param syncId the primary key of the d l sync
	* @return the d l sync
	* @throws PortalException if a d l sync with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSync getDLSync(
		long syncId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlSyncLocalService.getDLSync(syncId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlSyncLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the d l syncs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l syncs
	* @param end the upper bound of the range of d l syncs (not inclusive)
	* @return the range of d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLSync> getDLSyncs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlSyncLocalService.getDLSyncs(start, end);
	}

	/**
	* Returns the number of d l syncs.
	*
	* @return the number of d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public int getDLSyncsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlSyncLocalService.getDLSyncsCount();
	}

	/**
	* Updates the d l sync in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlSync the d l sync
	* @return the d l sync that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSync updateDLSync(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlSyncLocalService.updateDLSync(dlSync);
	}

	/**
	* Updates the d l sync in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlSync the d l sync
	* @param merge whether to merge the d l sync with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d l sync that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLSync updateDLSync(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlSyncLocalService.updateDLSync(dlSync, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _dlSyncLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlSyncLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.documentlibrary.model.DLSync addSync(
		long fileId, long companyId, long repositoryId, long parentFolderId,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlSyncLocalService.addSync(fileId, companyId, repositoryId,
			parentFolderId, type);
	}

	public com.liferay.portlet.documentlibrary.model.DLSync updateSync(
		long fileId, long parentFolderId, java.lang.String event)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlSyncLocalService.updateSync(fileId, parentFolderId, event);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public DLSyncLocalService getWrappedDLSyncLocalService() {
		return _dlSyncLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedDLSyncLocalService(
		DLSyncLocalService dlSyncLocalService) {
		_dlSyncLocalService = dlSyncLocalService;
	}

	public DLSyncLocalService getWrappedService() {
		return _dlSyncLocalService;
	}

	public void setWrappedService(DLSyncLocalService dlSyncLocalService) {
		_dlSyncLocalService = dlSyncLocalService;
	}

	private DLSyncLocalService _dlSyncLocalService;
}