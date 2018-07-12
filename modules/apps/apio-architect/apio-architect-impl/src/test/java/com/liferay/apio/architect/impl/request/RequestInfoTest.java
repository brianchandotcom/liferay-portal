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

package com.liferay.apio.architect.impl.request;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.impl.response.control.Embedded;
import com.liferay.apio.architect.impl.response.control.Fields;
import com.liferay.apio.architect.impl.url.ApplicationURL;
import com.liferay.apio.architect.impl.url.ServerURL;
import com.liferay.apio.architect.language.AcceptLanguage;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class RequestInfoTest {

	@Before
	public void setUp() {
		_httpServletRequest = Mockito.mock(HttpServletRequest.class);
		_serverURL = Mockito.mock(ServerURL.class);
		_applicationURL = Mockito.mock(ApplicationURL.class);
		_embedded = Mockito.mock(Embedded.class);
		_fields = Mockito.mock(Fields.class);
		_acceptLanguage = Mockito.mock(AcceptLanguage.class);
	}

	@Test
	public void testBuildingRequestInfoCreatesValidRequestInfo() {
		RequestInfo requestInfo = RequestInfo.create(
			builder -> builder.httpServletRequest(
				_httpServletRequest
			).serverURL(
				_serverURL
			).applicationURL(
				_applicationURL
			).embedded(
				_embedded
			).fields(
				_fields
			).language(
				_acceptLanguage
			).build());

		assertThat(requestInfo.getEmbedded(), is(_embedded));
		assertThat(requestInfo.getFields(), is(_fields));
		assertThat(
			requestInfo.getHttpServletRequest(), is(_httpServletRequest));
		assertThat(requestInfo.getAcceptLanguage(), is(_acceptLanguage));
		assertThat(requestInfo.getServerURL(), is(_serverURL));
		assertThat(requestInfo.getApplicationURL(), is(_applicationURL));
	}

	private AcceptLanguage _acceptLanguage;
	private ApplicationURL _applicationURL;
	private Embedded _embedded;
	private Fields _fields;
	private HttpServletRequest _httpServletRequest;
	private ServerURL _serverURL;

}