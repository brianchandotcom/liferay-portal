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

package com.liferay.portal.security.wedeploy.auth.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;

/**
 * Provides the remote service interface for WeDeployAuthApp. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthAppServiceUtil
 * @see com.liferay.portal.security.wedeploy.auth.service.base.WeDeployAuthAppServiceBaseImpl
 * @see com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=wedeployauth", "json.web.service.context.path=WeDeployAuthApp"}, service = WeDeployAuthAppService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface WeDeployAuthAppService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WeDeployAuthAppServiceUtil} to access the we deploy auth app remote service. Add custom service methods to {@link com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public WeDeployAuthApp addWeDeployAuthApp(java.lang.String name,
		java.lang.String redirectURI, ServiceContext serviceContext)
		throws PortalException;

	public WeDeployAuthApp deleteWeDeployAuthApp(long weDeployAuthAppId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();
}