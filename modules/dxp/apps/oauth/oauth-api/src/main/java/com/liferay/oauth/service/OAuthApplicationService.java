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

package com.liferay.oauth.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth.model.OAuthApplication;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.InputStream;

/**
 * Provides the remote service interface for OAuthApplication. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Ivica Cardic
 * @see OAuthApplicationServiceUtil
 * @see com.liferay.oauth.service.base.OAuthApplicationServiceBaseImpl
 * @see com.liferay.oauth.service.impl.OAuthApplicationServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=oauth", "json.web.service.context.path=OAuthApplication"}, service = OAuthApplicationService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface OAuthApplicationService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuthApplicationServiceUtil} to access the o auth application remote service. Add custom service methods to {@link com.liferay.oauth.service.impl.OAuthApplicationServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public OAuthApplication addOAuthApplication(String name,
		String description, int accessLevel, boolean shareableAccessToken,
		String callbackURI, String websiteURL, ServiceContext serviceContext)
		throws PortalException;

	public void deleteLogo(long oAuthApplicationId) throws PortalException;

	public OAuthApplication deleteOAuthApplication(long oAuthApplicationId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public OAuthApplication updateLogo(long oAuthApplicationId,
		InputStream inputStream) throws PortalException;

	public OAuthApplication updateOAuthApplication(long oAuthApplicationId,
		String name, String description, boolean shareableAccessToken,
		String callbackURI, String websiteURL, ServiceContext serviceContext)
		throws PortalException;
}