/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.interceptor;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.HttpContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eric Min
 */
@Component(service = HttpRequestInterceptor.class)
public class PreemptiveAuthInterceptor implements HttpRequestInterceptor {

	@Override
	public void process(HttpRequest httpRequest, HttpContext httpContext) {
		AuthState authState = (AuthState)httpContext.getAttribute(
			HttpClientContext.TARGET_AUTH_STATE);

		if (authState.getAuthScheme() != null) {
			return;
		}

		CredentialsProvider credentialsProvider =
			(CredentialsProvider)httpContext.getAttribute(
				HttpClientContext.CREDS_PROVIDER);

		HttpHost targetHttpHost = (HttpHost)httpContext.getAttribute(
			HttpClientContext.HTTP_TARGET_HOST);

		AuthScope authScope = new AuthScope(
			targetHttpHost.getHostName(), targetHttpHost.getPort());

		Credentials credentials = credentialsProvider.getCredentials(authScope);

		if (credentials != null) {
			authState.update(new BasicScheme(), credentials);
		}
	}

}