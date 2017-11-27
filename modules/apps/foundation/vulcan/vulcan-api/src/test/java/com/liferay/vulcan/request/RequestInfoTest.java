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

package com.liferay.vulcan.request;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.vulcan.language.Language;
import com.liferay.vulcan.response.control.Embedded;
import com.liferay.vulcan.response.control.Fields;
import com.liferay.vulcan.url.ServerURL;

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
			).serverURL(
				_serverURL
			).embedded(
				_embedded
			).fields(
				_fields
			).language(
				_language
			).build());

		assertThat(requestInfo.getHttpHeaders(), is(equalTo(_httpHeaders)));
		assertThat(requestInfo.getServerURL(), is(equalTo(_serverURL)));
		assertThat(
			requestInfo.getEmbeddedOptional(),
			is(optionalWithValue(equalTo(_embedded))));
		assertThat(
			requestInfo.getFieldsOptional(),
			is(optionalWithValue(equalTo(_fields))));
		assertThat(
			requestInfo.getLanguageOptional(),
			is(optionalWithValue(equalTo(_language))));
	}

	@Test
	public void testBuildingRequestInfoWithoutOptionalsReturnEmpty() {
		RequestInfo requestInfo = RequestInfo.create(
			builder -> builder.httpHeaders(
				_httpHeaders
			).serverURL(
				_serverURL
			).build());

		assertThat(requestInfo.getEmbeddedOptional(), is(emptyOptional()));
		assertThat(requestInfo.getFieldsOptional(), is(emptyOptional()));
		assertThat(requestInfo.getLanguageOptional(), is(emptyOptional()));
	}

	private Embedded _embedded;
	private Fields _fields;
	private HttpHeaders _httpHeaders;
	private Language _language;
	private ServerURL _serverURL;

}