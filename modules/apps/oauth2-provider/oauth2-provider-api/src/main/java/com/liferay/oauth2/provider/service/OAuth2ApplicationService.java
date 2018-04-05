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

package com.liferay.oauth2.provider.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.List;

/**
 * Provides the remote service interface for OAuth2Application. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationServiceUtil
 * @see com.liferay.oauth2.provider.service.base.OAuth2ApplicationServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.impl.OAuth2ApplicationServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=oauthtwo", "json.web.service.context.path=OAuth2Application"}, service = OAuth2ApplicationService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface OAuth2ApplicationService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2ApplicationServiceUtil} to access the o auth2 application remote service. Add custom service methods to {@link com.liferay.oauth2.provider.service.impl.OAuth2ApplicationServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public OAuth2Application addOAuth2Application(
		List<GrantType> allowedGrantTypesList, java.lang.String clientId,
		int clientProfile, java.lang.String clientSecret,
		java.lang.String description, List<java.lang.String> featuresList,
		java.lang.String homePageURL, long iconFileEntryId,
		java.lang.String name, java.lang.String privacyPolicyURL,
		List<java.lang.String> redirectURIsList,
		List<java.lang.String> scopeAliasesList, ServiceContext serviceContext)
		throws PortalException;

	public OAuth2Application deleteOAuth2Application(long oAuth2ApplicationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OAuth2Application fetchOAuth2Application(long companyId,
		java.lang.String clientId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OAuth2Application getOAuth2Application(long oAuth2ApplicationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OAuth2Application getOAuth2Application(long companyId,
		java.lang.String clientId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<OAuth2Application> getOAuth2Applications(long companyId,
		int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOAuth2ApplicationsCount(long companyId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public OAuth2Application updateIcon(long oAuth2ApplicationId,
		InputStream inputStream) throws PortalException;

	public OAuth2Application updateOAuth2Application(long oAuth2ApplicationId,
		List<GrantType> allowedGrantTypesList, java.lang.String clientId,
		int clientProfile, java.lang.String clientSecret,
		java.lang.String description, List<java.lang.String> featuresList,
		java.lang.String homePageURL, long iconFileEntryId,
		java.lang.String name, java.lang.String privacyPolicyURL,
		List<java.lang.String> redirectURIsList,
		long auth2ApplicationScopeAliasesId, ServiceContext serviceContext)
		throws PortalException;

	public OAuth2Application updateScopeAliases(long oAuth2ApplicationId,
		List<java.lang.String> scopeAliasesList) throws PortalException;
}