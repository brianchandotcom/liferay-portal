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

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mariano Álvaro Sáiz
 */
public class WebApplicationExceptionMapperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetProblem() {
		WebApplicationExceptionMapper webApplicationExceptionMapper =
			new WebApplicationExceptionMapper();

		Response response = Mockito.mock(Response.class);

		Response.Status responseStatus = Response.Status.NO_CONTENT;

		Mockito.when(
			response.getStatus()
		).thenReturn(
			responseStatus.getStatusCode()
		);

		WebApplicationException webApplicationException =
			new WebApplicationException("Risky message", response);

		Problem problem = webApplicationExceptionMapper.getProblem(
			webApplicationException);

		Assert.assertNull(problem.getDetail());
		Assert.assertEquals(responseStatus, problem.getStatus());
		Assert.assertEquals("A problem occured", problem.getTitle());
		Assert.assertNull(problem.getType());
	}

}