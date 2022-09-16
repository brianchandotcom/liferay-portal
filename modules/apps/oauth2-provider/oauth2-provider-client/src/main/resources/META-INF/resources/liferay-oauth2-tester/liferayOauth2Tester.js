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

const liferayUrl = document.getElementById('liferayUrl');
const clientId = document.getElementById('clientId');
const clientSecretOrCodeChallenge = document.getElementById(
	'clientSecretOrCodeChallenge'
);
const accessToken = document.getElementById('accessToken');
const refreshToken = document.getElementById('refreshToken');
const resultTextArea = document.getElementById('resultTextArea');
let pkceMode = false;
let parameterCount = 1;

/**
 * Generates the URL the user will be redirected to for OAuth2 authorization.
 * @param {string} liferayUrl - The base URL for the Liferay Portal.
 * @param {string} authorizeUrl - The URL path of the Portal where the authorization can happen.
 * @param {string} clientId - OAuth2 client ID of the application.
 * @param {string} [convertedCodeChallenge] - Base64 encoded SHA256 code challenge. Required only for PKCE authorization flow.
 * @returns {string} OAuth2 authorization url.
 */
const authorizationUri = (
	liferayUrl,
	authorizeUrl,
	clientId,
	convertedCodeChallenge = ''
) => {
	let uri =
		liferayUrl +
		authorizeUrl +
		'?client_id={0}&response_type=code&redirect_uri={1}'
			.replace('{0}', clientId)
			.replace('{1}', redirectUri(true));

	if (convertedCodeChallenge) {
		uri += `&code_challenge=${convertedCodeChallenge}`;
	}

	return uri;
};

/**
 * Collects all non-empty name and value pairs from parameters.
 * @returns {Object} - Name and value pairs of parameters.
 */
const paramList = () => {
	const data = {};
	const numberOfParams = document.querySelectorAll(
		`#parameters input[id^="paramName"]`
	).length;
	for (let i = 0; i < numberOfParams; i++) {
		const name = document.getElementById(`paramName${i}`).value;
		const value = document.getElementById(`paramValue${i}`).value;
		if (name.trim() && value.trim()) {
			data[name] = value;
		}
	}

	return data;
};

/**
 * Generates a random string for Code Challenge.
 * @param {number} [length=12] - Length of the random generated string.
 * @returns {string} A random generated string.
 */
const random = (length = 12) => {
	const chars =
		'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
	let str = '';
	for (let i = 0; i < length; i++) {
		str += chars.charAt(Math.floor(Math.random() * chars.length));
	}

	return str;
};

/**
 * @param {boolean} [encode=false] - If true the result will be URL encoded.
 * @returns {string} The necessary redirect URI.
 */
const redirectUri = (encode = false) => {
	const url =
		location.protocol +
		'//' +
		location.host +
		location.pathname +
		`${pkceMode ? '?pkce=1&' : '?'}` +
		`url=${liferayUrl.value}` +
		`&client_id=${clientId.value}`;

	return encode ? encodeURIComponent(url) : url;
};

/**
 * @returns {string} The URL for token requests.
 */
const tokenUrl = () => {
	const tokenUrl = document.getElementById('tokenUrl').value;

	return liferayUrl.value + tokenUrl;
};

/**
 * Changes the color of an accordion item to green and closes it.
 * @param {('authTab'|'tokenTab'|'requestTab')} name - Name of the accordion item.
 */
function completeAccordionItem(name) {
	document.getElementById(`${name}Button`).classList.add('text-bg-success');
	new bootstrap.Collapse(document.getElementById(`${name}Collapse`), {
		toggle: false,
	}).hide();
}

/**
 * By default, all accordion items are disabled. This function enables a given accordion item and opens it.
 * @param {('authTab'|'tokenTab'|'requestTab')} name - Name of the accordion item.
 */
function enableAndOpenAccordionItem(name) {
	document.getElementById(`${name}Button`).disabled = false;
	new bootstrap.Collapse(document.getElementById(`${name}Collapse`), {
		toggle: false,
	}).show();
}

/**
 * Generates a URL encoded string from an object to be usable as x-www-form-urlencoded body.
 * @param {Object} data - Input data.
 * @returns {string} URL encoded string of concatenated keys and values.
 */
function encodeFormData(data) {
	return Object.keys(data)
		.map(
			(key) =>
				encodeURIComponent(key) + '=' + encodeURIComponent(data[key])
		)
		.join('&');
}

/**
 * Generates a new column for the parameter section.
 * @param {('Name'|'Value')} type - Type of the input field.
 * @param {number} id - Unique ID for that parameter.
 * @returns {string} HTML code for a new parameter column.
 */
function generateNewParamCol(type, id) {
	return `<div class="col-md-6"><input type="text" id="param${type}${id}" placeholder="${type}" class="form-control form-control-sm" /></div>`;
}

/**
 * Searches for a given parameter in the URL parameters and returns its value if found.
 * @param {string} parameterName - Name of the parameter to search for.
 * @returns {string|null} The value of the searched parameter or null if not found.
 */
function getUrlParameter(parameterName) {
	const pageUrl = window.location.search.substring(1);
	const variablesUrl = pageUrl.split('&');
	for (let i = 0; i < variablesUrl.length; i++) {
		const parameter = variablesUrl[i].split('=');
		if (parameter[0] === parameterName) {
			return parameter[1];
		}
	}

	return null;
}

/**
 * Sends an HTTP request to a given resource.
 * @param {string} url - The URL of the resource which is being fetched.
 * @param {('GET'|'POST'|'PUT'|'DELETE')} method - HTTP method.
 * @param {('application/json'|'application/x-www-form-urlencoded')} contentType - Representation header which is used to indicate the original media type of the resource.
 * @param {Object} body - Body of the request.
 * @param {string} authorizationToken - If provided the request will be sent with Authorization header.
 * @returns {Promise<any>} The response of the HTTP request in JSON format if it was successful.
 */
async function sendHttpRequest(
	url,
	method,
	contentType,
	body,
	authorizationToken = ''
) {
	const headers = {
		'Accept': 'application/json',
		'Content-Type': contentType,
	};
	if (authorizationToken) {
		headers.Authorization = `Bearer ${authorizationToken}`;
	}

	/* Encode the body based on the content type */
	if (contentType === 'application/x-www-form-urlencoded') {
		body = encodeFormData(body);
	}
	else {
		body = JSON.stringify(body);
	}

	/* If the method is GET or DELETE clear the body. */
	if (['GET', 'DELETE'].indexOf(method) !== -1) {
		body = null;
	}

	try {
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch(url, {body, headers, method});
		if (response.ok) {
			return response.json();
		}
		else {
			if (response.statusText) {
				alert(`Error: ${response.statusText}`);
			}
			else {
				alert(`HTTP ${response.status}`);
			}
		}
	}
	catch (error) {
		alert(error);
	}
}

/**
 * Generates the body or a token introspect HTTP request and sends it.
 * @param {('access_token'|'refresh_token')} tokenType - Type of the token.
 * @param {string} token - The token to introspect.
 * @returns {Promise<any>} The response of the HTTP request in JSON format if it was successful.
 */
async function sendIntrospectRequest(tokenType, token) {
	const body = {
		client_id: clientId.value,
		token,
		token_type_hint: tokenType,
	};

	if (!pkceMode) {
		body.client_secret = clientSecretOrCodeChallenge.value;
	}

	return await sendHttpRequest(
		`${liferayUrl.value}/o/oauth2/introspect`,
		'POST',
		'application/x-www-form-urlencoded',
		body
	);
}

/*
 * Submit listener for authForm.
 * Redirects user to the Liferay OAuth2 application authorization page.
 */
document.forms['authForm'].addEventListener(
	'submit',
	(event) => {
		const authorizeUrl = document.getElementById('authorizeUrl');
		event.preventDefault();
		if (pkceMode) {
			const convertedCodeChallenge = document.getElementById(
				'convertedCodeChallenge'
			).value;
			window.location.href = authorizationUri(
				liferayUrl.value,
				authorizeUrl.value,
				clientId.value,
				convertedCodeChallenge
			);
		}
		else {
			window.location.href = authorizationUri(
				liferayUrl.value,
				authorizeUrl.value,
				clientId.value
			);
		}
	},
	false
);

/*
 * Submit listener for tokenForm.
 * Sends an HTTP request with the provided data.
 * If successful it sets the access and refresh tokens, closes this step and opens the next one.
 * If the user selected that they already have tokens we skip the request sending,
 * just wait for the user to input the client secret and go to the next step.
 */
document.forms['tokenForm'].addEventListener(
	'submit',
	async (event) => {
		event.preventDefault();

		/* Selected 'Already have tokens' */
		if (document.getElementById('getToken').innerHTML === 'Next') {
			completeAccordionItem('tokenTab');
			enableAndOpenAccordionItem('requestTab');
		}
		else {
			const authCode = document.getElementById('authCode').value;

			const body = {
				client_id: clientId.value,
				code: authCode,
				grant_type: 'authorization_code',
				redirect_uri: redirectUri(),
			};

			if (pkceMode) {
				body.code_verifier = clientSecretOrCodeChallenge.value;
			}
			else {
				body.client_secret = clientSecretOrCodeChallenge.value;
			}
			const response = await sendHttpRequest(
				tokenUrl(),
				'POST',
				'application/x-www-form-urlencoded',
				body
			);

			if (response) {
				completeAccordionItem('tokenTab');
				enableAndOpenAccordionItem('requestTab');
				accessToken.value = response.access_token;
				refreshToken.value = response.refresh_token;
			}
		}
	},
	false
);

/*
 * Submit listener for requestForm.
 * Sends an HTTP request with the provided data.
 */
document.forms['requestForm'].addEventListener(
	'submit',
	async (event) => {
		event.preventDefault();

		const url = document.getElementById('requestUrl').value;
		const methodType = document.getElementById('methodType').value;
		const contentType = document.getElementById('contentType').value;

		if (accessToken.value.trim()) {
			const response = await sendHttpRequest(
				url,
				methodType,
				contentType,
				paramList(),
				accessToken.value.trim()
			);

			if (response) {
				resultTextArea.value = JSON.stringify(response, null, 2);
				resultTextArea.innerHTML = JSON.stringify(response);
			}
		}
		else {
			alert('Access token is missing!');
		}
	},
	false
);

/*
 * If methodType in requestTab is getting changed to GET or DELETE hide the parameters section.
 */
document.getElementById('methodType').addEventListener('change', (event) => {
	if (['GET', 'DELETE'].indexOf(event.target.value) !== -1) {
		document.getElementById('parameters').style.display = 'none';
		document.getElementById('requestForm').classList.add('gy-4');
	}
	else {
		document.getElementById('parameters').style.display = '';
		document.getElementById('requestForm').classList.remove('gy-4');
	}
});

/*
 * Insert new lines for parameters when clicking the button.
 */
document.getElementById('addParameter').addEventListener('click', (event) => {
	event.preventDefault();
	event.target.parentElement.insertAdjacentHTML(
		'beforebegin',
		generateNewParamCol('Name', parameterCount)
	);
	event.target.parentElement.insertAdjacentHTML(
		'beforebegin',
		generateNewParamCol('Value', parameterCount)
	);
	parameterCount++;
});

/*
 * If the user already have tokens we skip several steps.
 * In both cases Client ID is required in authForm but no redirect will happen.
 * PKCE flow will continue on requestForm.
 * Basic flow will require the user to provide the Client Secret in tokenForm.
 * Note: In PKCE the user will not be able to be open the tokenTab.
 */
document
	.getElementById('alreadyHaveTokens')
	.addEventListener('click', (event) => {
		event.preventDefault();
		if (document.forms['authForm'].reportValidity()) {
			completeAccordionItem('authTab');
			if (pkceMode) {
				completeAccordionItem('tokenTab');
				enableAndOpenAccordionItem('requestTab');
			}
			else {
				enableAndOpenAccordionItem('tokenTab');
				document.getElementById('getToken').innerHTML = 'Next';
				document.getElementById('tokenLiferayUrl').value =
					liferayUrl.value;
				document.getElementById('tokenClientId').value = clientId.value;
			}
		}
	});

/*
 * Change between Basic and PKCE Authorization Code Grant Flow.
 * PKCE mode is enabled if 'pkce=1' is present in the URL parameters.
 */
document
	.getElementById('changeModeButton')
	.addEventListener('click', (event) => {
		event.preventDefault();
		if (!pkceMode) {
			window.location.replace(
				`${window.location.href.split('?')[0]}?pkce=1`
			);
		}
		else {
			window.location.replace(window.location.pathname);
		}
	});

/*
 * Send introspect access token request.
 */
document
	.getElementById('introspectAccessTokenButton')
	.addEventListener('click', async (event) => {
		event.preventDefault();
		if (accessToken.value.trim()) {
			const response = await sendIntrospectRequest(
				'access_token',
				accessToken.value.trim()
			);
			if (response) {
				resultTextArea.value = JSON.stringify(response, null, 2);
				resultTextArea.innerHTML = JSON.stringify(response);
			}
		}
		else {
			alert('Access token is missing!');
		}
	});

/*
 * Send introspect refresh token request.
 */
document
	.getElementById('introspectRefreshTokenButton')
	.addEventListener('click', async (event) => {
		event.preventDefault();
		if (refreshToken.value.trim()) {
			const response = await sendIntrospectRequest(
				'refresh_token',
				refreshToken.value.trim()
			);
			if (response) {
				resultTextArea.value = JSON.stringify(response, null, 2);
				resultTextArea.innerHTML = JSON.stringify(response);
			}
		}
		else {
			alert('Refresh token is missing!');
		}
	});

/*
 * Generate code challenge.
 */
document
	.getElementById('generateCodeChallenge')
	.addEventListener('click', (event) => {
		event.preventDefault();
		const codeChallenge = (document.getElementById(
			'codeChallenge'
		).value = random());
		const codeSHA256 = CryptoJS.SHA256(
			CryptoJS.enc.Utf8.parse(codeChallenge)
		);
		document.getElementById(
			'convertedCodeChallenge'
		).value = CryptoJS.enc.Base64url.stringify(codeSHA256);
		document.getElementById('getAuthorizationCode').disabled = false;
	});

/*
 * Refresh the access token.
 */
document
	.getElementById('refreshTokenButton')
	.addEventListener('click', async (event) => {
		event.preventDefault();
		if (refreshToken.value.trim()) {
			const body = {
				client_id: clientId.value,
				grant_type: 'refresh_token',
				refresh_token: refreshToken.value.trim(),
			};

			if (!pkceMode) {
				body.client_secret = clientSecretOrCodeChallenge.value;
			}

			const response = await sendHttpRequest(
				tokenUrl(),
				'POST',
				'application/x-www-form-urlencoded',
				body
			);

			if (response) {
				accessToken.value = response.access_token;
				refreshToken.value = response.refresh_token;
			}
		}
		else {
			alert('Refresh token is missing!');
		}
	});

/*
 * Reset all the forms.
 */
document.getElementById('resetButton').addEventListener('click', (event) => {
	event.preventDefault();
	let url = window.location.href.split('?')[0];
	url += pkceMode ? '?pkce=1' : '';
	window.location.replace(url);
});

window.addEventListener('load', () => {

	/* Change the Callback URL in the configuration helper to the current one. */
	document.getElementById('callbackUri').innerHTML =
		location.origin + location.pathname + '?';

	/* Check if we got redirected back here with an authorization code. */
	if (getUrlParameter('code')) {
		document.getElementById(
			'tokenLiferayUrl'
		).value = liferayUrl.value = decodeURIComponent(getUrlParameter('url'));
		document.getElementById(
			'tokenClientId'
		).value = clientId.value = getUrlParameter('client_id');
		document.getElementById('authCode').value = getUrlParameter('code');
		completeAccordionItem('authTab');
		enableAndOpenAccordionItem('tokenTab');
	}
	else {
		enableAndOpenAccordionItem('authTab');
	}

	/* Switch to PKCE mode. */
	if (getUrlParameter('pkce')) {
		pkceMode = true;
		document.title = 'Liferay - OAuth2 Tester - PKCE';
		document.getElementById('title').innerHTML =
			'Liferay OAuth2 Tester - PKCE';
		document.getElementById('callbackUri').innerHTML =
			document.getElementById('callbackUri').innerHTML + 'pkce=1&';
		document.getElementById('changeModeButton').innerHTML =
			'Use Basic Authorization Code Grant Flow';
		document.getElementById('getAuthorizationCode').disabled = true;
		document.getElementById('generateCodeChallenge').style.display = '';
		document.getElementById('codeChallengeDiv').style.display = '';
		document.getElementById('convertedCodeChallengeDiv').style.display = '';
		document
			.getElementById('clientIdDiv')
			.classList.replace('col-12', 'col-md-3');
		document.getElementById('tokenTabButton').innerHTML =
			'2. Exchange code for Access Token';
		document.getElementById('clientSecretOrCodeChallengeLabel').innerHTML =
			'Code Challenge:';
	}
});
