/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.one.drive.web.internal.graph;

import com.liferay.document.library.opener.one.drive.web.internal.oauth.AccessToken;

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
	public void authenticateRequest(IHttpRequest request) {
		try {
			request.addHeader(
				"Authorization", "Bearer " + _accessToken.getAccessToken());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private final AccessToken _accessToken;

}