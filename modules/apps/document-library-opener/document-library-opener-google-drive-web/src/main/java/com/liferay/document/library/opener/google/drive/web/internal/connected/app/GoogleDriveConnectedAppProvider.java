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

package com.liferay.document.library.opener.google.drive.web.internal.connected.app;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;

import com.liferay.connected.app.ConnectedApp;
import com.liferay.connected.app.ConnectedAppProvider;
import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.oauth.OAuth2Manager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.security.GeneralSecurityException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ConnectedAppProvider.class)
public class GoogleDriveConnectedAppProvider implements ConnectedAppProvider {

	@Override
	public ConnectedApp getConnectedApp(User user) throws PortalException {
		if (!_dlOpenerGoogleDriveManager.isConfigured(user.getCompanyId())) {
			return null;
		}

		Credential credential = _oAuth2Manager.getCredential(
			user.getCompanyId(), user.getUserId());

		if (credential == null) {
			return null;
		}

		return new ConnectedApp() {

			@Override
			public String getImageURL() {
				return _servletContext.getContextPath() +
					"/images/google_drive.png";
			}

			@Override
			public String getKey() {
				return "google-drive";
			}

			@Override
			public String getName(Locale locale) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					locale, getClass());

				StringBundler sb = new StringBundler(5);

				sb.append(LanguageUtil.get(resourceBundle, "google-drive"));

				String emailAddress = _getGoogleDriveUserEmailAddress(
					credential);

				if (Validator.isNotNull(emailAddress)) {
					sb.append(StringPool.SPACE);
					sb.append(StringPool.OPEN_PARENTHESIS);
					sb.append(emailAddress);
					sb.append(StringPool.CLOSE_PARENTHESIS);
				}

				return sb.toString();
			}

			@Override
			public void revoke() throws PortalException {
				_oAuth2Manager.revokeCredential(
					user.getCompanyId(), user.getUserId());
			}

		};
	}

	@Activate
	protected void activate() throws GeneralSecurityException, IOException {
		_jsonFactory = JacksonFactory.getDefaultInstance();
		_netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
	}

	private String _getGoogleDriveUserEmailAddress(Credential credential) {
		try {
			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory, credential
			).build();

			Drive.About driveAbout = drive.about();

			Drive.About.Get get = driveAbout.get();

			get.setFields("user");

			About about = get.execute();

			com.google.api.services.drive.model.User user = about.getUser();

			return user.getEmailAddress();
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleDriveConnectedAppProvider.class);

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	private JacksonFactory _jsonFactory;
	private NetHttpTransport _netHttpTransport;

	@Reference
	private OAuth2Manager _oAuth2Manager;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.opener.google.drive.web)"
	)
	private ServletContext _servletContext;

}