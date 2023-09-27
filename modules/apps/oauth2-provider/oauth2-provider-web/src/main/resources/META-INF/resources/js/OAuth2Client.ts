/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import pkceChallenge from 'pkce-challenge';

interface IOAuth2ClientFromParametersOptions {
	authorizeURL?: string;
	clientId: string;
	homePageURL: string;
	redirectURIs?: Array<string>;
	tokenURL?: string;
}

interface IOAuth2ClientOptions {
	authorizeURL: string;
	clientId: string;
	encodedRedirectURL: string;
	homePageURL: string;
	redirectURIs: Array<string>;
	tokenURL: string;
}

class OAuth2Client {
	private authorizeURL: string;
	private clientId: string;
	private encodedRedirectURL: string;
	private homePageURL: string;
	private redirectURIs: Array<string>;
	private tokenURL: string;

	constructor(options: IOAuth2ClientOptions) {
		this.authorizeURL = options.authorizeURL;
		this.clientId = options.clientId;
		this.encodedRedirectURL = options.encodedRedirectURL;
		this.homePageURL = options.homePageURL;
		this.redirectURIs = options.redirectURIs;
		this.tokenURL = options.tokenURL;
	}

	public async fetch(url: RequestInfo, options: any = {}): Promise<any> {
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

	private _createIframe(challenge: any, sessionKey: string): Promise<any> {
		const oauth2Client = this;

		const ifrm = document.createElement('iframe');

		ifrm.src = `${oauth2Client.authorizeURL}?client_id=${oauth2Client.clientId}&code_challenge=${challenge.code_challenge}&code_challenge_method=S256&redirect_uri=${oauth2Client.encodedRedirectURL}&response_type=code&prompt=none`;
		ifrm.style.display = 'none';

		document.body.appendChild(ifrm);

		return new Promise((resolve, reject) => {
			const eventHandler = (event: any) => {
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
						Liferay.Util.SessionStorage.setItem(
							sessionKey,
							JSON.stringify(response),
							Liferay.Util.SessionStorage.TYPES.NECESSARY
						)
					);
				}
				finally {
					ifrm.parentElement?.removeChild(ifrm);
				}
			};

			if (ifrm.contentWindow) {
				ifrm.contentWindow.addEventListener('message', eventHandler);
			}
		});
	}

	private async _fetch(
		resource: RequestInfo | URL,
		options: any = {}
	): Promise<any> {
		const oauth2Client = this;

		let resourceUrl: string =
			resource instanceof Request ? resource.url : resource.toString();

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
			resource instanceof Request
				? {...resource, url: resourceUrl}
				: resourceUrl;

		// This client must avoid using @liferay/portal/no-global-fetch in order
		// to perform OAuth2 token authentication instead
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		return await fetch(resource, {
			headers: {
				Authorization: `Bearer ${tokenData.access_token}`,
			},
			...options,
		});
	}

	private _getOrRequestToken(): Promise<any> {
		const oauth2Client = this;
		const sessionKey = `${oauth2Client.clientId}-${Liferay.authToken}-token`;

		return new Promise((resolve) => {
			const cachedTokenData = Liferay.Util.SessionStorage.getItem(
				sessionKey,
				Liferay.Util.SessionStorage.TYPES.NECESSARY
			);

			if (cachedTokenData !== null && cachedTokenData !== undefined) {
				resolve(JSON.parse(cachedTokenData));

				return;
			}

			resolve(oauth2Client._requestTokenSilently(sessionKey));
		});
	}

	private _requestTokenSilently(sessionKey: string): Promise<any> {
		const oauth2Client = this;
		const challenge = pkceChallenge(128);

		return oauth2Client._createIframe(challenge, sessionKey);
	}

	private async _requestToken(
		codeVerifier: string,
		code: string
	): Promise<any> {
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

export function FromParameters(options: IOAuth2ClientFromParametersOptions) {
	return new OAuth2Client({
		authorizeURL: options.authorizeURL || Liferay.OAuth2.getAuthorizeURL(),
		clientId: options.clientId,
		encodedRedirectURL: encodeURIComponent(
			(options.redirectURIs && options.redirectURIs[0]) ||
				Liferay.OAuth2.getBuiltInRedirectURL()
		),
		homePageURL: options.homePageURL,
		redirectURIs: options.redirectURIs || [
			Liferay.OAuth2.getBuiltInRedirectURL(),
		],
		tokenURL: options.tokenURL || Liferay.OAuth2.getTokenURL(),
	});
}

export function FromUserAgentApplication(userAgentApplicationName: string) {
	const userAgentApplication = Liferay.OAuth2.getUserAgentApplication(
		userAgentApplicationName
	);

	if (!userAgentApplication) {
		throw new Error(
			`No Application User Agent profile found for ${userAgentApplicationName}`
		);
	}

	return new OAuth2Client({
		authorizeURL: Liferay.OAuth2.getAuthorizeURL(),
		clientId: userAgentApplication.clientId,
		encodedRedirectURL: encodeURIComponent(
			userAgentApplication.redirectURIs[0]
		),
		homePageURL: userAgentApplication.homePageURL,
		redirectURIs: userAgentApplication.redirectURIs,
		tokenURL: Liferay.OAuth2.getTokenURL(),
	});
}
