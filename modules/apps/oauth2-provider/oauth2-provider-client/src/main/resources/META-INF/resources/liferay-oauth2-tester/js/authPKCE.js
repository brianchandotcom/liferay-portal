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

/*
 * PKCE Authorization Code Grand Flow
 * Step 1: Obtain Authorization Code from Liferay Configured Application with ClientID (Callback URL must be this application)
 */
function authorizePKCE() {
	if ($('#authFormPKCE').valid()) {
		const clientId = $('#clientIdPCKE').val();
		const liferayUrl = $('#liferayUrlPKCE').val();
		const codeChallenge = $('#convertedCodeChallenge').val();

		const redirectUri = encodeURIComponent(
			window.location.href +
				'?url=' +
				liferayUrl +
				'&client_id=' +
				clientId
		);
		window.location.href =
			liferayUrl +
			$('#authorizeUrlPKCE').val() +
			'?client_id={0}&response_type=code&redirect_uri={1}&code_challenge={2}'
				.replace('{0}', clientId)
				.replace('{1}', redirectUri)
				.replace('{2}', codeChallenge);
	}
}

/*
 * Generate random string as Code Challenge
 */
function generateCodeChallenge() {
	const length = 12;
	let randomString = '';
	const dictionary =
		'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
	for (let i = 0; i < length; i++) {
		randomString += dictionary.charAt(
			Math.floor(Math.random() * dictionary.length)
		);
	}
	$('#codeChallenge').val(randomString);
	getEncodedCodeChallenge();
}

/*
 * Encode Code Challenge as indicated at PKCE specification: BaseURLEncode(SHA256(code-challenge))
 */
function getEncodedCodeChallenge() {
	CryptoJS.enc.Base64URL = {
		_map:
			'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_',
		parse: CryptoJS.enc.Base64.parse,
		stringify: CryptoJS.enc.Base64.stringify,
	};

	const code = CryptoJS.enc.Utf8.parse($('#codeChallenge').val());
	const codeSHA256 = CryptoJS.SHA256(code);

	$('#convertedCodeChallenge').val(
		CryptoJS.enc.Base64URL.stringify(codeSHA256)
	);

	$('#getAuthorizationPCKE').prop('disabled', false);
}
