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

package com.liferay.saml.opensaml.integration.internal.transport;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;

import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.xml.security.credential.Credential;

/**
 * @author Mika Koivisto
 */
public class HttpClientOutTransport implements HTTPOutTransport {

	public HttpClientOutTransport(PostMethod postMethod) {
		_postMethod = postMethod;
	}

	@Override
	public void addParameter(String name, String value) {
	}

	@Override
	public Object getAttribute(String name) {
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		NameValuePair nameValuePair = _postMethod.getParameter(
			"http.protocol.content-charset");

		return nameValuePair.getValue();
	}

	@Override
	public String getHeaderValue(String name) {
		Header header = _postMethod.getRequestHeader(name);

		return header.getValue();
	}

	@Override
	public String getHTTPMethod() {
		NameValuePair nameValuePair = _postMethod.getParameter(
			"http.protocol.version");

		return nameValuePair.getValue();
	}

	@Override
	public Credential getLocalCredential() {
		return null;
	}

	@Override
	public OutputStream getOutgoingStream() {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		RequestEntity requestEntity = new OutputStreamRequestEntity(
			byteArrayOutputStream);

		_postMethod.setRequestEntity(requestEntity);

		return byteArrayOutputStream;
	}

	@Override
	public String getParameterValue(String name) {
		return null;
	}

	@Override
	public List<String> getParameterValues(String name) {
		return null;
	}

	@Override
	public Credential getPeerCredential() {
		return null;
	}

	public PostMethod getPostMethod() {
		return _postMethod;
	}

	@Override
	public int getStatusCode() {
		return -1;
	}

	@Override
	public HTTP_VERSION getVersion() {
		HttpMethodParams httpMethodParams = _postMethod.getParams();

		HttpVersion httpVersion = (HttpVersion)httpMethodParams.getParameter(
			"http.protocol.version");

		if (httpVersion == HttpVersion.HTTP_1_1) {
			return HTTP_VERSION.HTTP1_1;
		}

		return HTTP_VERSION.HTTP1_0;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public boolean isConfidential() {
		try {
			URI uri = _postMethod.getURI();

			String scheme = uri.getScheme();

			if (scheme.equals("https")) {
				return true;
			}
		}
		catch (URIException urie) {
		}

		return false;
	}

	@Override
	public boolean isIntegrityProtected() {
		return false;
	}

	@Override
	public void sendRedirect(String redirect) {
	}

	@Override
	public void setAttribute(String name, Object value) {
	}

	@Override
	public void setAuthenticated(boolean uthenticated) {
	}

	@Override
	public void setCharacterEncoding(String characterEncoding) {
		HttpMethodParams httpMethodParams = _postMethod.getParams();

		httpMethodParams.setParameter(
			"http.protocol.content-charset", characterEncoding);
	}

	@Override
	public void setConfidential(boolean confidential) {
	}

	@Override
	public void setHeader(String name, String value) {
		_postMethod.setRequestHeader(name, value);
	}

	@Override
	public void setIntegrityProtected(boolean integrityProtected) {
	}

	@Override
	public void setStatusCode(int statusCode) {
	}

	@Override
	public void setVersion(HTTP_VERSION httpVersion) {
		HttpParams httpParams = _postMethod.getParams();

		if (httpVersion.equals(HTTP_VERSION.HTTP1_0)) {
			httpParams.setParameter(
				"http.protocol.version", HttpVersion.HTTP_1_0);
		}
		else if (httpVersion.equals(HTTP_VERSION.HTTP1_1)) {
			httpParams.setParameter(
				"http.protocol.version", HttpVersion.HTTP_1_1);
		}
	}

	private final PostMethod _postMethod;

}