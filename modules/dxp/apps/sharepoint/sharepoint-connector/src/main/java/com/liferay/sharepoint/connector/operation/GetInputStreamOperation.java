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

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.sharepoint.connector.SharepointException;
import com.liferay.sharepoint.connector.SharepointObject;
import com.liferay.sharepoint.connector.SharepointVersion;

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

		URL url = sharepointObject.getURL();

		return execute(url);
	}

	public InputStream execute(SharepointVersion sharepointVersion)
		throws SharepointException {

		URL url = sharepointVersion.getURL();

		return execute(url);
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
				InputStream inputStream = getMethod.getResponseBodyAsStream();

				byte[] bytes = null;

				try {
					bytes = FileUtil.getBytes(inputStream);
				}
				catch (IOException ioe) {
					throw new SharepointException(
						"Unable to read input stream", ioe);
				}

				return new ByteArrayInputStream(bytes);
			}

			throw new SharepointException(
				"Downloading " + url + " failed with status " + status);
		}
		catch (IOException ioe) {
			throw new SharepointException(
				"Unable to communicate with the Sharepoint server", ioe);
		}
		finally {
			getMethod.releaseConnection();
		}
	}

}