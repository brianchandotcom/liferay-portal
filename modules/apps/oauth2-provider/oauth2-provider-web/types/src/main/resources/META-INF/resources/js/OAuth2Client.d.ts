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
declare class OAuth2Client {
	private authorizeURL;
	private clientId;
	private encodedRedirectURL;
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
	userAgentApplicationName: string
): OAuth2Client;
export {};
