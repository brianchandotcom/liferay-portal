/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.authorization.oauth2;

import com.liferay.document.library.repository.authorization.capability.AuthorizationException;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.function.Function;

/**
 * @author Adolfo Pérez
 */
public class OAuth2AuthorizationException extends AuthorizationException {

	public static OAuth2AuthorizationException getErrorException(
		String error, String description) {

		Function<String, OAuth2AuthorizationException> function =
			_functions.getOrDefault(error, OAuth2AuthorizationException::new);

		return function.apply(description);
	}

	public static class AccessDenied extends OAuth2AuthorizationException {

		public AccessDenied(String description) {
			super(
				String.format(
					"The client is not authorized to request an " +
						"authorization code using method: %s",
					description));
		}

	}

	public static class InvalidRequest extends OAuth2AuthorizationException {

		public InvalidRequest(String description) {
			super(
				String.format(
					"The request is missing a required parameter, includes " +
						"an invalid parameter value, includes a parameter " +
							"more than once, or is otherwise malformed: %s",
					description));
		}

	}

	public static class InvalidScope extends OAuth2AuthorizationException {

		public InvalidScope(String description) {
			super(
				String.format(
					"The requested scope is invalid, unknown, or malformed: %s",
					description));
		}

	}

	public static class InvalidState extends OAuth2AuthorizationException {

		public InvalidState(String state) {
			super(
				String.format(
					"The resource owner returned an invalid state value: %s",
					state));
		}

	}

	public static class ServerError extends OAuth2AuthorizationException {

		public ServerError(String description) {
			super(
				String.format(
					"The authorization server encountered an unexpected " +
						"condition that prevented it from fulfilling the " +
							"request: %s",
					description));
		}

	}

	public static class TemporarilyUnavailable
		extends OAuth2AuthorizationException {

		public TemporarilyUnavailable(String description) {
			super(
				String.format(
					"The authorization server is currently unable to handle " +
						"the request due to a temporary overloading or " +
							"maintenance of the server: %s",
					description));
		}

	}

	public static class UnauthorizedClient
		extends OAuth2AuthorizationException {

		public UnauthorizedClient(String description) {
			super(
				String.format(
					"The resource owner or authorization server denied the " +
						"request: %s",
					description));
		}

	}

	public static class UnsupportedResponseType
		extends OAuth2AuthorizationException {

		public UnsupportedResponseType(String description) {
			super(
				String.format(
					"The authorization server does not support obtaining an " +
						"authorization code using this method: %s",
					description));
		}

	}

	protected OAuth2AuthorizationException() {
	}

	protected OAuth2AuthorizationException(String msg) {
		super(msg);
	}

	protected OAuth2AuthorizationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	protected OAuth2AuthorizationException(Throwable throwable) {
		super(throwable);
	}

	private static final Map
		<String, Function<String, OAuth2AuthorizationException>> _functions =
			HashMapBuilder.
				<String, Function<String, OAuth2AuthorizationException>>put(
					"access_denied",
					OAuth2AuthorizationException.AccessDenied::new
				).put(
					"invalid_request",
					OAuth2AuthorizationException.InvalidRequest::new
				).put(
					"invalid_scope",
					OAuth2AuthorizationException.InvalidScope::new
				).put(
					"server_error",
					OAuth2AuthorizationException.ServerError::new
				).put(
					"temporarily_unavailable",
					OAuth2AuthorizationException.TemporarilyUnavailable::new
				).put(
					"unauthorized_client",
					OAuth2AuthorizationException.UnauthorizedClient::new
				).put(
					"unsupported_response_type",
					OAuth2AuthorizationException.UnsupportedResponseType::new
				).build();

}