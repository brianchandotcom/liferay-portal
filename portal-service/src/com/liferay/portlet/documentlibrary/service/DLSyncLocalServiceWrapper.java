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

/**
 * <p>
 * This class is a wrapper for {@link DLSyncLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLSyncLocalService
 * @generated
 */
public class DLSyncLocalServiceWrapper implements DLSyncLocalService {
	public DLSyncLocalServiceWrapper(DLSyncLocalService dlSyncLocalService) {
		_dlSyncLocalService = dlSyncLocalService;
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

	public DLSyncLocalService getWrappedDLSyncLocalService() {
		return _dlSyncLocalService;
	}

	public void setWrappedDLSyncLocalService(
		DLSyncLocalService dlSyncLocalService) {
		_dlSyncLocalService = dlSyncLocalService;
	}

	private DLSyncLocalService _dlSyncLocalService;
}