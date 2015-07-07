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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RepositoryService}.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryService
 * @generated
 */
@ProviderType
public class RepositoryServiceWrapper implements RepositoryService,
	ServiceWrapper<RepositoryService> {
	public RepositoryServiceWrapper(RepositoryService repositoryService) {
		_repositoryService = repositoryService;
	}

	@Override
	public com.liferay.portal.model.Repository addRepository(long groupId,
		long classNameId, long parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String portletId,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryService.addRepository(groupId, classNameId,
			parentFolderId, name, description, portletId,
			typeSettingsProperties, serviceContext);
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
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _repositoryService.getBeanIdentifier();
	}

	@Override
	public com.liferay.portal.model.Repository getRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryService.getRepository(repositoryId);
	}

	@Override
	public java.lang.String[] getSupportedConfigurations(long classNameId) {
		return _repositoryService.getSupportedConfigurations(classNameId);
	}

	@Override
	public java.lang.String[] getSupportedParameters(
		java.lang.String className, java.lang.String configuration) {
		return _repositoryService.getSupportedParameters(className,
			configuration);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#getSupportedParameters(String, String)}
	*/
	@Deprecated
	@Override
	public java.lang.String[] getSupportedParameters(long classNameId,
		java.lang.String configuration) {
		return _repositoryService.getSupportedParameters(classNameId,
			configuration);
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryService.getTypeSettingsProperties(repositoryId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_repositoryService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void updateRepository(long repositoryId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		_repositoryService.updateRepository(repositoryId, name, description);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public RepositoryService getWrappedRepositoryService() {
		return _repositoryService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedRepositoryService(RepositoryService repositoryService) {
		_repositoryService = repositoryService;
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