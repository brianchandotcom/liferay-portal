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
 * Provides a wrapper for {@link SCProductVersionService}.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductVersionService
 * @generated
 */
@ProviderType
public class SCProductVersionServiceWrapper implements SCProductVersionService,
	ServiceWrapper<SCProductVersionService> {
	public SCProductVersionServiceWrapper(
		SCProductVersionService scProductVersionService) {
		_scProductVersionService = scProductVersionService;
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion addProductVersion(
		long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionService.addProductVersion(productEntryId,
			version, changeLog, downloadPageURL, directDownloadURL,
			testDirectDownloadURL, repoStoreArtifact, frameworkVersionIds,
			serviceContext);
	}

	@Override
	public void deleteProductVersion(long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scProductVersionService.deleteProductVersion(productVersionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _scProductVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getProductVersion(
		long productVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionService.getProductVersion(productVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getProductVersions(
		long productEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionService.getProductVersions(productEntryId,
			start, end);
	}

	@Override
	public int getProductVersionsCount(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionService.getProductVersionsCount(productEntryId);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scProductVersionService.updateProductVersion(productVersionId,
			version, changeLog, downloadPageURL, directDownloadURL,
			testDirectDownloadURL, repoStoreArtifact, frameworkVersionIds);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SCProductVersionService getWrappedSCProductVersionService() {
		return _scProductVersionService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSCProductVersionService(
		SCProductVersionService scProductVersionService) {
		_scProductVersionService = scProductVersionService;
	}

	@Override
	public SCProductVersionService getWrappedService() {
		return _scProductVersionService;
	}

	@Override
	public void setWrappedService(
		SCProductVersionService scProductVersionService) {
		_scProductVersionService = scProductVersionService;
	}

	private SCProductVersionService _scProductVersionService;
}