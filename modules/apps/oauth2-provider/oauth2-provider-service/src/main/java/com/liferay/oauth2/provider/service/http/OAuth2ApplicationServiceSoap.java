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

package com.liferay.oauth2.provider.service.http;

import com.liferay.oauth2.provider.service.OAuth2ApplicationServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>OAuth2ApplicationServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.oauth2.provider.model.OAuth2ApplicationSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.oauth2.provider.model.OAuth2Application</code>, that is translated to a
 * <code>com.liferay.oauth2.provider.model.OAuth2ApplicationSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationServiceHttp
 * @generated
 */
public class OAuth2ApplicationServiceSoap {

	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap
			addOAuth2Application(
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				long clientCredentialUserId, String clientId, int clientProfile,
				String clientSecret, String description,
				java.util.List<String> featuresList, String homePageURL,
				long iconFileEntryId, String name, String privacyPolicyURL,
				java.util.List<String> redirectURIsList,
				java.util.List<String> scopeAliasesList,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.oauth2.provider.model.OAuth2Application returnValue =
				OAuth2ApplicationServiceUtil.addOAuth2Application(
					allowedGrantTypesList, clientCredentialUserId, clientId,
					clientProfile, clientSecret, description, featuresList,
					homePageURL, iconFileEntryId, name, privacyPolicyURL,
					redirectURIsList, scopeAliasesList, serviceContext);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap
			addOAuth2Application(
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				String clientId, int clientProfile, String clientSecret,
				String description, java.util.List<String> featuresList,
				String homePageURL, long iconFileEntryId, String name,
				String privacyPolicyURL,
				java.util.List<String> redirectURIsList,
				java.util.List<String> scopeAliasesList,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.oauth2.provider.model.OAuth2Application returnValue =
				OAuth2ApplicationServiceUtil.addOAuth2Application(
					allowedGrantTypesList, clientId, clientProfile,
					clientSecret, description, featuresList, homePageURL,
					iconFileEntryId, name, privacyPolicyURL, redirectURIsList,
					scopeAliasesList, serviceContext);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap
			deleteOAuth2Application(long oAuth2ApplicationId)
		throws RemoteException {

		try {
			com.liferay.oauth2.provider.model.OAuth2Application returnValue =
				OAuth2ApplicationServiceUtil.deleteOAuth2Application(
					oAuth2ApplicationId);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap
			fetchOAuth2Application(long companyId, String clientId)
		throws RemoteException {

		try {
			com.liferay.oauth2.provider.model.OAuth2Application returnValue =
				OAuth2ApplicationServiceUtil.fetchOAuth2Application(
					companyId, clientId);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap
			getOAuth2Application(long oAuth2ApplicationId)
		throws RemoteException {

		try {
			com.liferay.oauth2.provider.model.OAuth2Application returnValue =
				OAuth2ApplicationServiceUtil.getOAuth2Application(
					oAuth2ApplicationId);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap
			getOAuth2Application(long companyId, String clientId)
		throws RemoteException {

		try {
			com.liferay.oauth2.provider.model.OAuth2Application returnValue =
				OAuth2ApplicationServiceUtil.getOAuth2Application(
					companyId, clientId);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap[]
			getOAuth2Applications(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.oauth2.provider.model.OAuth2Application>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.oauth2.provider.model.OAuth2Application>
				returnValue =
					OAuth2ApplicationServiceUtil.getOAuth2Applications(
						companyId, start, end, orderByComparator);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getOAuth2ApplicationsCount(long companyId)
		throws RemoteException {

		try {
			int returnValue =
				OAuth2ApplicationServiceUtil.getOAuth2ApplicationsCount(
					companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap
			updateOAuth2Application(
				long oAuth2ApplicationId,
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				long clientCredentialUserId, String clientId, int clientProfile,
				String clientSecret, String description,
				java.util.List<String> featuresList, String homePageURL,
				long iconFileEntryId, String name, String privacyPolicyURL,
				java.util.List<String> redirectURIsList,
				long auth2ApplicationScopeAliasesId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.oauth2.provider.model.OAuth2Application returnValue =
				OAuth2ApplicationServiceUtil.updateOAuth2Application(
					oAuth2ApplicationId, allowedGrantTypesList,
					clientCredentialUserId, clientId, clientProfile,
					clientSecret, description, featuresList, homePageURL,
					iconFileEntryId, name, privacyPolicyURL, redirectURIsList,
					auth2ApplicationScopeAliasesId, serviceContext);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap
			updateOAuth2Application(
				long oAuth2ApplicationId,
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				String clientId, int clientProfile, String clientSecret,
				String description, java.util.List<String> featuresList,
				String homePageURL, long iconFileEntryId, String name,
				String privacyPolicyURL,
				java.util.List<String> redirectURIsList,
				long auth2ApplicationScopeAliasesId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.oauth2.provider.model.OAuth2Application returnValue =
				OAuth2ApplicationServiceUtil.updateOAuth2Application(
					oAuth2ApplicationId, allowedGrantTypesList, clientId,
					clientProfile, clientSecret, description, featuresList,
					homePageURL, iconFileEntryId, name, privacyPolicyURL,
					redirectURIsList, auth2ApplicationScopeAliasesId,
					serviceContext);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2ApplicationSoap
			updateScopeAliases(
				long oAuth2ApplicationId,
				java.util.List<String> scopeAliasesList)
		throws RemoteException {

		try {
			com.liferay.oauth2.provider.model.OAuth2Application returnValue =
				OAuth2ApplicationServiceUtil.updateScopeAliases(
					oAuth2ApplicationId, scopeAliasesList);

			return com.liferay.oauth2.provider.model.OAuth2ApplicationSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		OAuth2ApplicationServiceSoap.class);

}