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

package com.liferay.portal.vulcan.internal.jaxrs.exception.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.Arrays;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mariano Álvaro Sáiz
 */
public class JsonMappingExceptionMapperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetProblem() {
		JsonMappingExceptionMapper jsonMappingExceptionMapper =
			new JsonMappingExceptionMapper();

		JsonMappingException.Reference reference = Mockito.mock(
			JsonMappingException.Reference.class);

		Mockito.when(
			reference.getFieldName()
		).thenReturn(
			"PATH"
		);

		JsonMappingException jsonMappingException = Mockito.mock(
			JsonMappingException.class);

		Mockito.when(
			jsonMappingException.getPath()
		).thenReturn(
			Arrays.asList(reference)
		);

		Problem problem = jsonMappingExceptionMapper.getProblem(
			jsonMappingException);

		Assert.assertNull(problem.getDetail());
		Assert.assertEquals(Response.Status.BAD_REQUEST, problem.getStatus());
		Assert.assertEquals(
			"Unable to map JSON path: PATH", problem.getTitle());
		Assert.assertNull(problem.getType());
	}

}