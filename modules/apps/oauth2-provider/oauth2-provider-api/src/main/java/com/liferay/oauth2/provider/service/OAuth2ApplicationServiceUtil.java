/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.List;

/**
 * Provides the remote service utility for OAuth2Application. This utility wraps
 * <code>com.liferay.oauth2.provider.service.impl.OAuth2ApplicationServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationService
 * @generated
 */
public class OAuth2ApplicationServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.oauth2.provider.service.impl.OAuth2ApplicationServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static OAuth2Application addOAuth2Application(
			List<com.liferay.oauth2.provider.constants.GrantType>
				allowedGrantTypesList,
			String clientAuthenticationMethod, long clientCredentialUserId,
			String clientId, int clientProfile, String clientSecret,
			String description, List<String> featuresList, String homePageURL,
			long iconFileEntryId, String jwks, String name,
			String privacyPolicyURL, List<String> redirectURIsList,
			boolean rememberDevice, List<String> scopeAliasesList,
			boolean trustedApplication,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOAuth2Application(
			allowedGrantTypesList, clientAuthenticationMethod,
			clientCredentialUserId, clientId, clientProfile, clientSecret,
			description, featuresList, homePageURL, iconFileEntryId, jwks, name,
			privacyPolicyURL, redirectURIsList, rememberDevice,
			scopeAliasesList, trustedApplication, serviceContext);
	}

	public static OAuth2Application deleteOAuth2Application(
			long oAuth2ApplicationId)
		throws PortalException {

		return getService().deleteOAuth2Application(oAuth2ApplicationId);
	}

	public static OAuth2Application fetchOAuth2Application(
			long companyId, String clientId)
		throws PortalException {

		return getService().fetchOAuth2Application(companyId, clientId);
	}

	public static OAuth2Application getOAuth2Application(
			long oAuth2ApplicationId)
		throws PortalException {

		return getService().getOAuth2Application(oAuth2ApplicationId);
	}

	public static OAuth2Application getOAuth2Application(
			long companyId, String clientId)
		throws PortalException {

		return getService().getOAuth2Application(companyId, clientId);
	}

	public static List<OAuth2Application> getOAuth2Applications(
		long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return getService().getOAuth2Applications(
			companyId, start, end, orderByComparator);
	}

	public static int getOAuth2ApplicationsCount(long companyId) {
		return getService().getOAuth2ApplicationsCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static OAuth2Application updateIcon(
			long oAuth2ApplicationId, InputStream inputStream)
		throws PortalException {

		return getService().updateIcon(oAuth2ApplicationId, inputStream);
	}

	public static OAuth2Application updateOAuth2Application(
			long oAuth2ApplicationId, long oAuth2ApplicationScopeAliasesId,
			List<com.liferay.oauth2.provider.constants.GrantType>
				allowedGrantTypesList,
			String clientAuthenticationMethod, long clientCredentialUserId,
			String clientId, int clientProfile, String clientSecret,
			String description, List<String> featuresList, String homePageURL,
			long iconFileEntryId, String jwks, String name,
			String privacyPolicyURL, List<String> redirectURIsList,
			boolean rememberDevice, boolean trustedApplication)
		throws PortalException {

		return getService().updateOAuth2Application(
			oAuth2ApplicationId, oAuth2ApplicationScopeAliasesId,
			allowedGrantTypesList, clientAuthenticationMethod,
			clientCredentialUserId, clientId, clientProfile, clientSecret,
			description, featuresList, homePageURL, iconFileEntryId, jwks, name,
			privacyPolicyURL, redirectURIsList, rememberDevice,
			trustedApplication);
	}

	public static OAuth2Application updateScopeAliases(
			long oAuth2ApplicationId, List<String> scopeAliasesList)
		throws PortalException {

		return getService().updateScopeAliases(
			oAuth2ApplicationId, scopeAliasesList);
	}

	public static OAuth2ApplicationService getService() {
		return _service;
	}

	public static void setService(OAuth2ApplicationService service) {
		_service = service;
	}

	private static volatile OAuth2ApplicationService _service;

}