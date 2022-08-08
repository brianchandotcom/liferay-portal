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

// @ts-nocheck

import pkceChallenge from 'pkce-challenge';

class OAuth2Client {
	authorizeURL: any;
	clientId: any;
	encodedRedirectURL: string;
	homePageURL: any;
	redirectURIs: any[];
	tokenURL: any;

	static FromParameters({
		clientId,
		homePageURL,

		// @ts-ignore

		authorizeURL = Liferay.OAuth2.getAuthorizeURL(),

		// @ts-ignore

		redirectURIs = [Liferay.OAuth2.getBuiltInRedirectURL()],

		// @ts-ignore

		tokenURL = Liferay.OAuth2.getTokenURL(),
	}) {
		const webClient = new OAuth2Client();

		webClient.authorizeURL = authorizeURL;
		webClient.clientId = clientId;
		webClient.encodedRedirectURL = encodeURIComponent(redirectURIs[0]);
		webClient.homePageURL = homePageURL;
		webClient.redirectURIs = redirectURIs;
		webClient.tokenURL = tokenURL;

		return webClient;
	}

	static FromUserAgentApplication(userAgentApplicationName: string) {

		// @ts-ignore

		const userAgentApplication = Liferay.OAuth2.getUserAgentApplication(
			userAgentApplicationName
		);

		if (!userAgentApplication) {
			throw new Error(
				`No Application User Agent profile found for ${userAgentApplicationName}`
			);
		}

		const webClient = new OAuth2Client();

		// @ts-ignore

		webClient.authorizeURL = Liferay.OAuth2.getAuthorizeURL();
		webClient.clientId = userAgentApplication.clientId;
		webClient.encodedRedirectURL = encodeURIComponent(
			userAgentApplication.redirectURIs[0]
		);
		webClient.homePageURL = userAgentApplication.homePageURL;
		webClient.redirectURIs = userAgentApplication.redirectURIs;

		// @ts-ignore

		webClient.tokenURL = Liferay.OAuth2.getTokenURL();

		return webClient;
	}

	async fetch(url: RequestInfo, options: any = {}): Promise<any> {
		const oauth2Client = this;

		return oauth2Client._fetch(url, options).then((response) => {
			if (response.ok) {
				const contentType = response.headers.get('content-type');
				if (
					contentType &&
					contentType.indexOf('application/json') !== -1
				) {
					return response.json();
				}
				else {
					return Promise.resolve(response);
				}
			}

			return Promise.reject(response);
		});
	}

	_createIframe(): HTMLIFrameElement {
		const ifrm = document.createElement('iframe');
		ifrm.style.display = 'none';
		document.body.appendChild(ifrm);

		return ifrm;
	}

	async _fetch(resource: RequestInfo, options: any = {}): Promise<any> {
		const oauth2Client = this;

		let resourceUrl: string = resource['url'] ? resource['url'] : resource;

		if (
			resourceUrl.includes('//') &&
			!resourceUrl.startsWith(oauth2Client.homePageURL)
		) {
			throw new Error(
				`This client only supports calls to ${oauth2Client.homePageURL}`
			);
		}

		if (!resourceUrl.startsWith(oauth2Client.homePageURL)) {
			if (resourceUrl.startsWith('/')) {
				resourceUrl = resourceUrl.substring(1);
			}

			resourceUrl = `${oauth2Client.homePageURL}/${resourceUrl}`;
		}

		const tokenData = await oauth2Client._getOrRequestToken();

		resource =
			resource instanceof Object
				? {...resource, url: resourceUrl}
				: resourceUrl;

		// This client must avoid using @liferay/portal/no-global-fetch in order
		// to perform OAuth2 token authentication instead
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		return await fetch(resource, {
			headers: {
				'Authorization': `Bearer ${tokenData.access_token}`,
				'Content-Type': 'application/json',
			},
			...options,
		});
	}

	_getOrRequestToken(): Promise<any> {
		const oauth2Client = this;
		const sessionKey = `${oauth2Client.clientId}-${Liferay.authToken}-token`;

		return new Promise((resolve) => {
			const cachedTokenData = sessionStorage.getItem(sessionKey);

			if (cachedTokenData !== null) {
				resolve(JSON.parse(cachedTokenData));

				return;
			}

			resolve(oauth2Client._requestTokenSilently(sessionKey));
		});
	}

	_requestTokenSilently(sessionKey: string): Promise<any> {
		const oauth2Client = this;
		const challenge = pkceChallenge(128);
		const ifrm = oauth2Client._createIframe();

		const promise = new Promise((resolve, reject) => {
			ifrm.contentWindow.addEventListener('message', function abc(event) {
				try {
					if (event.data.error) {
						reject(event.data.error);

						return;
					}
					else if (event.data.code === null) {
						reject();

						return;
					}

					const tokenResponse = oauth2Client._requestToken(
						challenge.code_verifier,
						event.data.code
					);

					resolve(tokenResponse);

					tokenResponse.then((response) =>
						sessionStorage.setItem(
							sessionKey,
							JSON.stringify(response)
						)
					);
				}
				finally {
					ifrm.parentNode.removeChild(ifrm);
				}
			});
		});

		ifrm.src = `${oauth2Client.authorizeURL}?response_type=code&client_id=${oauth2Client.clientId}&redirect_uri=${oauth2Client.encodedRedirectURL}&prompt=none&code_challenge=${challenge.code_challenge}&code_challenge_method=S256`;

		return promise;
	}

	async _requestToken(codeVerifier: string, code: string): Promise<any> {
		const oauth2Client = this;

		// This client must avoid using @liferay/portal/no-global-fetch in order
		// to perform OAuth2 token authentication instead
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch(oauth2Client.tokenURL, {
			body: new URLSearchParams({
				client_id: oauth2Client.clientId,
				code,
				code_verifier: codeVerifier,
				grant_type: 'authorization_code',
				redirect_uri: oauth2Client.redirectURIs[0],
			}),
			cache: 'no-cache',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded',
			},
			method: 'POST',
			mode: 'cors',
		});

		if (response.ok) {
			return response.json();
		}

		return await Promise.reject(response);
	}
}

export default OAuth2Client;
