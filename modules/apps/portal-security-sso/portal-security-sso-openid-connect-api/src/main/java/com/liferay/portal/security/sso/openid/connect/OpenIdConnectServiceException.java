/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Thuong Dinh
 */
public class OpenIdConnectServiceException extends PortalException {

	public OpenIdConnectServiceException(String msg) {
		super(msg);
	}

	public OpenIdConnectServiceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public static class AuthenticationException
		extends OpenIdConnectServiceException {

		public AuthenticationException(String msg) {
			super(msg);
		}

		public AuthenticationException(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class NoOpenIdConnectSessionException
		extends OpenIdConnectServiceException {

		public NoOpenIdConnectSessionException(String msg) {
			super(msg);
		}

		public NoOpenIdConnectSessionException(
			String msg, Throwable throwable) {

			super(msg, throwable);
		}

	}

	public static class ProviderException
		extends OpenIdConnectServiceException {

		public ProviderException(String msg) {
			super(msg);
		}

		public ProviderException(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class TokenException extends OpenIdConnectServiceException {

		public TokenException(String msg) {
			super(msg);
		}

		public TokenException(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class UserInfoException
		extends OpenIdConnectServiceException {

		public UserInfoException(String msg) {
			super(msg);
		}

		public UserInfoException(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

	public static class UserMappingException
		extends OpenIdConnectServiceException {

		public UserMappingException(String msg) {
			super(msg);
		}

		public UserMappingException(String msg, Throwable throwable) {
			super(msg, throwable);
		}

	}

}