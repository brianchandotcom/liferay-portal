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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for Repository. This utility wraps
 * {@link com.liferay.portal.service.impl.RepositoryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryService
 * @see com.liferay.portal.service.base.RepositoryServiceBaseImpl
 * @see com.liferay.portal.service.impl.RepositoryServiceImpl
 * @generated
 */
@ProviderType
public class RepositoryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.RepositoryServiceImpl} and rerun ServiceBuilder to regenerate this class.
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

	public static com.liferay.portal.model.Repository addRepository(
		long groupId, long classNameId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRepository(groupId, classNameId, parentFolderId, name,
			description, portletId, typeSettingsProperties, serviceContext);
	}

	public static void checkRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkRepository(repositoryId);
	}

	public static void deleteRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRepository(repositoryId);
	}

	public static com.liferay.portal.kernel.repository.LocalRepository getLocalRepositoryImpl(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLocalRepositoryImpl(repositoryId);
	}

	public static com.liferay.portal.kernel.repository.LocalRepository getLocalRepositoryImpl(
		long folderId, long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLocalRepositoryImpl(folderId, fileEntryId, fileVersionId);
	}

	public static com.liferay.portal.model.Repository getRepository(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRepository(repositoryId);
	}

	public static com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRepositoryImpl(repositoryId);
	}

	public static com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
		long folderId, long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getRepositoryImpl(folderId, fileEntryId, fileVersionId);
	}

	public static java.lang.String[] getSupportedConfigurations(
		long classNameId) {
		return getService().getSupportedConfigurations(classNameId);
	}

	public static java.lang.String[] getSupportedParameters(long classNameId,
		java.lang.String configuration) {
		return getService().getSupportedParameters(classNameId, configuration);
	}

	public static com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTypeSettingsProperties(repositoryId);
	}

	public static void updateRepository(long repositoryId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateRepository(repositoryId, name, description);
	}

	public static RepositoryService getService() {
		if (_service == null) {
			_service = (RepositoryService)PortalBeanLocatorUtil.locate(RepositoryService.class.getName());

			ReferenceRegistry.registerReference(RepositoryServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(RepositoryService service) {
	}

	private static RepositoryService _service;
}