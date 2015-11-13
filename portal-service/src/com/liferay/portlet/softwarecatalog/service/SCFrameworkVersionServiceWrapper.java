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
 * Provides a wrapper for {@link SCFrameworkVersionService}.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionService
 * @generated
 */
@ProviderType
public class SCFrameworkVersionServiceWrapper
	implements SCFrameworkVersionService,
		ServiceWrapper<SCFrameworkVersionService> {
	public SCFrameworkVersionServiceWrapper(
		SCFrameworkVersionService scFrameworkVersionService) {
		_scFrameworkVersionService = scFrameworkVersionService;
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		java.lang.String name, java.lang.String url, boolean active,
		int priority, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionService.addFrameworkVersion(name, url,
			active, priority, serviceContext);
	}

	@Override
	public void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scFrameworkVersionService.deleteFrameworkVersion(frameworkVersionId);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionService.getFrameworkVersion(frameworkVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active) {
		return _scFrameworkVersionService.getFrameworkVersions(groupId, active);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end) {
		return _scFrameworkVersionService.getFrameworkVersions(groupId, active,
			start, end);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _scFrameworkVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scFrameworkVersionService.updateFrameworkVersion(frameworkVersionId,
			name, url, active, priority);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SCFrameworkVersionService getWrappedSCFrameworkVersionService() {
		return _scFrameworkVersionService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSCFrameworkVersionService(
		SCFrameworkVersionService scFrameworkVersionService) {
		_scFrameworkVersionService = scFrameworkVersionService;
	}

	@Override
	public SCFrameworkVersionService getWrappedService() {
		return _scFrameworkVersionService;
	}

	@Override
	public void setWrappedService(
		SCFrameworkVersionService scFrameworkVersionService) {
		_scFrameworkVersionService = scFrameworkVersionService;
	}

	private SCFrameworkVersionService _scFrameworkVersionService;
}