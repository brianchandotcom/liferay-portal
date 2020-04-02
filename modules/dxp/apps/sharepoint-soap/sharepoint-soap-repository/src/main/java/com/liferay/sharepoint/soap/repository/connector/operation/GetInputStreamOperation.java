/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;
import com.liferay.sharepoint.soap.repository.connector.SharepointVersion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Iván Zaera
 */
public final class GetInputStreamOperation extends BaseOperation {

	public InputStream execute(SharepointObject sharepointObject)
		throws SharepointException {

		return _execute(sharepointObject.getURL());
	}

	public InputStream execute(SharepointVersion sharepointVersion)
		throws SharepointException {

		return _execute(sharepointVersion.getURL());
	}

	private InputStream _execute(URL url) throws SharepointException {
		url = URLUtil.escapeURL(url);

		HttpGet httpGet = new HttpGet(url.toString());

		try {
			HttpClient httpClient = HttpClients.createDefault();

			HttpResponse httpResponse = httpClient.execute(
				httpGet, _getHttpClientContext(url));

			StatusLine statusLine = httpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();

				return new ByteArrayInputStream(
					_getBytes(httpEntity.getContent()));
			}

			throw new SharepointException(
				StringBundler.concat(
					"Downloading ", url, " failed with status ",
					statusLine.getStatusCode()));
		}
		catch (IOException ioException) {
			throw new SharepointException(
				"Unable to communicate with the Sharepoint server",
				ioException);
		}
		finally {
			httpGet.releaseConnection();
		}
	}

	private byte[] _getBytes(InputStream inputStream)
		throws SharepointException {

		try {
			return FileUtil.getBytes(inputStream);
		}
		catch (IOException ioException) {
			throw new SharepointException(
				"Unable to read input stream", ioException);
		}
	}

	private HttpClientContext _getHttpClientContext(URL url) {
		HttpClientContext httpClientContext = HttpClientContext.create();

		AuthCache authCache = new BasicAuthCache();

		HttpHost httpHost = new HttpHost(
			url.getHost(), url.getPort(), url.getProtocol());

		authCache.put(httpHost, new BasicScheme());

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		credentialsProvider.setCredentials(
			new AuthScope(httpHost, url.getHost(), AuthSchemes.BASIC),
			new UsernamePasswordCredentials(
				sharepointConnectionInfo.getUsername(),
				sharepointConnectionInfo.getPassword()));

		httpClientContext.setAuthCache(authCache);
		httpClientContext.setCredentialsProvider(credentialsProvider);

		return httpClientContext;
	}

}