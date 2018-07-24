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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.io.IOException;

import java.security.GeneralSecurityException;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.opener.google.drive.internal.configuration.DLOpenerGoogleDriveConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = OAuth2Manager.class
)
public class OAuth2Manager {

	public String getAuthorizationURL(String state, String redirectUri) {
		GoogleAuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl =
			_googleAuthorizationCodeFlow.newAuthorizationUrl();

		googleAuthorizationCodeRequestUrl =
			googleAuthorizationCodeRequestUrl.setState(state);

		googleAuthorizationCodeRequestUrl =
			googleAuthorizationCodeRequestUrl.setRedirectUri(redirectUri);

		googleAuthorizationCodeRequestUrl =
			googleAuthorizationCodeRequestUrl.setScopes(
				Collections.singleton(DriveScopes.DRIVE_FILE));

		return googleAuthorizationCodeRequestUrl.build();
	}

	public Credential getCredential(long userId) throws IOException {
		return _googleAuthorizationCodeFlow.loadCredential(
			String.valueOf(userId));
	}

	public void requestAuthorizationToken(
			long userId, String code, String redirectUri)
		throws IOException {

		GoogleAuthorizationCodeTokenRequest
			googleAuthorizationCodeTokenRequest =
				_googleAuthorizationCodeFlow.newTokenRequest(code);

		googleAuthorizationCodeTokenRequest =
			googleAuthorizationCodeTokenRequest.setRedirectUri(redirectUri);

		GoogleTokenResponse googleTokenResponse =
			googleAuthorizationCodeTokenRequest.execute();

		_googleAuthorizationCodeFlow.createAndStoreCredential(
			googleTokenResponse, String.valueOf(userId));
	}

	@Activate
	protected void activate(Map<String, Object> properties)
		throws GeneralSecurityException, IOException {

		DLOpenerGoogleDriveConfiguration dlOpenerGoogleDriveConfiguration =
			ConfigurableUtil.createConfigurable(
				DLOpenerGoogleDriveConfiguration.class, properties);

		GoogleAuthorizationCodeFlow.Builder googleAuthorizationCodeFlowBuilder =
			new GoogleAuthorizationCodeFlow.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(),
				dlOpenerGoogleDriveConfiguration.clientId(),
				dlOpenerGoogleDriveConfiguration.clientSecret(),
				Collections.singleton(DriveScopes.DRIVE_FILE));

		googleAuthorizationCodeFlowBuilder =
			googleAuthorizationCodeFlowBuilder.setDataStoreFactory(
				MemoryDataStoreFactory.getDefaultInstance());

		_googleAuthorizationCodeFlow =
			googleAuthorizationCodeFlowBuilder.build();
	}

	private GoogleAuthorizationCodeFlow _googleAuthorizationCodeFlow;

}