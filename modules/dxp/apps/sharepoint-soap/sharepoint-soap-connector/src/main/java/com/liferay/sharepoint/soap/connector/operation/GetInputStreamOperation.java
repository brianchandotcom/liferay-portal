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

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.SharepointObject;
import com.liferay.sharepoint.soap.connector.SharepointVersion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

/**
 * @author Iván Zaera
 */
public class GetInputStreamOperation extends BaseOperation {

	public InputStream execute(SharepointObject sharepointObject)
		throws SharepointException {

		return execute(sharepointObject.getURL());
	}

	public InputStream execute(SharepointVersion sharepointVersion)
		throws SharepointException {

		return execute(sharepointVersion.getURL());
	}

	protected void authenticate(HttpClient httpClient, URL url) {
		HttpClientParams httpClientParams = httpClient.getParams();

		httpClientParams.setAuthenticationPreemptive(true);

		AuthScope authScope = new AuthScope(
			url.getHost(), url.getPort(), url.getHost(), "BASIC");

		UsernamePasswordCredentials usernamePasswordCredentials =
			new UsernamePasswordCredentials(
				sharepointConnectionInfo.getUsername(),
				sharepointConnectionInfo.getPassword());

		HttpState httpClientState = httpClient.getState();

		httpClientState.setCredentials(authScope, usernamePasswordCredentials);
	}

	protected InputStream execute(URL url) throws SharepointException {
		url = urlHelper.escapeURL(url);

		HttpClient httpClient = new HttpClient();

		authenticate(httpClient, url);

		GetMethod getMethod = new GetMethod(url.toString());

		getMethod.setDoAuthentication(true);

		try {
			int status = httpClient.executeMethod(getMethod);

			if (status == HttpStatus.SC_OK) {
				return new ByteArrayInputStream(
					_getBytes(getMethod.getResponseBodyAsStream()));
			}

			throw new SharepointException(
				StringBundler.concat(
					"Downloading ", url, " failed with status ", status));
		}
		catch (IOException ioException) {
			throw new SharepointException(
				"Unable to communicate with the Sharepoint server",
				ioException);
		}
		finally {
			getMethod.releaseConnection();
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

}