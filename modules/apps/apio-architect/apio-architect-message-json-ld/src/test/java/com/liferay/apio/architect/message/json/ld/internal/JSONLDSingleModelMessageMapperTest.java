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

package com.liferay.apio.architect.message.json.ld.internal;

import static com.liferay.apio.architect.message.json.ld.internal.JSONLDTestUtil.aRootElementJsonObjectWithId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonObject;

import com.liferay.apio.architect.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.test.util.writer.MockSingleModelWriter;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Javier Gamarra
 * @author Alejandro Hernández
 */
public class JSONLDSingleModelMessageMapperTest {

	@Test
	public void testJSONLDSingleModelMessageMapper() {
		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		JsonObject jsonObject = MockSingleModelWriter.write(
			httpHeaders, _singleModelMessageMapper);

		assertThat(
			jsonObject, is(aRootElementJsonObjectWithId("first", true, false)));
	}

	@Test
	public void testMediaTypeIsCorrect() {
		String mediaType = _singleModelMessageMapper.getMediaType();

		assertThat(mediaType, is("application/ld+json"));
	}

	private final SingleModelMessageMapper<RootModel>
		_singleModelMessageMapper = new JSONLDSingleModelMessageMapper<>();

}