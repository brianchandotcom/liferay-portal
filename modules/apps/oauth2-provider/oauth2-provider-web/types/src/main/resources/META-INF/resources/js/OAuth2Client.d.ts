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
interface IOAuth2 {
    getAuthorizeURL(): string;
    getBuiltInRedirectURL(): string;
    getTokenURL(): string;
    getUserAgentApplication(externalReferenceCode: string): {
        clientId: string;
        homePageURL: string;
        redirectURIs: Array<string>;
    };
}
interface IOAuth2ClientFromParametersOptions {
    clientId: string;
    homePageURL: string;
    authorizeURL?: string;
    redirectURIs?: Array<string>;
    tokenURL?: string;
}
interface ILiferay {
    OAuth2: IOAuth2;
    OAuth2Client: OAuth2Client;
    authToken: string;
}
declare global {
    interface Window {
        Liferay: ILiferay;
    }
}
declare class OAuth2Client {
    private authorizeURL;
    private clientId;
    private encodedRedirectURL;
    private homePageURL;
    private redirectURIs;
    private tokenURL;
    static FromParameters(options: IOAuth2ClientFromParametersOptions): OAuth2Client;
    static FromUserAgentApplication(userAgentApplicationName: string): OAuth2Client;
    fetch(url: RequestInfo, options?: any): Promise<any>;
    _createIframe(): HTMLIFrameElement;
    _fetch(resource: RequestInfo | URL, options?: any): Promise<any>;
    _getOrRequestToken(): Promise<any>;
    _requestTokenSilently(sessionKey: string): Promise<any>;
    _requestToken(codeVerifier: string, code: string): Promise<any>;
}
export default OAuth2Client;
