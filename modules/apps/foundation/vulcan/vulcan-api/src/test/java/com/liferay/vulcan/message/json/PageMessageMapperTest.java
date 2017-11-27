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

package com.liferay.vulcan.message.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import com.liferay.vulcan.pagination.Page;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class PageMessageMapperTest {

	@Test
	public void testMessageMapperIsEmptyByDefaultAndSupportsMapping() {
		PageMessageMapper<Integer> pageMessageMapper = () -> "mediaType";

		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		assertThat(
			pageMessageMapper.supports(page, httpHeaders), is(equalTo(true)));
	}

	@Mock
	public Page<Integer> page;

}