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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for OAuth2Application. This utility wraps
 * {@link com.liferay.oauth2.provider.service.impl.OAuth2ApplicationServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationService
 * @see com.liferay.oauth2.provider.service.base.OAuth2ApplicationServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.impl.OAuth2ApplicationServiceImpl
 * @generated
 */
@ProviderType
public class OAuth2ApplicationServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.oauth2.provider.service.impl.OAuth2ApplicationServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use {@link OAuth2ApplicationServiceUtil} to access the o auth2 application remote service.
	*/
	public static com.liferay.oauth2.provider.model.OAuth2Application addOAuth2Application(
		java.util.List<com.liferay.oauth2.provider.constants.GrantType> allowedGrantTypesList,
		boolean clientConfidential, java.lang.String clientId,
		java.lang.String clientSecret, java.lang.String description,
		java.util.List<java.lang.String> featuresList,
		java.lang.String homePageURL, long iconFileEntryId,
		java.lang.String name, java.lang.String privacyPolicyURL,
		java.util.List<java.lang.String> redirectURIsList,
		java.util.List<java.lang.String> scopesList,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOAuth2Application(allowedGrantTypesList,
			clientConfidential, clientId, clientSecret, description,
			featuresList, homePageURL, iconFileEntryId, name, privacyPolicyURL,
			redirectURIsList, scopesList, serviceContext);
	}

	public static void check(
		com.liferay.oauth2.provider.model.OAuth2Application oAuth2Application,
		java.lang.String action)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		getService().check(oAuth2Application, action);
	}

	public static void check(java.lang.String action)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		getService().check(action);
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application deleteOAuth2Application(
		long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOAuth2Application(oAuth2ApplicationId);
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application fetchOAuth2Application(
		long companyId, java.lang.String clientId)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		return getService().fetchOAuth2Application(companyId, clientId);
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application getOAuth2Application(
		long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOAuth2Application(oAuth2ApplicationId);
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application getOAuth2Application(
		long companyId, java.lang.String clientId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException,
			com.liferay.portal.kernel.security.auth.PrincipalException {
		return getService().getOAuth2Application(companyId, clientId);
	}

	public static java.util.List<com.liferay.oauth2.provider.model.OAuth2Application> getOAuth2Applications(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Application> orderByComparator) {
		return getService()
				   .getOAuth2Applications(companyId, start, end,
			orderByComparator);
	}

	public static int getOAuth2ApplicationsCount(long companyId) {
		return getService().getOAuth2ApplicationsCount(companyId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application updateIcon(
		long oAuth2ApplicationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateIcon(oAuth2ApplicationId, inputStream);
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application updateOAuth2Application(
		long oAuth2ApplicationId,
		java.util.List<com.liferay.oauth2.provider.constants.GrantType> allowedGrantTypesList,
		boolean clientConfidential, java.lang.String clientId,
		java.lang.String clientSecret, java.lang.String description,
		java.util.List<java.lang.String> featuresList,
		java.lang.String homePageURL, long iconFileEntryId,
		java.lang.String name, java.lang.String privacyPolicyURL,
		java.util.List<java.lang.String> redirectURIsList,
		java.util.List<java.lang.String> scopesList,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOAuth2Application(oAuth2ApplicationId,
			allowedGrantTypesList, clientConfidential, clientId, clientSecret,
			description, featuresList, homePageURL, iconFileEntryId, name,
			privacyPolicyURL, redirectURIsList, scopesList, serviceContext);
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application updateScopes(
		long oAuth2ApplicationId, java.util.List<java.lang.String> scopes)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateScopes(oAuth2ApplicationId, scopes);
	}

	public static OAuth2ApplicationService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2ApplicationService, OAuth2ApplicationService> _serviceTracker =
		ServiceTrackerFactory.open(OAuth2ApplicationService.class);
}