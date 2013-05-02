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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the document library file entry metadata local service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryMetadataLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileEntryMetadataLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileEntryMetadataLocalServiceImpl
 * @generated
 */
public class DLFileEntryMetadataLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryMetadataLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the document library file entry metadata to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	* @return the document library file entry metadata that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata addDLFileEntryMetadata(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDLFileEntryMetadata(dlFileEntryMetadata);
	}

	/**
	* Creates a new document library file entry metadata with the primary key. Does not add the document library file entry metadata to the database.
	*
	* @param fileEntryMetadataId the primary key for the new document library file entry metadata
	* @return the new document library file entry metadata
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata createDLFileEntryMetadata(
		long fileEntryMetadataId) {
		return getService().createDLFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	* Deletes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata that was removed
	* @throws PortalException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata deleteDLFileEntryMetadata(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	* Deletes the document library file entry metadata from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	* @return the document library file entry metadata that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata deleteDLFileEntryMetadata(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLFileEntryMetadata(dlFileEntryMetadata);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchDLFileEntryMetadata(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDLFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	* Returns the document library file entry metadata with the primary key.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata
	* @throws PortalException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata getDLFileEntryMetadata(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileEntryMetadata(fileEntryMetadataId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the document library file entry metadatas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> getDLFileEntryMetadatas(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileEntryMetadatas(start, end);
	}

	/**
	* Returns the number of document library file entry metadatas.
	*
	* @return the number of document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static int getDLFileEntryMetadatasCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileEntryMetadatasCount();
	}

	/**
	* Updates the document library file entry metadata in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	* @return the document library file entry metadata that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata updateDLFileEntryMetadata(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLFileEntryMetadata(dlFileEntryMetadata);
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

	public static void deleteFileEntryMetadata(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntryMetadata(fileEntryId);
	}

	public static void deleteFileVersionFileEntryMetadata(long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileVersionFileEntryMetadata(fileVersionId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchFileEntryMetadata(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchFileEntryMetadata(fileEntryMetadataId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchFileEntryMetadata(
		long ddmStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchFileEntryMetadata(ddmStructureId, fileVersionId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata getFileEntryMetadata(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntryMetadata(fileEntryMetadataId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata getFileEntryMetadata(
		long ddmStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntryMetadata(ddmStructureId, fileVersionId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFileVersionFileEntryMetadatasCount(long)}
	*/
	public static long getFileEntryMetadataCount(long fileEntryId,
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileEntryMetadataCount(fileEntryId, fileVersionId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> getFileVersionFileEntryMetadatas(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileVersionFileEntryMetadatas(fileVersionId);
	}

	public static long getFileVersionFileEntryMetadatasCount(long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileVersionFileEntryMetadatasCount(fileVersionId);
	}

	public static void updateFileEntryMetadata(long companyId,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures,
		long fileEntryTypeId, long fileEntryId, long fileVersionId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.storage.Fields> fieldsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateFileEntryMetadata(companyId, ddmStructures, fileEntryTypeId,
			fileEntryId, fileVersionId, fieldsMap, serviceContext);
	}

	public static void updateFileEntryMetadata(long fileEntryTypeId,
		long fileEntryId, long fileVersionId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.storage.Fields> fieldsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateFileEntryMetadata(fileEntryTypeId, fileEntryId,
			fileVersionId, fieldsMap, serviceContext);
	}

	public static DLFileEntryMetadataLocalService getService() {
		if (_service == null) {
			_service = (DLFileEntryMetadataLocalService)PortalBeanLocatorUtil.locate(DLFileEntryMetadataLocalService.class.getName());

			ReferenceRegistry.registerReference(DLFileEntryMetadataLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(DLFileEntryMetadataLocalService service) {
	}

	private static DLFileEntryMetadataLocalService _service;
}