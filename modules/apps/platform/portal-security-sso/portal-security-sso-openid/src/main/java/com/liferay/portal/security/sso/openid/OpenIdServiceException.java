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

package com.liferay.portal.security.sso.openid;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Michael C. Han
 */
public class OpenIdServiceException extends PortalException {

	public OpenIdServiceException(String msg) {
		super(msg);
	}

	public OpenIdServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public static class AssociationException extends OpenIdServiceException {

		public AssociationException(String msg) {
			super(msg);
		}

		public AssociationException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class ConsumerException extends OpenIdServiceException {

		public ConsumerException(String msg) {
			super(msg);
		}

		public ConsumerException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class DiscoveryException extends OpenIdServiceException {

		public DiscoveryException(String msg) {
			super(msg);
		}

		public DiscoveryException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

	public static class MessageException extends OpenIdServiceException {

		public MessageException(String msg) {
			super(msg);
		}

		public MessageException(String msg, Throwable cause) {
			super(msg, cause);
		}

	}

}