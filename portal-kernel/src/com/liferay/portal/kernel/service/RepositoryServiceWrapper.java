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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link RepositoryService}.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryService
 * @generated
 */
public class RepositoryServiceWrapper
	implements RepositoryService, ServiceWrapper<RepositoryService> {

	public RepositoryServiceWrapper(RepositoryService repositoryService) {
		_repositoryService = repositoryService;
	}

	@Override
	public com.liferay.portal.kernel.model.Repository addRepository(
			long groupId, long classNameId, long parentFolderId,
			java.lang.String name, java.lang.String description,
			java.lang.String portletId,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _repositoryService.addRepository(
			groupId, classNameId, parentFolderId, name, description, portletId,
			typeSettingsUnicodeProperties, serviceContext);
	}

	@Override
	public void checkRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_repositoryService.checkRepository(repositoryId);
	}

	@Override
	public void deleteRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_repositoryService.deleteRepository(repositoryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _repositoryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.Repository getRepository(
			long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _repositoryService.getRepository(repositoryId);
	}

	@Override
	public com.liferay.portal.kernel.model.Repository getRepository(
			long groupId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _repositoryService.getRepository(groupId, portletId);
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
			getTypeSettingsProperties(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _repositoryService.getTypeSettingsProperties(repositoryId);
	}

	@Override
	public void updateRepository(
			long repositoryId, java.lang.String name,
			java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		_repositoryService.updateRepository(repositoryId, name, description);
	}

	@Override
	public RepositoryService getWrappedService() {
		return _repositoryService;
	}

	@Override
	public void setWrappedService(RepositoryService repositoryService) {
		_repositoryService = repositoryService;
	}

	private RepositoryService _repositoryService;

}