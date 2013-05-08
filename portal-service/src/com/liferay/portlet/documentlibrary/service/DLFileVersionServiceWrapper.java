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
 * Provides a wrapper for {@link DLFileVersionService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileVersionService
 * @generated
 */
public class DLFileVersionServiceWrapper implements DLFileVersionService,
	ServiceWrapper<DLFileVersionService> {
	public DLFileVersionServiceWrapper(
		DLFileVersionService dlFileVersionService) {
		_dlFileVersionService = dlFileVersionService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _dlFileVersionService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlFileVersionService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionService.getFileVersion(fileVersionId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionService.getLatestFileVersion(fileEntryId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public DLFileVersionService getWrappedDLFileVersionService() {
		return _dlFileVersionService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedDLFileVersionService(
		DLFileVersionService dlFileVersionService) {
		_dlFileVersionService = dlFileVersionService;
	}

	public DLFileVersionService getWrappedService() {
		return _dlFileVersionService;
	}

	public void setWrappedService(DLFileVersionService dlFileVersionService) {
		_dlFileVersionService = dlFileVersionService;
	}

	private DLFileVersionService _dlFileVersionService;
}