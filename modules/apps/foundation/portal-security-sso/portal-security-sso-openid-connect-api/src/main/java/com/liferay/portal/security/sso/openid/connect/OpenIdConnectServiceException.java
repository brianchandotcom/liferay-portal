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

package com.liferay.portal.security.sso.openid.connect;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Thuong Dinh
 */
public class OpenIdConnectServiceException extends PortalException {

	public OpenIdConnectServiceException(String msg) {
		super(msg);
	}

	public OpenIdConnectServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public static class AuthenticationException
		extends OpenIdConnectServiceException {

		public AuthenticationException(String msg) {
			super(msg);
		}

		public AuthenticationException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class NoOpenIdConnectSessionException
		extends OpenIdConnectServiceException {

		public NoOpenIdConnectSessionException(String msg) {
			super(msg);
		}

		public NoOpenIdConnectSessionException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class ProviderException
		extends OpenIdConnectServiceException {

		public ProviderException(String msg) {
			super(msg);
		}

		public ProviderException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class TokenException extends OpenIdConnectServiceException {

		public TokenException(String msg) {
			super(msg);
		}

		public TokenException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class UserInfoException
		extends OpenIdConnectServiceException {

		public UserInfoException(String msg) {
			super(msg);
		}

		public UserInfoException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class UserMappingException
		extends OpenIdConnectServiceException {

		public UserMappingException(String msg) {
			super(msg);
		}

		public UserMappingException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

}