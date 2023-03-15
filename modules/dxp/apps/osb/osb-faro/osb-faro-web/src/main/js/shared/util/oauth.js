import * as API from 'shared/api';
import Promise from 'metal-promise';
import {Routes} from 'shared/util/router';

export const OAUTH_CALLBACK_URL = `${location.origin}${Routes.OAUTH_RECEIVE}`;

export const OAUTH_ERROR_CODES = [
	'invalid_client',
	'invalid_grant',
	'invalid_request',
	'unauthorized_client',
	'unsupported_grant_type'
];

/**
 * OAuth 1 Flow
 * Represents the TempCredentials to be passed to the backend, who
 * will perform the last step of the OAuth flow during the creation
 * of the data source.
 * @typedef {Object} TempCredentials
 * @property {string!} oAuthToken - The oauth_token.
 * @property {string!} oAuthTokenSecret - The oauth_token_secret.
 * @property {string!} oAuthVerifier - The oauth_verifier.
 */

/**
 * The timeout before the promise rejects.
 * This is set to 15 minutes, which is the default OAuth
 * auth code expiration.
 */
const DEFAULT_TIMEOUT = 15 * 60 * 1000;

/**
 * A constant to help disambiguate between OAuth1 and OAuth2 flows and
 * also between other messages dispatched on the window.
 * @property {string} AC_RECEIVE_AUTH_CODE - Event type for authorization code received in OAuth2 flow
 * @property {string} AC_RECEIVE_OAUTH_TOKEN - Event type for credentials received in OAuth1 flow
 */
export const EVENT_TYPES = {
	AC_RECEIVE_AUTH_CODE: 'AC_RECEIVE_AUTH_CODE',
	AC_RECEIVE_OAUTH_TOKEN: 'AC_RECEIVE_OAUTH_TOKEN'
};

/**
 * The types of errors that `getTempCredentials` can reject with.
 */
export const ERROR_TYPES = {
	AC_RECEIVE_CALLBACK_ERROR: 'AC_RECEIVE_CALLBACK_ERROR',
	OTHER: 'OTHER',
	TIMEOUT: 'TIMEOUT',
	WINDOW_BLOCKED: 'WINDOW_BLOCKED',
	WINDOW_CLOSED: 'WINDOW_CLOSED'
};

function createError(message, type) {
	const error = new Error(message);
	error.type = type;

	return error;
}

/**
 * OAuth 2 Flow
 * Represents the authorization code received after
 * the user allowed access in the login window.
 *
 * @typedef {string} AuthorizationCode
 */

/**
 * Represents only part of the OAuth credentials that are returned
 * by certain portions of this process. It is eventually joined with
 * the `tokenSecret` to form the {@link TempCredentials}.
 * @typedef {Object} PartialCredentials
 * @property {string!} oAuthToken - The oauth_token.
 * @property {string!} oAuthVerifier - The oauth_verifier.
 */

/**
 * Check if the error string matches an OAuth type error code.
 * @param {string} - The error message to check.
 */
export function isOAuthErrorString(error) {
	return OAUTH_ERROR_CODES.includes(error);
}

/**
 * Opens the OAuth Authorization window and resolves with
 * the token for OAuth1 or authorization code for OAuth2 flows.
 * @param {Object} options - The options for the request.
 * @param {string!} options.url - The OAuth provider URL.
 * @param {Window} options.authWindow - The Auth window.
 * @param {number} options.timeout - The timeout before the request fails.
 * @returns {Promise.<PartialCredentials>|Promise.<AuthorizationCode>}
 */
export function openOAuthWindow({
	authWindow,
	timeout = DEFAULT_TIMEOUT,
	url
} = {}) {
	authWindow.location.href = url;

	authWindow.focus();

	return new Promise((resolve, reject) => {
		function handleMessage(event) {
			if (event.origin === location.origin) {
				cleanUp();

				const {code, message, token, verifier} = event.data;

				switch (event.data.type) {
					case EVENT_TYPES.AC_RECEIVE_OAUTH_TOKEN:
						resolve({token, verifier});
						break;
					case EVENT_TYPES.AC_RECEIVE_AUTH_CODE:
						resolve({code});
						break;
					case ERROR_TYPES.AC_RECEIVE_CALLBACK_ERROR:
						reject(
							createError(
								message,
								ERROR_TYPES.AC_RECEIVE_CALLBACK_ERROR
							)
						);
						break;
					default:
						break;
				}
			}
		}

		function cleanUp() {
			clearInterval(intervalId);
			clearTimeout(timeoutId);
			removeEventListener('message', handleMessage);

			authWindow.close();
		}

		const intervalId = setInterval(() => {
			if (authWindow.closed) {
				cleanUp();

				reject(
					createError(
						'OAuth window closed.',
						ERROR_TYPES.WINDOW_CLOSED
					)
				);
			}
		}, 250);

		const timeoutId = setTimeout(() => {
			cleanUp();

			reject(
				createError('OAuth login flow timed out.', ERROR_TYPES.TIMEOUT)
			);
		}, timeout);

		addEventListener('message', handleMessage, false);
	});
}

/**
 * OAuth 2 Flow
 * Emits the authorization code as an event on the window.
 * Should be received by openOAuthWindow.
 * @param {AuthorizationCode} code - Code to exchange for an access token
 */
export function emitAuthCode({code}) {
	if (opener) {
		opener.postMessage(
			{code, type: EVENT_TYPES.AC_RECEIVE_AUTH_CODE},
			location.origin
		);
	}
}

/**
 * Emits error received in callback url as an event on the window.
 * Should be received by openOAuthWindow.
 * @param {Object} error - The Error Object.
 * @param {string} error.message - The Error message.
 */
export function emitError({message}) {
	if (opener) {
		opener.postMessage(
			{
				message,
				type: ERROR_TYPES.AC_RECEIVE_CALLBACK_ERROR
			},
			location.origin
		);
	}
}
/**
 * OAuth 1 Flow
 * Emits the token as an event on the window. Should be
 * received by `getTempCredentials`.
 * @param {PartialCredentials} credentials - The temporary credentials.
 */
export function emitToken({token, verifier}) {
	if (opener) {
		opener.postMessage(
			{token, type: EVENT_TYPES.AC_RECEIVE_OAUTH_TOKEN, verifier},
			location.origin
		);
	}
}

/**
 * Returns a promise that resolves with the OAuth token
 * after the login flow has completed.
 *
 * Note the `authWindow` parameter, which needs to be passed in. Most browsers
 * will block a pop-up unless it is triggered directly by a user action. Further,
 * the call to `open` should be as close to the top of the call stack as possible,
 * otherwise it will still be blocked. This means that even if we are using a Form
 * component to get the values for this function, we will not be able to call this
 * from the Form component's `onSubmit` callback as it will be too deep in the call
 * stack to pass the browser's blocking. Prefer using an `onClick` directly on the
 * submit button instead.
 *
 * See the `OAuthKit` page as an example.
 *
 * @param {Object} options - The options for the request.
 * @param {string!} options.baseUrl - The baseUrl of the provider.
 * @param {string!} options.consumerKey - The OAuth consumer key.
 * @param {string!} options.consumerSecret - The OAuth consumer secret.
 * @param {Window} options.authWindow - The window to display the Authorization url.
 * @param {number} options.timeout - The timeout before the request fails.
 * @param {string} options.type - The type of dataSource, e.g., 'LIFERAY'.
 * @param {string} options.callbackUrl - The callback uri.
 * @returns {Promise.<TempCredentials>}
 */
export function getTempCredentials({
	authWindow,
	baseUrl,
	callbackUrl = OAUTH_CALLBACK_URL,
	consumerKey,
	consumerSecret,
	groupId,
	timeout,
	type
}) {
	if (!authWindow) {
		return Promise.reject(
			createError(
				`Failed to open window for "${baseUrl}".`,
				ERROR_TYPES.WINDOW_BLOCKED
			)
		);
	}

	return API.dataSource
		.fetchOAuthUrl({
			baseUrl,
			callbackUrl,
			consumerKey,
			consumerSecret,
			groupId,
			type
		})
		.catch(() => {
			if (authWindow) {
				authWindow.close();
			}

			throw createError(
				'Failed to fetch Authorize url.',
				ERROR_TYPES.OTHER
			);
		})
		.then(response =>
			openOAuthWindow({
				authWindow,
				timeout,
				url: response.oAuthAuthorizationURL
			}).then(({code, token, verifier}) => ({
				...response,
				oAuthCallbackURL: callbackUrl,
				oAuthCode: code,
				oAuthToken: token,
				oAuthVerifier: verifier
			}))
		);
}

/**
 * Get error message to display for OAuth popup window errors.
 * @param {Object} error - The Error Object.
 * @param {string} error.type - The type of error. Should be one of the ERROR_TYPES.
 * @param {string} error.message - The error message.
 * @returns {string} The error message to display.
 */
export function getOAuthWindowErrorMessage({message, type}) {
	switch (type) {
		case ERROR_TYPES.AC_RECEIVE_CALLBACK_ERROR:
			return message;
		case ERROR_TYPES.TIMEOUT:
			return Liferay.Language.get(
				'you-have-been-timed-out.-please-retry-to-complete-authorization'
			);
		case ERROR_TYPES.WINDOW_BLOCKED:
			return Liferay.Language.get(
				'pop-up-window-blocked,-please-turn-off-any-pop-up-blockers-and-retry-authorization'
			);
		case ERROR_TYPES.WINDOW_CLOSED:
			return Liferay.Language.get(
				'window-was-closed-without-completing-authorization.-please-retry-authorization'
			);
		default:
			return Liferay.Language.get(
				'unknown-error.-please-retry-authorization'
			);
	}
}
