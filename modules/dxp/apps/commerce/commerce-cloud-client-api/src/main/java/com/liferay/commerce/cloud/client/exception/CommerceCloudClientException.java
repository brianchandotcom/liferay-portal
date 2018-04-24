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

package com.liferay.commerce.cloud.client.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CommerceCloudClientException extends PortalException {

	public CommerceCloudClientException() {
	}

	public CommerceCloudClientException(String msg) {
		super(msg);
	}

	public CommerceCloudClientException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CommerceCloudClientException(Throwable cause) {
		super(cause);
	}

	public static class MustBeConfigured extends CommerceCloudClientException {

		public MustBeConfigured(String message) {
			super(message);
		}

	}

	public static class ServerError extends CommerceCloudClientException {

		public ServerError(String url, IOException ioe) {
			super("Request " + url + " failed", ioe);

			_url = url;
		}

		public String getUrl() {
			return _url;
		}

		private final String _url;

	}

	public static class ServerResponseError
		extends CommerceCloudClientException {

		public ServerResponseError(
			String url, int responseCode, String responseBody) {

			super(
				StringBundler.concat(
					"Request ", url, " failed: ", String.valueOf(responseCode),
					StringPool.SPACE, responseBody));

			_url = url;
			_responseCode = responseCode;
			_responseBody = responseBody;
		}

		public String getResponseBody() {
			return _responseBody;
		}

		public int getResponseCode() {
			return _responseCode;
		}

		public String getUrl() {
			return _url;
		}

		private final String _responseBody;
		private final int _responseCode;
		private final String _url;

	}

}