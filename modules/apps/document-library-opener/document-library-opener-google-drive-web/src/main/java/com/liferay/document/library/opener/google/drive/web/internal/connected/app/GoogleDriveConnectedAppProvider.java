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

import com.liferay.connected.apps.ConnectedApp;
import com.liferay.connected.apps.ConnectedAppProvider;
import com.liferay.document.library.opener.google.drive.web.internal.OAuth2Manager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ConnectedAppProvider.class)
public class GoogleDriveConnectedAppProvider implements ConnectedAppProvider {

	@Override
	public ConnectedApp getConnectedApp(User user) throws PortalException {
		Credential credential = _oAuth2Manager.getCredential(
			user.getCompanyId(), user.getUserId());

		if (credential != null) {
			return new ConnectedApp() {

				@Override
				public String getImageURL() {
					return _GOOGLE_DRIVE_LOGO_URL;
				}

				@Override
				public String getKey() {
					return "google-drive";
				}

				@Override
				public String getName(Locale locale) {
					ResourceBundle resourceBundle =
						ResourceBundleUtil.getBundle(locale, getClass());

					return LanguageUtil.get(resourceBundle, "google-drive");
				}

				@Override
				public void revoke() throws PortalException {
					_oAuth2Manager.revokeCredential(
						user.getCompanyId(), user.getUserId());
				}

			};
		}

		return null;
	}

	private static final String _GOOGLE_DRIVE_LOGO_URL =
		"https://firebasestorage.googleapis.com/v0/b/drive-assets.google.com." +
			"a.appspot.com/o/Asset%20-%20Drive%20Icon512.png?alt=media";

	@Reference
	private OAuth2Manager _oAuth2Manager;

}