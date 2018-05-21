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

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.nio.charset.Charset;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;

import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.xml.security.credential.Credential;

/**
 * @author Mika Koivisto
 */
public class HttpClientInTransport implements HTTPInTransport {

	public HttpClientInTransport(HttpPost httpPost, String location) {
		_httpPost = httpPost;
		_location = location;

		_httpEntity = _httpPost.getEntity();
	}

	@Override
	public Object getAttribute(String name) {
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		ContentType contentType = ContentType.get(_httpEntity);

		Charset charset = contentType.getCharset();

		return charset.name();
	}

	@Override
	public String getHeaderValue(String name) {
		return null;
	}

	@Override
	public String getHTTPMethod() {
		return _httpPost.getMethod();
	}

	@Override
	public InputStream getIncomingStream() {
		try {
			return _httpEntity.getContent();
		}
		catch (IOException ioe) {
			return null;
		}
	}

	public String getLocalAddress() {
		return _location;
	}

	@Override
	public Credential getLocalCredential() {
		return null;
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
	public String getPeerAddress() {
		return null;
	}

	@Override
	public Credential getPeerCredential() {
		return null;
	}

	@Override
	public String getPeerDomainName() {
		return null;
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.SC_OK;
	}

	@Override
	public HTTP_VERSION getVersion() {
		return null;
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
	public void setAuthenticated(boolean authenticated) {
	}

	@Override
	public void setConfidential(boolean confidential) {
	}

	@Override
	public void setIntegrityProtected(boolean integrityProtected) {
	}

	private final HttpEntity _httpEntity;
	private final HttpPost _httpPost;
	private final String _location;

}