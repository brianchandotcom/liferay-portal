/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		HttpHost httpHost = new HttpHost(
			url.getHost(), url.getPort(), url.getProtocol());

		httpClientContext.setAuthCache(
			new BasicAuthCache() {
				{
					put(httpHost, new BasicScheme());
				}
			});
		httpClientContext.setCredentialsProvider(
			new BasicCredentialsProvider() {
				{
					setCredentials(
						new AuthScope(
							httpHost, url.getHost(), AuthSchemes.BASIC),
						new UsernamePasswordCredentials(
							sharepointConnectionInfo.getUserName(),
							sharepointConnectionInfo.getPassword()));
				}
			});

		return httpClientContext;
	}

}