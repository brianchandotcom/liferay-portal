/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import * as base64Url from './base64url';

/**
 * Creates a credential
 *
 * @param {Object} options A PublicKeyCredentialCreationOptions object,
 *	where binary values are base64url encoded strings instead of byte arrays
 *
 * @returns {Promise<Credential|null>}
 */
export function create(options) {
	return navigator.credentials.create({
		publicKey: decodeCredentialCreationOptions(options),
	});
}

/**
 * Convert a PublicKeyCredential into an Object with base64 encoded binary values
 *
 * @param {PublicKeyCredential} credential The PublicKeyCredential to convert
 *
 * @returns {Object}
 */
export function credentialToObject(credential) {
	let clientExtensionResults = {};

	try {
		clientExtensionResults = credential.getClientExtensionResults();
	}
	catch (error) {
		console.error('getClientExtensionResults failed', error);
	}

	const {id, response, type} = credential;

	if (response.attestationObject) {
		return {
			clientExtensionResults,
			id,
			response: {
				attestationObject: base64Url.fromByteArray(
					response.attestationObject
				),
				clientDataJSON: base64Url.fromByteArray(
					response.clientDataJSON
				),
			},
			type,
		};
	}
	else {
		return {
			clientExtensionResults,
			id,
			response: {
				authenticatorData: base64Url.fromByteArray(
					response.authenticatorData
				),
				clientDataJSON: base64Url.fromByteArray(
					response.clientDataJSON
				),
				signature: base64Url.fromByteArray(response.signature),
				userHandle:
					response.userHandle &&
					base64Url.fromByteArray(response.userHandle),
			},
			type,
		};
	}
}

function decodeCredentialCreationOptions(request) {
	const excludeCredentials = request.excludeCredentials.map((credential) => ({
		...credential,
		id: base64Url.toByteArray(credential.id),
	}));

	return {
		...request,
		attestation: 'direct',
		challenge: base64Url.toByteArray(request.challenge),
		excludeCredentials,
		user: {
			...request.user,
			id: base64Url.toByteArray(request.user.id),
		},
	};
}

function decodeCredentialRequestOptions(request) {
	const allowCredentials =
		request.allowCredentials &&
		request.allowCredentials.map((credential) => ({
			...credential,
			id: base64Url.toByteArray(credential.id),
		}));

	return {
		...request,
		allowCredentials,
		challenge: base64Url.toByteArray(request.challenge),
	};
}

/**
 * Returns a Promise to a single Credential instance that matches the provided parameters.
 * If no match is found the Promise will resolve to null.
 *
 * @param {CredentialRequestOptions} options PublicKeyCredentialRequestOptions object
 *
 * @returns {Promise<Credential|null>}
 */
export function get(options) {
	return navigator.credentials.get({
		publicKey: decodeCredentialRequestOptions(options),
	});
}
