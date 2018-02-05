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

package com.liferay.apio.architect.sample.internal.filter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.liferay.apio.architect.sample.internal.auth.PermissionChecker;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class GeneralFilterTest {

	@Test
	public void testGeneralFilter() throws IOException {
		GeneralFilter generalFilter = new GeneralFilter();

		ContainerRequestContext containerRequestContext = Mockito.mock(
			ContainerRequestContext.class);

		Mockito.when(
			containerRequestContext.getHeaderString("Authorization")
		).thenReturn(
			"Apio"
		);

		environmentVariables.set("APIO_AUTH", "Apio");

		generalFilter.filter(containerRequestContext);

		assertThat(PermissionChecker.hasPermission(), is(true));
	}

	@Rule
	public final EnvironmentVariables environmentVariables =
		new EnvironmentVariables();

}