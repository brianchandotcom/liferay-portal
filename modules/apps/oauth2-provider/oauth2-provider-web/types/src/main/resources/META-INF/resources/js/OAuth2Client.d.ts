/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IOAuth2ClientFromParametersOptions {
	authorizeURL?: string;
	clientId: string;
	debug?: boolean;
	homePageURL: string;
	redirectURIs?: Array<string>;
	tokenURL?: string;
}
interface IOAuth2ClientOptions {
	authorizeURL: string;
	clientId: string;
	debug?: boolean;
	homePageURL: string;
	redirectURIs: Array<string>;
	tokenURL: string;
}
declare class OAuth2Client {
	private authorizeURL;
	private clientId;
	private debug;
	private homePageURL;
	private redirectURIs;
	private tokenURL;
	constructor(options: IOAuth2ClientOptions);
	fetch(url: RequestInfo, options?: any): Promise<any>;
	private _createIframe;
	private _fetch;
	private _getOrRequestToken;
	private _requestTokenSilently;
	private _requestToken;
}
export declare function FromParameters(
	options: IOAuth2ClientFromParametersOptions
): OAuth2Client;
export declare function FromUserAgentApplication(
	userAgentApplicationName: string,
	debug?: boolean
): OAuth2Client;
export {};
