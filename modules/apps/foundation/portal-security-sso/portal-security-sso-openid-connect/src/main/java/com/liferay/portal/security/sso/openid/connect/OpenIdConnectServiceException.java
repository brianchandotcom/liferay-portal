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

	public static class AuthenticationErrorException
		extends OpenIdConnectServiceException {

		public AuthenticationErrorException(String msg) {
			super(msg);
		}

		public AuthenticationErrorException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class MissingClientInformationException
		extends OpenIdConnectServiceException {

		public MissingClientInformationException(String msg) {
			super(msg);
		}

		public MissingClientInformationException(String msg, Throwable cause) {
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

	public static class TokenErrorException
		extends OpenIdConnectServiceException {

		public TokenErrorException(String msg) {
			super(msg);
		}

		public TokenErrorException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class UserInfoErrorException
		extends OpenIdConnectServiceException {

		public UserInfoErrorException(String msg) {
			super(msg);
		}

		public UserInfoErrorException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class UserInfoMissingException
		extends OpenIdConnectServiceException {

		public UserInfoMissingException(String msg) {
			super(msg);
		}

		public UserInfoMissingException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

}