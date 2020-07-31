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

import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for OAuth2Application. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface OAuth2ApplicationService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.oauth2.provider.service.impl.OAuth2ApplicationServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the o auth2 application remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link OAuth2ApplicationServiceUtil} if injection and service tracking are not available.
	 */
	public OAuth2Application addOAuth2Application(
			List<GrantType> allowedGrantTypesList, long clientCredentialUserId,
			String clientId, int clientProfile, String clientSecret,
			String description, List<String> featuresList, String homePageURL,
			long iconFileEntryId, String name, String privacyPolicyURL,
			List<String> redirectURIsList, List<String> scopeAliasesList,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public OAuth2Application addOAuth2Application(
			List<GrantType> allowedGrantTypesList, String clientId,
			int clientProfile, String clientSecret, String description,
			List<String> featuresList, String homePageURL, long iconFileEntryId,
			String name, String privacyPolicyURL, List<String> redirectURIsList,
			List<String> scopeAliasesList, ServiceContext serviceContext)
		throws PortalException;

	public OAuth2Application deleteOAuth2Application(long oAuth2ApplicationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OAuth2Application fetchOAuth2Application(
			long companyId, String clientId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OAuth2Application getOAuth2Application(long oAuth2ApplicationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OAuth2Application getOAuth2Application(
			long companyId, String clientId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<OAuth2Application> getOAuth2Applications(
		long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOAuth2ApplicationsCount(long companyId);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public OAuth2Application updateIcon(
			long oAuth2ApplicationId, InputStream inputStream)
		throws PortalException;

	public OAuth2Application updateOAuth2Application(
			long oAuth2ApplicationId, List<GrantType> allowedGrantTypesList,
			long clientCredentialUserId, String clientId, int clientProfile,
			String clientSecret, String description, List<String> featuresList,
			String homePageURL, long iconFileEntryId, String name,
			String privacyPolicyURL, List<String> redirectURIsList,
			long auth2ApplicationScopeAliasesId, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public OAuth2Application updateOAuth2Application(
			long oAuth2ApplicationId, List<GrantType> allowedGrantTypesList,
			String clientId, int clientProfile, String clientSecret,
			String description, List<String> featuresList, String homePageURL,
			long iconFileEntryId, String name, String privacyPolicyURL,
			List<String> redirectURIsList, long auth2ApplicationScopeAliasesId,
			ServiceContext serviceContext)
		throws PortalException;

	public OAuth2Application updateScopeAliases(
			long oAuth2ApplicationId, List<String> scopeAliasesList)
		throws PortalException;

}