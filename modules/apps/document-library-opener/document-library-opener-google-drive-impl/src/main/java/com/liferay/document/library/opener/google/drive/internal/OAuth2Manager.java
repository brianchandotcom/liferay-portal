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

package com.liferay.document.library.opener.google.drive.internal;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.DriveScopes;

import com.liferay.document.library.opener.google.drive.internal.configuration.DLOpenerGoogleDriveConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.security.GeneralSecurityException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
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

			return googleAuthorizationCodeFlow.loadCredential(
				String.valueOf(userId));
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	public boolean isConfigured(long companyId) {
		try {
			DLOpenerGoogleDriveConfiguration dlOpenerGoogleDriveConfiguration =
				_getDlOpenerGoogleDriveConfiguration(companyId);

			if (Validator.isNotNull(
					dlOpenerGoogleDriveConfiguration.clientId()) &&
				Validator.isNotNull(
					dlOpenerGoogleDriveConfiguration.clientSecret())) {

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

	private DLOpenerGoogleDriveConfiguration
			_getDlOpenerGoogleDriveConfiguration(long companyId)
		throws ConfigurationException {

		return _configurationProvider.getCompanyConfiguration(
			DLOpenerGoogleDriveConfiguration.class, companyId);
	}

	private GoogleAuthorizationCodeFlow _getGoogleAuthorizationCodeFlow(
			long companyId)
		throws PortalException {

		if (_googleAuthorizationCodeFlows.containsKey(companyId)) {
			return _googleAuthorizationCodeFlows.get(companyId);
		}

		try {
			DLOpenerGoogleDriveConfiguration dlOpenerGoogleDriveConfiguration =
				_getDlOpenerGoogleDriveConfiguration(companyId);

			GoogleAuthorizationCodeFlow.Builder
				googleAuthorizationCodeFlowBuilder =
					new GoogleAuthorizationCodeFlow.Builder(
						GoogleNetHttpTransport.newTrustedTransport(),
						JacksonFactory.getDefaultInstance(),
						GetterUtil.getString(
							dlOpenerGoogleDriveConfiguration.clientId()),
						GetterUtil.getString(
							dlOpenerGoogleDriveConfiguration.clientSecret()),
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