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