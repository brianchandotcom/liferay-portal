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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * Provides the remote service interface for Repository. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryServiceUtil
 * @see com.liferay.portal.service.base.RepositoryServiceBaseImpl
 * @see com.liferay.portal.service.impl.RepositoryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface RepositoryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RepositoryServiceUtil} to access the repository remote service. Add custom service methods to {@link com.liferay.portal.service.impl.RepositoryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public Repository addRepository(long groupId, long classNameId,
		long parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String portletId,
		UnicodeProperties typeSettingsProperties,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException;

	public void checkRepository(long repositoryId) throws PortalException;

	public void deleteRepository(long repositoryId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Repository getRepository(long repositoryId)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getSupportedConfigurations(long classNameId);

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getSupportedParameters(
		java.lang.String className, java.lang.String configuration);

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getSupportedParameters(long classNameId,
		java.lang.String configuration);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws PortalException;

	public void updateRepository(long repositoryId, java.lang.String name,
		java.lang.String description) throws PortalException;
}