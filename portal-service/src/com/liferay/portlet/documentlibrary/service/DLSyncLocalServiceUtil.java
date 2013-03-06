/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d l sync local service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLSyncLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLSyncLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLSyncLocalServiceImpl
 * @generated
 */
public class DLSyncLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLSyncLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d l sync to the database. Also notifies the appropriate model listeners.
	*
	* @param dlSync the d l sync
	* @return the d l sync that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync addDLSync(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDLSync(dlSync);
	}

	/**
	* Creates a new d l sync with the primary key. Does not add the d l sync to the database.
	*
	* @param syncId the primary key for the new d l sync
	* @return the new d l sync
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync createDLSync(
		long syncId) {
		return getService().createDLSync(syncId);
	}

	/**
	* Deletes the d l sync with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param syncId the primary key of the d l sync
	* @return the d l sync that was removed
	* @throws PortalException if a d l sync with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync deleteDLSync(
		long syncId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLSync(syncId);
	}

	/**
	* Deletes the d l sync from the database. Also notifies the appropriate model listeners.
	*
	* @param dlSync the d l sync
	* @return the d l sync that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync deleteDLSync(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLSync(dlSync);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.documentlibrary.model.DLSync fetchDLSync(
		long syncId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDLSync(syncId);
	}

	/**
	* Returns the d l sync with the primary key.
	*
	* @param syncId the primary key of the d l sync
	* @return the d l sync
	* @throws PortalException if a d l sync with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync getDLSync(
		long syncId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLSync(syncId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the d l syncs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d l syncs
	* @param end the upper bound of the range of d l syncs (not inclusive)
	* @return the range of d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLSync> getDLSyncs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLSyncs(start, end);
	}

	/**
	* Returns the number of d l syncs.
	*
	* @return the number of d l syncs
	* @throws SystemException if a system exception occurred
	*/
	public static int getDLSyncsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLSyncsCount();
	}

	/**
	* Updates the d l sync in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlSync the d l sync
	* @return the d l sync that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync updateDLSync(
		com.liferay.portlet.documentlibrary.model.DLSync dlSync)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLSync(dlSync);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addSync(long, String, long,
	long, long, String, String, String, String)}
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync addSync(
		long fileId, java.lang.String fileUuid, long companyId,
		long repositoryId, long parentFolderId, java.lang.String name,
		java.lang.String type, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addSync(fileId, fileUuid, companyId, repositoryId,
			parentFolderId, name, type, version);
	}

	public static com.liferay.portlet.documentlibrary.model.DLSync addSync(
		long fileId, java.lang.String fileUuid, long companyId,
		long repositoryId, long parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addSync(fileId, fileUuid, companyId, repositoryId,
			parentFolderId, name, description, type, version);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateSync(long, long,
	String, String, String, String)}
	*/
	public static com.liferay.portlet.documentlibrary.model.DLSync updateSync(
		long fileId, long parentFolderId, java.lang.String name,
		java.lang.String event, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateSync(fileId, parentFolderId, name, event, version);
	}

	public static com.liferay.portlet.documentlibrary.model.DLSync updateSync(
		long fileId, long parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String event,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateSync(fileId, parentFolderId, name, description,
			event, version);
	}

	public static DLSyncLocalService getService() {
		if (_service == null) {
			_service = (DLSyncLocalService)PortalBeanLocatorUtil.locate(DLSyncLocalService.class.getName());

			ReferenceRegistry.registerReference(DLSyncLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(DLSyncLocalService service) {
	}

	private static DLSyncLocalService _service;
}