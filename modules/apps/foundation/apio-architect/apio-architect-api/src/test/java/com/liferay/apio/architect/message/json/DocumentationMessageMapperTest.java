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

package com.liferay.apio.architect.message.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.documentation.Documentation;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class DocumentationMessageMapperTest {

	@Test
	public void testMessageMapperIsEmptyByDefaultAndSupportsMapping() {
		DocumentationMessageMapper documentationMessageMapper =
			() -> "mediaType";

		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		Documentation documentation = Mockito.mock(Documentation.class);

		assertThat(
			documentationMessageMapper.supports(documentation, httpHeaders),
			is(true));
	}

}