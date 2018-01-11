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

package com.liferay.apio.architect.request;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.language.Language;
import com.liferay.apio.architect.response.control.Embedded;
import com.liferay.apio.architect.response.control.Fields;
import com.liferay.apio.architect.url.ServerURL;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class RequestInfoTest {

	@Before
	public void setUp() {
		_httpHeaders = Mockito.mock(HttpHeaders.class);
		_httpServletRequest = Mockito.mock(HttpServletRequest.class);
		_serverURL = Mockito.mock(ServerURL.class);
		_embedded = Mockito.mock(Embedded.class);
		_fields = Mockito.mock(Fields.class);
		_language = Mockito.mock(Language.class);
	}

	@Test
	public void testBuildingRequestInfoCreatesValidRequestInfo() {
		RequestInfo requestInfo = RequestInfo.create(
			builder -> builder.httpHeaders(
				_httpHeaders
			).httpServletRequest(
				_httpServletRequest
			).serverURL(
				_serverURL
			).embedded(
				_embedded
			).fields(
				_fields
			).language(
				_language
			).build());

		assertThat(requestInfo.getEmbedded(), is(_embedded));
		assertThat(requestInfo.getFields(), is(_fields));
		assertThat(requestInfo.getHttpHeaders(), is(_httpHeaders));
		assertThat(
			requestInfo.getHttpServletRequest(), is(_httpServletRequest));
		assertThat(requestInfo.getLanguage(), is(_language));
		assertThat(requestInfo.getServerURL(), is(_serverURL));
	}

	private Embedded _embedded;
	private Fields _fields;
	private HttpHeaders _httpHeaders;
	private HttpServletRequest _httpServletRequest;
	private Language _language;
	private ServerURL _serverURL;

}