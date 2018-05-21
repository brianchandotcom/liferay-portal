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

package com.liferay.saml.opensaml.integration.internal.transport;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import java.net.URI;

import java.nio.charset.Charset;

import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;

import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.xml.security.credential.Credential;

/**
 * @author Mika Koivisto
 */
public class HttpClientOutTransport implements HTTPOutTransport {

	public HttpClientOutTransport(HttpPost httpPost) {
		_httpPost = httpPost;

		_httpEntity = _httpPost.getEntity();
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
		ContentType contentType = ContentType.get(_httpEntity);

		if (contentType != null) {
			Charset charset = contentType.getCharset();

			return charset.name();
		}

		return Consts.ISO_8859_1.name();
	}

	@Override
	public String getHeaderValue(String name) {
		Header header = _httpPost.getFirstHeader(name);

		return header.getValue();
	}

	@Override
	public String getHTTPMethod() {
		return _httpPost.getMethod();
	}

	public HttpPost getHttpPost() {
		return _httpPost;
	}

	@Override
	public Credential getLocalCredential() {
		return null;
	}

	@Override
	public OutputStream getOutgoingStream() {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		if (_httpEntity == null) {
			_httpEntity = new OutputStreamHttpEntity(byteArrayOutputStream);

			_httpPost.setEntity(_httpEntity);
		}

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

	@Override
	public int getStatusCode() {
		return -1;
	}

	@Override
	public HTTP_VERSION getVersion() {
		ProtocolVersion protocolVersion = _httpPost.getProtocolVersion();

		if (protocolVersion.equals(HttpVersion.HTTP_1_1)) {
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
		URI uri = _httpPost.getURI();

		String scheme = uri.getScheme();

		if (scheme.equals("https")) {
			return true;
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
		_httpPost.setHeader("http.protocol.content-charset", characterEncoding);
	}

	@Override
	public void setConfidential(boolean confidential) {
	}

	@Override
	public void setHeader(String name, String value) {
		_httpPost.setHeader(name, value);
	}

	@Override
	public void setIntegrityProtected(boolean integrityProtected) {
	}

	@Override
	public void setStatusCode(int statusCode) {
	}

	@Override
	public void setVersion(HTTP_VERSION httpVersion) {
		if (httpVersion.equals(HTTP_VERSION.HTTP1_0)) {
			_httpPost.setProtocolVersion(HttpVersion.HTTP_1_0);
		}
		else if (httpVersion.equals(HTTP_VERSION.HTTP1_1)) {
			_httpPost.setProtocolVersion(HttpVersion.HTTP_1_1);
		}
	}

	private HttpEntity _httpEntity;
	private final HttpPost _httpPost;

}