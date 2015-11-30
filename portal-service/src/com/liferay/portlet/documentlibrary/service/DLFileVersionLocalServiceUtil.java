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

package com.liferay.portlet.documentlibrary.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for DLFileVersion. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLFileVersionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileVersionLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileVersionLocalServiceImpl
 * @generated
 */
@ProviderType
public class DLFileVersionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileVersionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the document library file version to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the document library file version
	* @return the document library file version that was added
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion addDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion) {
		return getService().addDLFileVersion(dlFileVersion);
	}

	/**
	* Creates a new document library file version with the primary key. Does not add the document library file version to the database.
	*
	* @param fileVersionId the primary key for the new document library file version
	* @return the new document library file version
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion createDLFileVersion(
		long fileVersionId) {
		return getService().createDLFileVersion(fileVersionId);
	}

	/**
	* Deletes the document library file version from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the document library file version
	* @return the document library file version that was removed
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion deleteDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion) {
		return getService().deleteDLFileVersion(dlFileVersion);
	}

	/**
	* Deletes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version that was removed
	* @throws PortalException if a document library file version with the primary key could not be found
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion deleteDLFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDLFileVersion(fileVersionId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion fetchDLFileVersion(
		long fileVersionId) {
		return getService().fetchDLFileVersion(fileVersionId);
	}

	/**
	* Returns the document library file version matching the UUID and group.
	*
	* @param uuid the document library file version's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion fetchDLFileVersionByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchDLFileVersionByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion fetchLatestFileVersion(
		long fileEntryId, boolean excludeWorkingCopy) {
		return getService()
				   .fetchLatestFileVersion(fileEntryId, excludeWorkingCopy);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the document library file version with the primary key.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version
	* @throws PortalException if a document library file version with the primary key could not be found
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getDLFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDLFileVersion(fileVersionId);
	}

	/**
	* Returns the document library file version matching the UUID and group.
	*
	* @param uuid the document library file version's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file version
	* @throws PortalException if a matching document library file version could not be found
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getDLFileVersionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDLFileVersionByUuidAndGroupId(uuid, groupId);
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
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getDLFileVersions(
		int start, int end) {
		return getService().getDLFileVersions(start, end);
	}

	/**
	* Returns all the document library file versions matching the UUID and company.
	*
	* @param uuid the UUID of the document library file versions
	* @param companyId the primary key of the company
	* @return the matching document library file versions, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getDLFileVersionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getDLFileVersionsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of document library file versions matching the UUID and company.
	*
	* @param uuid the UUID of the document library file versions
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching document library file versions, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getDLFileVersionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileVersion> orderByComparator) {
		return getService()
				   .getDLFileVersionsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of document library file versions.
	*
	* @return the number of document library file versions
	*/
	public static int getDLFileVersionsCount() {
		return getService().getDLFileVersionsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileVersion(fileEntryId, version);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFileVersion(fileVersionId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersionByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().getFileVersionByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		long fileEntryId, int status) {
		return getService().getFileVersions(fileEntryId, status);
	}

	public static int getFileVersionsCount(long fileEntryId, int status) {
		return getService().getFileVersionsCount(fileEntryId, status);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		long fileEntryId, boolean excludeWorkingCopy)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestFileVersion(fileEntryId, excludeWorkingCopy);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		long userId, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestFileVersion(userId, fileEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void rebuildTree(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().rebuildTree(companyId);
	}

	public static void setTreePaths(long folderId, java.lang.String treePath)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().setTreePaths(folderId, treePath);
	}

	/**
	* Updates the document library file version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the document library file version
	* @return the document library file version that was updated
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion updateDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion) {
		return getService().updateDLFileVersion(dlFileVersion);
	}

	public static DLFileVersionLocalService getService() {
		if (_service == null) {
			_service = (DLFileVersionLocalService)PortalBeanLocatorUtil.locate(DLFileVersionLocalService.class.getName());

			ReferenceRegistry.registerReference(DLFileVersionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DLFileVersionLocalService service) {
	}

	private static DLFileVersionLocalService _service;
}