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

package com.liferay.document.library.opener.google.drive.web.internal.oauth;

import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.DriveScopes;

import com.liferay.document.library.google.drive.configuration.DLGoogleDriveCompanyConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.security.GeneralSecurityException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = OAuth2Manager.class)
public class OAuth2Manager {

	public String getAuthorizationURL(
			long companyId, String state, String redirectUri)
		throws PortalException {

		GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow =
			_getGoogleAuthorizationCodeFlow(companyId);

		if (googleAuthorizationCodeFlow == null) {
			throw new PortalException(
				"No Google authorization code flow found");
		}

		GoogleAuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl =
			googleAuthorizationCodeFlow.newAuthorizationUrl();

		googleAuthorizationCodeRequestUrl =
			googleAuthorizationCodeRequestUrl.setState(state);

		googleAuthorizationCodeRequestUrl =
			googleAuthorizationCodeRequestUrl.setRedirectUri(redirectUri);

		googleAuthorizationCodeRequestUrl =
			googleAuthorizationCodeRequestUrl.setScopes(
				Collections.singleton(DriveScopes.DRIVE_FILE));

		return googleAuthorizationCodeRequestUrl.build();
	}

	public Credential getCredential(long companyId, long userId)
		throws PortalException {

		try {
			GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow =
				_getGoogleAuthorizationCodeFlow(companyId);

			if (googleAuthorizationCodeFlow == null) {
				return null;
			}

			return googleAuthorizationCodeFlow.loadCredential(
				String.valueOf(userId));
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	public boolean isConfigured(long companyId) {
		try {
			DLGoogleDriveCompanyConfiguration
				dlGoogleDriveCompanyConfiguration =
					_getDlGoogleDriveCompanyConfiguration(companyId);

			if (Validator.isNotNull(
					dlGoogleDriveCompanyConfiguration.clientId()) &&
				Validator.isNotNull(
					dlGoogleDriveCompanyConfiguration.clientSecret())) {

				return true;
			}

			return false;
		}
		catch (ConfigurationException ce) {
			if (_log.isDebugEnabled()) {
				_log.debug(ce, ce);
			}

			return false;
		}
	}

	public void requestAuthorizationToken(
			long companyId, long userId, String code, String redirectUri)
		throws IOException, PortalException {

		GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow =
			_getGoogleAuthorizationCodeFlow(companyId);

		if (googleAuthorizationCodeFlow == null) {
			throw new PortalException(
				"No Google Authorization Code Flow found");
		}

		GoogleAuthorizationCodeTokenRequest
			googleAuthorizationCodeTokenRequest =
				googleAuthorizationCodeFlow.newTokenRequest(code);

		googleAuthorizationCodeTokenRequest =
			googleAuthorizationCodeTokenRequest.setRedirectUri(redirectUri);

		GoogleTokenResponse googleTokenResponse =
			googleAuthorizationCodeTokenRequest.execute();

		googleAuthorizationCodeFlow.createAndStoreCredential(
			googleTokenResponse, String.valueOf(userId));
	}

	public void revokeCredential(long companyId, long userId)
		throws PortalException {

		try {
			GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow =
				_getGoogleAuthorizationCodeFlow(companyId);

			if (googleAuthorizationCodeFlow != null) {
				DataStore<StoredCredential> credentialDataStore =
					googleAuthorizationCodeFlow.getCredentialDataStore();

				credentialDataStore.delete(String.valueOf(userId));
			}
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Deactivate
	protected void deactivate() {
		_googleAuthorizationCodeFlows.clear();
	}

	private DLGoogleDriveCompanyConfiguration
			_getDlGoogleDriveCompanyConfiguration(long companyId)
		throws ConfigurationException {

		return _configurationProvider.getCompanyConfiguration(
			DLGoogleDriveCompanyConfiguration.class, companyId);
	}

	private synchronized GoogleAuthorizationCodeFlow
			_getGoogleAuthorizationCodeFlow(long companyId)
		throws PortalException {

		if (!isConfigured(companyId)) {
			return null;
		}

		try {
			DLGoogleDriveCompanyConfiguration
				dlGoogleDriveCompanyConfiguration =
					_getDlGoogleDriveCompanyConfiguration(companyId);

			if (_googleAuthorizationCodeFlows.containsKey(companyId)) {
				GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow =
					_googleAuthorizationCodeFlows.get(companyId);

				ClientParametersAuthentication clientParametersAuthentication =
					(ClientParametersAuthentication)
						googleAuthorizationCodeFlow.getClientAuthentication();

				if (StringUtil.equals(
						clientParametersAuthentication.getClientId(),
						dlGoogleDriveCompanyConfiguration.clientId()) &&
					StringUtil.equals(
						clientParametersAuthentication.getClientSecret(),
						dlGoogleDriveCompanyConfiguration.clientSecret())) {

					return googleAuthorizationCodeFlow;
				}

				DataStore<StoredCredential> credentialDataStore =
					googleAuthorizationCodeFlow.getCredentialDataStore();

				credentialDataStore.clear();
			}

			GoogleAuthorizationCodeFlow.Builder
				googleAuthorizationCodeFlowBuilder =
					new GoogleAuthorizationCodeFlow.Builder(
						GoogleNetHttpTransport.newTrustedTransport(),
						JacksonFactory.getDefaultInstance(),
						GetterUtil.getString(
							dlGoogleDriveCompanyConfiguration.clientId()),
						GetterUtil.getString(
							dlGoogleDriveCompanyConfiguration.clientSecret()),
						Collections.singleton(DriveScopes.DRIVE_FILE));

			googleAuthorizationCodeFlowBuilder =
				googleAuthorizationCodeFlowBuilder.setDataStoreFactory(
					MemoryDataStoreFactory.getDefaultInstance());

			GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow =
				googleAuthorizationCodeFlowBuilder.build();

			_googleAuthorizationCodeFlows.put(
				companyId, googleAuthorizationCodeFlow);

			return googleAuthorizationCodeFlow;
		}
		catch (GeneralSecurityException gse) {
			throw new PrincipalException(gse);
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(OAuth2Manager.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	private final Map<Long, GoogleAuthorizationCodeFlow>
		_googleAuthorizationCodeFlows = new ConcurrentHashMap<>();

}