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
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

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
public class InvalidFormatExceptionMapperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetProblem() {
		InvalidFormatExceptionMapper invalidFormatExceptionMapper =
			new InvalidFormatExceptionMapper();

		JsonMappingException.Reference reference = Mockito.mock(
			JsonMappingException.Reference.class);

		Mockito.when(
			reference.getFieldName()
		).thenReturn(
			"PATH"
		);

		InvalidFormatException invalidFormatException = Mockito.mock(
			InvalidFormatException.class);

		Mockito.when(
			invalidFormatException.getPath()
		).thenReturn(
			Arrays.asList(reference)
		);

		Problem problem = invalidFormatExceptionMapper.getProblem(
			invalidFormatException);

		Assert.assertNull(problem.getDetail());
		Assert.assertEquals(Response.Status.BAD_REQUEST, problem.getStatus());
		Assert.assertEquals(
			"Unable to map JSON path: PATH", problem.getTitle());
		Assert.assertNull(problem.getType());
	}

}