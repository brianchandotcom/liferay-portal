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
 * The utility for the d l sync remote service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLSyncServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncService
 * @see com.liferay.portlet.documentlibrary.service.base.DLSyncServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLSyncServiceImpl
 * @generated
 */
public class DLSyncServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLSyncServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

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

	public static com.liferay.portlet.documentlibrary.model.DLSyncUpdate getDLSyncUpdate(
		long companyId, long repositoryId, long lastAccessDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getDLSyncUpdate(companyId, repositoryId, lastAccessDate);
	}

	public static java.io.InputStream getFileDeltaAsStream(long fileEntryId,
		java.lang.String sourceVersion, java.lang.String destinationVersion)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileDeltaAsStream(fileEntryId, sourceVersion,
			destinationVersion);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry updateFileEntry(
		long fileEntryId, java.lang.String sourceFileName,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String changeLog,
		boolean majorVersion, java.io.InputStream deltaInputStream, long size,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileEntry(fileEntryId, sourceFileName, mimeType,
			title, description, changeLog, majorVersion, deltaInputStream,
			size, serviceContext);
	}

	public static DLSyncService getService() {
		if (_service == null) {
			_service = (DLSyncService)PortalBeanLocatorUtil.locate(DLSyncService.class.getName());

			ReferenceRegistry.registerReference(DLSyncServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(DLSyncService service) {
	}

	private static DLSyncService _service;
}