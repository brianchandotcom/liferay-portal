/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.onedrive.web.internal.graph;

import com.liferay.document.library.opener.onedrive.web.internal.oauth.AccessToken;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.http.IHttpRequest;

/**
 * @author Cristina González
 */
public class IAuthenticationProviderImpl implements IAuthenticationProvider {

	public IAuthenticationProviderImpl(AccessToken accessToken) {
		_accessToken = accessToken;
	}

	@Override
	public void authenticateRequest(IHttpRequest iHttpRequest) {
		try {
			iHttpRequest.addHeader(
				"Authorization", "Bearer " + _accessToken.getAccessToken());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private final AccessToken _accessToken;

}